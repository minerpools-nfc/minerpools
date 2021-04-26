package com.dpool.app.service;

import com.dpool.app.mapper.ZsDevnfMapper;
import com.dpool.app.mapper.ZsReleaseMapper;
import com.dpool.app.vo.ZsDeviceTockenRecord;
import com.dpool.app.vo.ZsReleaseRecord;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ReleaseService {

    @Autowired
    private ZsReleaseMapper zsReleaseMapper;

    @Autowired
    private ZsDevnfMapper zsDevnfMapper;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 根据时间获取收益表数据
	 * @param ctime
	 * @return
	 */
	public   List<ZsDeviceTockenRecord>  getTokenRecord(String ctime){
    	Map<String,Object>  param=new HashMap<String, Object>();
		param.put("ctime", ctime);
    	return  zsReleaseMapper.getSQLManager().select("zsReleaseRecord.queryTockenRs", ZsDeviceTockenRecord.class,param);
       
    }

	/**
	 * 根据收益表生成线性释放表数据
	 * @param ctime
	 * @param tcs
	 * @return
	 */
    public String gendata(String ctime,List<ZsDeviceTockenRecord> tcs) {
    	double relNumtol=0.0;
    	try {
//    		updateTokenRecord(ctime);
         Date date=sdf.parse(ctime);
         List<ZsReleaseRecord>  zrdList=new ArrayList<ZsReleaseRecord>();
         for (ZsDeviceTockenRecord otr : tcs) {
             try {
            	double dayreleasenum= calDayReleaseNum(otr);//日释放量
            	if(otr.getNfcNum()-otr.getHasrelease()<=dayreleasenum) {
            		dayreleasenum=otr.getNfcNum()-otr.getHasrelease();
            	}
            	if(dayreleasenum<=0) {
            		continue;
            	}
            	relNumtol+=dayreleasenum;
            	ZsReleaseRecord rr = new ZsReleaseRecord();
            	rr.setReleaseid(DigestUtils.md5Hex(otr.getFlowid()+date+otr.getDevicesn()));
                rr.setFlowid(otr.getFlowid());
                rr.setCtime(date);
                rr.setDevicesn(otr.getDevicesn());
                rr.setUserid(otr.getOwerid());
                rr.setShareRate(otr.getShareRate());
                rr.setSharenum(dayreleasenum*otr.getShareRate()*0.01);
                rr.setTakenum(dayreleasenum-rr.getSharenum());
                rr.setNfcNum(dayreleasenum);
                rr.setDrawStatus(1);
                rr.setInstime(new Date());
                rr.setUpdatetime(rr.getInstime());
                zrdList.add(rr);
             }catch (Exception e) {
            	 log.error("生成释放记录" + otr + "报错", e);
			}
         }
         if(zrdList.size()>0) {
        	 zsReleaseMapper.insertBatch(zrdList);
        	 updateTokenRecord(ctime);
         }
    	}catch (Exception e) {
			log.error("计算释放数据出错",e);
		}
    	return "释放"+relNumtol+"NFC";
    }
    
    /**
     * 更新收益表释放状态和释放值
     */
    public void updateTokenRecord(String ctime) {
    	try {
    		Map<String,Object>  param=new HashMap<String, Object>();
    		param.put("ctime", ctime);
    	 List<ZsDeviceTockenRecord> tcList = zsReleaseMapper.getSQLManager().select("zsReleaseRecord.queryButchSumReleasenum", ZsDeviceTockenRecord.class,param);
    	if(tcList!=null&&tcList.size()>0) {
    		for(ZsDeviceTockenRecord  info:tcList) {
    			if(info.getHasrelease()!=null&&info.getHasrelease().doubleValue()==info.getNfcNum().doubleValue()) {
    				info.setReleaseStatus(2);
    			}else {
    				info.setReleaseStatus(3);
    			}
    		}
    		zsReleaseMapper.getSQLManager().updateBatchTemplateById(ZsDeviceTockenRecord.class, tcList);
    	}
    	}catch (Exception e) {
			log.error("更新收益表释放值出错",e);
		}
    }

	/**
	 * 计算日释放nfc数量
	 * @param info
	 * @return
	 * @throws Exception
	 */
    private double calDayReleaseNum(ZsDeviceTockenRecord  info) throws Exception{
    	if(info.getReleasedays()==null||info.getReleasedays().intValue()==0) {
    		return info.getNfcNum().doubleValue();
    	}
    	return info.getNfcNum().doubleValue()/info.getReleasedays();
    }


    public String genDrawdata() {

       return null;
    }
}
