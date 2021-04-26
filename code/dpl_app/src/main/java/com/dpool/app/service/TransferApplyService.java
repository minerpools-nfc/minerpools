package com.dpool.app.service;

import com.alibaba.fastjson.JSONObject;
import com.dpool.app.mapper.ZsDevnfMapper;
import com.dpool.app.mapper.ZsDrawApplyMapper;
import com.dpool.app.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提请申请打币service
 */
@Slf4j
@Service
public class TransferApplyService {

    @Value("${transfer.apply.fromaddr:}")
    private String fromaddr;

    @Value("${transfer.apply.rpcurl:}")
    private String rpcurl;
    @Value("${transfer.confBlockNumber:6}")
    private String confBlockNumber;

    @Autowired
    ZsDrawApplyMapper zsDrawApplyMapper;
    @Autowired
    private ZsDevnfMapper zsDevnfMapper;

    /**
     * 根据私钥和申请表id进行区块链打币
     * @param param
     */
    public synchronized void startTransferDigitalMoney(TransferApplyParam param,JSONObject r) {
        String prikey = param.getPrikey();
        String applyid = param.getApplyid();
        if (null == prikey || null == applyid || "".equals(prikey.trim())
                || "".equals(applyid.trim())) {
            log.error("参数错误:" + param);
            return;
        }
        ZsDrawApply apply = zsDrawApplyMapper.single(applyid);
        if (apply != null) {
            Integer oldtransStatus=apply.getTransStatus();
            if(oldtransStatus!=null){
                if(oldtransStatus==3||oldtransStatus==2){
                    log.info("该记录已经转过账"+apply);
                    r.put("result", 650);
                    r.put("errmsg", "该记录已经转过账"+apply.getApplyid());
                    return;
                }
            }
            log.info("参数:" + applyid + "开始进行转账");
            //初始化区块链rpc对象和解锁账号
            Web3j web3j = Web3j.build(new HttpService(rpcurl));
            try {
                ZsDrawApply updateR=new ZsDrawApply();
                updateR.setApplyid(applyid);
                updateR.setUpdatetime(new Date());
                updateR.setTransStatus(3);
                zsDrawApplyMapper.updateTemplateById(updateR);
                Boolean success=sendNFC(apply, web3j, prikey);
                if(success){
                    updateR.setTransNo(apply.getTransNo());
                    updateR.setTransTime(new Date());
                    updateR.setTransStatus(4);
                    updateR.setUpdatetime(new Date());
                    zsDrawApplyMapper.updateTemplateById(updateR);
                }else{
                    r.put("result", 650);
                    r.put("errmsg", "转账失败,请联系管理员");
                }
            } catch (Exception e) {
                log.error("参数:" + applyid +"转账报错", e);
            }
            log.info("参数:" + applyid +"结束进行转账");
        } else {
            log.info("参数:" + applyid + "没有查出转账记录");
            r.put("result", 650);
            r.put("errmsg", "参数:" + applyid + "没有查出转账记录");
        }
    }

    /**
     * 计算收益表已提取的数量和最后提取时间
     * @param applyid
     */
    public void calTockenNum(String applyid) {
        Map rUm=new HashMap();
        rUm.put("applyid",applyid);
        List<String> flowids= zsDrawApplyMapper.getSQLManager().select("zsDrawApply.queryFlowids",String.class,rUm);
        if(null!=flowids&&flowids.size()>0){
            for(String flowid:flowids){
                Map fM=new HashMap();
                fM.put("flowid",flowid);
                BigDecimal sumSN=zsDrawApplyMapper.getSQLManager().selectSingle("zsDrawApply.querySumSharenum",fM,BigDecimal.class);
                ZsDeviceTockenRecord tr=zsDrawApplyMapper.getSQLManager().selectSingle("zsDrawApply.queryTockenR",fM,ZsDeviceTockenRecord.class);
                if(null!=tr){
                	BigDecimal tsharenum=BigDecimal.valueOf(tr.getSharenum());
                    ZsDeviceTockenRecord trU=new ZsDeviceTockenRecord();
                    trU.setFlowid(flowid);
                    trU.setDrawnum(sumSN.doubleValue());
                    if(sumSN.compareTo(new BigDecimal("0"))==0){
                        trU.setDrawStatus(1);
                        trU.setLastDrawtime(new Date());
                    }else if(sumSN.compareTo(tsharenum)<0){
                        trU.setDrawStatus(3);
                        trU.setLastDrawtime(new Date());
                    }else if(sumSN.compareTo(tsharenum)>=0){
                        trU.setDrawStatus(2);
                        trU.setLastDrawtime(new Date());
                    }
                    zsDevnfMapper.updateTemplateById(trU);
                }
            }
        }
    }
    
//    /**
//     * 批量回填
//     */
    public void   butchClaTocken() {
    	try {
    	List<ZsDeviceTockenRecord> list=zsDevnfMapper.getSQLManager().select("zsDrawApply.queryTockenRecordByDay", ZsDeviceTockenRecord.class);
    	 if(list!=null&&list.size()>0) {
    		  HashMap<String,ZsDeviceTockenRecord>  trdmap=new HashMap<>();
    		 for(ZsDeviceTockenRecord info:list) {
    			 ZsDeviceTockenRecord cinfo=trdmap.get(info.getFlowid());
    			 if(cinfo==null) {
    				 cinfo=new ZsDeviceTockenRecord();
    				 cinfo.setFlowid(info.getFlowid());
    				 cinfo.setHasrelease(info.getHasrelease());
    				 if(info.getDrawStatus().intValue()==3) {
    					 cinfo.setDrawnum(info.getDrawnum());
    				 }else {
    					 cinfo.setDrawnum(0d);
    				 }
    				
    				 trdmap.put(info.getFlowid(), cinfo);
    			 }else {
    				 cinfo.setHasrelease(cinfo.getHasrelease()+info.getHasrelease());
    				 if(info.getDrawStatus().intValue()==3) {
    				 cinfo.setDrawnum(cinfo.getDrawnum()+info.getDrawnum());
    				 }else {
    					 cinfo.setDrawnum(0d);
    				 }
    			 }
    			 if(cinfo.getHasrelease()>0&&cinfo.getHasrelease()<info.getNfcNum()) {
    				 cinfo.setReleaseStatus(3);
    			 }else if(cinfo.getHasrelease()==info.getNfcNum()) {
    				 cinfo.setReleaseStatus(2);
    			 }else {
    				 cinfo.setReleaseStatus(1);
    			 }
    			 if(cinfo.getDrawnum()>0&&cinfo.getDrawnum()<info.getHasrelease()) {
    				 cinfo.setDrawStatus(3);
    			 }else if(cinfo.getDrawnum()==info.getHasrelease()) {
    				 cinfo.setDrawStatus(2);
    			 }else {
    				 cinfo.setDrawStatus(1);
    			 }
    		 }
    		 if(trdmap.size()>0) {
    			 zsDevnfMapper.getSQLManager().updateBatchTemplateById(ZsDeviceTockenRecord.class, new ArrayList(trdmap.values()));
    		 }
    	 }
    	}catch (Exception e) {
			log.error("批量回填提取数据出错",e);
		}
    }

    /**
     * 区块链打币
     * @param onek 待打币收益记录
     * @return
     */
    private boolean sendNFC(ZsDrawApply onek, Web3j web3j, String privatekey) {
        boolean issuccess = false;
        try {
            String applyid = onek.getApplyid();
            //发送区块链转账请求
            String transferNo = null;
            String drawAddress = onek.getWalletAddress();
            if (null == drawAddress || "".equals(drawAddress.trim())) {
                log.info("转账没有地址:" + onek);
                return false;
            }
            try {
                transferNo = blockchainTransferDigitalMoney(onek.getShareNum(), web3j, fromaddr, privatekey, drawAddress, applyid);
                if (transferNo != null && transferNo.trim().length() > 50) {
                    issuccess = true;
                    onek.setTransNo(transferNo);
                }
            } catch (Exception e) {
                log.error("转账" + applyid + "失败", e);
                issuccess = false;
            }
            if (issuccess) {
               log.info("转账" + applyid + "成功");
            } else {
               log.info("转账" + applyid + "失败");
            }
        } catch (Exception e) {
            log.error("打币出错" + onek, e);
        }
        return issuccess;
    }

    /**
     * 上链进行打币
     * @param coinNum 转账金额
     * @param web3j
     * @param from
     * @param privatekey
     * @param to
     * @param recordid
     * @return
     * @throws Exception
     */
    public String blockchainTransferDigitalMoney(BigDecimal coinNum, Web3j web3j, String from, String privatekey, String to, String recordid) throws Exception {
        String hash = "";
        //转账的凭证，需要传入私钥67
        Credentials credentials = Credentials.create(privatekey);
        //支付的矿工费
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        BigInteger gasLimit = new BigInteger("210000");
        //获取交易笔数
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.PENDING).send();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        coinNum = coinNum.multiply(new BigDecimal(10).pow(18));
        BigInteger value = coinNum.toBigInteger(); //转账金额
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(gasPrice, gasLimit, nonce, to, value);
        //RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,gasPrice, gasLimit, to, value);
        //进行签名操作
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //transMainid 转账记录表id
        log.info("recordid:" + recordid + "开始转账,from:" + from + ",gasPrice:" + gasPrice + ",gasLimit:" + gasLimit + ",to:" + to + ",nonce:" + nonce + ",value:" + value);
        //hash="0xe764561aadf27f16678b7e23dc4f73750e598595d63a51e38246f37548b6f110";
        //return hash;
        //发起交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        hash = ethSendTransaction.getTransactionHash();
        if (hash == null) {
            Response.Error error = ethSendTransaction.getError();
            String msg = error.getCode() + ":" + error.getMessage();
            log.error("recordid:" + recordid + ",from:" + from + "转账给地址:" + to + "报错Code:" + error.getCode() + "Message:" + error.getMessage() + "Data:" + error.getData());
            throw new Exception(msg);
        }
        log.info("recordid:" + recordid + "转账成功,sendTransaction:" + hash);
        return hash;
    }

    /**
     * 确认提请转账结果
     */
    public void startConfirm() {
        try {
            confirmApply();
        } catch (Exception e) {
            log.error("确认提请转账结果报错:", e);
        }
    }

    /**
     * 查询提请转账结果需确认的数据
     */
    public void confirmApply() {
        List<ZsDrawApply> krs = zsDrawApplyMapper.getSQLManager().select("zsDrawApply.queryConfirmA", ZsDrawApply.class);
        if (krs != null && krs.size() > 0) {
            log.info("开始确认提请转账,预计详情成功转账" + krs.size() + "条");
            int s = 0;
            int w = 0;
            //连接区块链对象
            JsonRpc2_0Admin admin = (JsonRpc2_0Admin) Admin.build(new HttpService(rpcurl));
            for (ZsDrawApply onek : krs) {
                String applyid = onek.getApplyid();
                //查询区块链转账请求是否成功
                Boolean transferNoR = false;
                try {
                    transferNoR = isSuccessByHash(onek.getTransNo(), admin, confBlockNumber);
                } catch (Exception e) {
                    log.error("提请转账" + applyid + "失败", e);
                    transferNoR = false;
                }

                Date endDate = new Date();
                ZsDrawApply updateA = new ZsDrawApply();
                updateA.setApplyid(applyid);
                updateA.setUpdatetime(endDate);
                if (transferNoR) {
                    s++;
                    updateA.setTransStatus(2);
                    zsDrawApplyMapper.updateTemplateById(updateA);
                    Map rUm=new HashMap();
                    rUm.put("applyid",applyid);
                    zsDrawApplyMapper.getSQLManager().update("zsDrawApply.updateRelease",rUm);
                    calTockenNum(applyid);
                } else {
                    w++;
                }
            }
            log.info("结束提请确认转账,确认成功" + s + "条,未确认" + w + "条");
        } else {
            log.info("没有查出提请需确认转账的记录");
        }
    }

    /**
     * 交易是否被区块链确认
     * @param hash
     * @param admin
     * @param confBlockNumber
     * @return
     * @throws IOException
     */
    public boolean isSuccessByHash(String hash, JsonRpc2_0Admin admin, String confBlockNumber) throws IOException {
        if (!StringUtils.isEmpty(hash)) {
            org.web3j.protocol.core.methods.response.Transaction transaction = null;
            try {
                transaction = admin.ethGetTransactionByHash(hash).send().getTransaction().get();
            } catch (Exception e) {
                log.error("获取hash:" + hash + "的transaction报错", e);
                transaction = null;
            }
            if (transaction != null) {
                BigInteger blockNumber = null;
                try {
                    blockNumber = transaction.getBlockNumber();
                } catch (Exception e) {
                    log.error("获取blockNumber报错", e);
                    blockNumber = null;
                }
                if (blockNumber == null) return false;
                BigInteger currentBlockNumber = admin.ethBlockNumber().send().getBlockNumber();
                if (blockNumber != null && currentBlockNumber != null) {
                    if (currentBlockNumber.subtract(blockNumber).compareTo(new BigInteger(confBlockNumber)) >= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
