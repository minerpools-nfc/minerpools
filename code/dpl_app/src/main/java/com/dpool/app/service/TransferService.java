package com.dpool.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpool.app.dbpoolsyc.DbpoolSycSyncSrv;
import com.dpool.app.mapper.ZumrdMapper;
import com.dpool.app.util.HmacSha256Util;
import com.dpool.app.vo.TransferParam;
import com.dpool.app.vo.ZsUserMbpoitRecordDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.*;

/**
 * 分红打印和积分转账service
 */
@Slf4j
@Service
public class TransferService {

    @Value("${dbpoolSycService.secretkey}")
    private String secretkey;

    @Autowired
    ZumrdMapper zumrdMapper;

    /**
     * 获取需转让积分的记录
     * @return
     * @throws Exception
     */
    public  List<ZsUserMbpoitRecordDetail> getNeedTransMbpoit(TransferParam param) throws Exception{
    	return zumrdMapper.getSQLManager().select("zsUserMbpoitRecordDetail.queryMbpoitDetails", ZsUserMbpoitRecordDetail.class,param);
         
    }
    
    /**
     * 转让用户积分
     */
    public void startTransferMbp(TransferParam param,JSONObject r) throws Exception{
        List<ZsUserMbpoitRecordDetail> drs=getNeedTransMbpoit(param);
        String detail ="";
    	if (drs != null && drs.size() > 0) {
            log.info("开始进行积分转账,预计成功转账" + drs.size() + "条");
            int s = 0;
            int f = 0;
            for (ZsUserMbpoitRecordDetail onek : drs) {
                try {
                    if (checkMbpR(onek)&&sendMbp(onek)) {
                        s++;
                    } else {
                        f++;
                    }
                } catch (Exception e) {
                    log.error("积分转账记录", e);
                }
            }
            detail="积分转让结束,发送成功" + s + "条,失败" + f + "条";
            log.info(detail);
            if(s>0&&s<=drs.size()){
                r.put("result", 0);
                r.put("errmsg", detail);
                (new DbpoolSycSyncSrv("user", null)).start();//同步積分
                return;
            }
            if(f>0&&s<=0){
                r.put("result",650);
                r.put("errmsg", detail);
                return;
            }
        } else {
            r.put("result", 650);
            r.put("errmsg", "参数:" + param.getMtbutch() + "没有查出积分转账记录");
            log.info("没有查出积分转账记录");
        }
    	return;
    }

    /**
     * 检查转让积分参数
     * @param onek
     * @return
     */
    private boolean checkMbpR(ZsUserMbpoitRecordDetail onek) {
        BigDecimal mbpoint = onek.getMbpoint();
        if (null == mbpoint) {
            log.error("转账没有积分:" + onek);
            return false;
        }
        Long fromuserid = onek.getFromuserid();
        if (null == fromuserid) {
            log.error("转账没有fromuserid:" + onek);
            return false;
        }
        Long touserid = onek.getTouserid();
        if (null == touserid) {
            log.error("转账没有touserid:" + onek);
            return false;
        }
        return true;
    }

    /**
     * 通过post请求转让积分
     * @param onek
     * @return
     */
    private boolean sendMbp(ZsUserMbpoitRecordDetail onek) {
        boolean issuccess = false;
        try {
            String detailid = onek.getRdetailid();
            //开始转账 将记录置为转账中
            Date sendDateIng = new Date();
            ZsUserMbpoitRecordDetail updateKing = new ZsUserMbpoitRecordDetail();
            updateKing.setRdetailid(detailid);
            updateKing.setTransfertime(sendDateIng);
            updateKing.setTransferStatus(2);
            zumrdMapper.updateTemplateById(updateKing);
            String faildMsg = null;
            try {
                String transferNo = transferPost(onek.getFromuserid(), onek.getTouserid(), onek.getMbpoint());
                if (transferNo != null && !"".equals(transferNo.trim())) {
                    JSONObject jsonR = JSON.parseObject(transferNo);
                    Integer resultI = (Integer) jsonR.get("result");
                    if (null!=resultI&&0 == resultI) {
                        issuccess = true;
                        faildMsg="";
                    } else if (null!=resultI&&1 == resultI) {
                        faildMsg = "无效的用户ID";
                    } else if (null!=resultI&&2 == resultI) {
                        faildMsg = "无效的转让积分";
                    } else if (null!=resultI&&3 == resultI) {
                        faildMsg = "用户积分不足";
                    } else {
                        log.error("transferPost 返回报错:" + transferNo);
                        faildMsg = "系统错误,请联系管理员";
                    }
                } else {
                    log.error("transferPost 返回null或者空字符串");
                    faildMsg = "系统错误,请联系管理员";
                }
            } catch (Exception e) {
                log.error("转账" + detailid + "失败", e);
                issuccess = false;
            }
            ZsUserMbpoitRecordDetail updateK = new ZsUserMbpoitRecordDetail();
            updateK.setRdetailid(detailid);
            updateK.setUpdatetime(new Date());
            updateK.setFaildMsg(faildMsg);
            if (issuccess) {
                updateK.setTransferStatus(3);
                zumrdMapper.updateTemplateById(updateK);
            } else {
                updateK.setTransferStatus(4);
                zumrdMapper.updateTemplateById(updateK);
            }
        } catch (Exception e) {
            log.error("积分转账出错" + onek, e);
        }
        return issuccess;
    }

    /**
     * 构造参数发送转让积分post请求
     * @param fromuserid
     * @param touserid
     * @param mbpoint
     * @return
     */
    private String transferPost(Long fromuserid, Long touserid, BigDecimal mbpoint) throws Exception {
        JSONObject json = new JSONObject();
        json.put("trout_userid", fromuserid.toString());
        json.put("trin_userid", touserid.toString());
        json.put("mbpoint", mbpoint);
        Map urls = zumrdMapper.getSQLManager().selectSingle("zsDevices.queryInterface", null, Map.class);
        if (null == urls || urls.size() == 0) {
        	return "未配置积分转让接口url";
        }
        String username= (String) urls.get("username");
        String userpwd= (String) urls.get("userpwd");
        String random = System.currentTimeMillis() + "";
        json.put("loginname", username);
        String data = "loginname=" + username + "&pwd=" + userpwd + "&random=" + random;
        String MD5val = DigestUtils.md5Hex(data);
        String sig = HmacSha256Util.HMACSHA256(MD5val, secretkey);
        json.put("sig", sig);
        String httpurl = (String) urls.get("httpurl");
        String mbpTranferurl=httpurl.replace("getdata","transfermbp")+"&random=" + random;;
        return sendPost(json, mbpTranferurl);
    }

    /**
     * 发送post请求
     * @param json
     * @param url
     * @return
     */
    public String sendPost(JSONObject json, String url) {
        log.info("sendPost:json:" + json + " url:" + url);
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(10000);
        RestTemplate restTemplate = null;
        String resultStr = null;
        try {
            restTemplate = new RestTemplate(requestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<String>(json.toString(), headers);
            resultStr = restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            log.error("sendPost:json:" + json + " url:" + url, e);
            resultStr = null;
        }
        log.info("resultStr:" + resultStr);
        return resultStr;
    }
}
