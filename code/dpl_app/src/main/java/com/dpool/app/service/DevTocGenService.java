package com.dpool.app.service;

import com.dpool.app.mapper.ZsDevnfMapper;
import com.dpool.app.mapper.ZsReleaseMapper;
import com.dpool.app.vo.ZsDeviceTockenRecord;
import com.dpool.app.vo.ZsDevices;
import com.dpool.app.vo.ZsReleaseRecord;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生成虚拟设备流量收益记录
 * @Author yyj
 * @Date 2021-04-15 13:19
 * @return
 */
@Slf4j
@Service
public class DevTocGenService {

    @Autowired
    private ZsDevnfMapper zsDevnfMapper;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 生成虚拟设备流量收益记录
	 * @return
	 */
	public String genVirTocken() {
		Date beforeday=getBeforeDay();
		String ctime=sdf.format(beforeday);
		List<ZsDevices> devs=zsDevnfMapper.getSQLManager().select("zsDeviceTockenRecord.queryVirdevs", ZsDevices.class);
		int successnum=0;
		int failnum=0;
		if(null!=devs&&devs.size()>0){
           for(ZsDevices dev:devs){
           	   try{
				   String devicesn=dev.getDevicesn();
				   BigDecimal nfcNum=dev.getNfcNum();
				   Integer deviceClass=dev.getDeviceClass();
				   Integer lockdays = dev.getLockdays();
				   Integer releasedays = dev.getReleasedays();
				   BigDecimal shareRate = dev.getShareRate();

				   String md5id = ctime + "_" + devicesn;
				   md5id = DigestUtils.md5Hex(md5id);
				   ZsDeviceTockenRecord nfcRecord = zsDevnfMapper.single(md5id);
				   Boolean isUpdate = false;
				   if (null != nfcRecord) {
					   isUpdate = true;
				   }
				   nfcRecord = new ZsDeviceTockenRecord();
				   nfcRecord.setFlowid(md5id);
				   nfcRecord.setDevicesn(devicesn);
				   nfcRecord.setDeviceClass(deviceClass);

				   if (isUpdate) {
					   nfcRecord.setUpdatetime(new Date());
					   zsDevnfMapper.updateTemplateById(nfcRecord);
				   } else {
					   nfcRecord.setFlowBill(new Double("0"));
					   nfcRecord.setMbpoint(new Double("0"));
					   nfcRecord.setCtime(beforeday);
					   nfcRecord.setNfcNum(nfcNum.doubleValue());
					   nfcRecord.setDrawnum(0.0);
					   nfcRecord.setDrawStatus(1);
					   nfcRecord.setReleaseStatus(1);
					   nfcRecord.setHasrelease(0.0);
					   nfcRecord.setLockdays(lockdays);
					   nfcRecord.setReleasedays(releasedays);
					   nfcRecord.setShareRate(shareRate.doubleValue());
					   BigDecimal sharenum = nfcNum.multiply(shareRate).divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP);
					   nfcRecord.setSharenum(sharenum.doubleValue());
					   BigDecimal takenum = nfcNum.subtract(sharenum);
					   nfcRecord.setTakenum(takenum.doubleValue());

					   nfcRecord.setTranstime(new Date());
					   nfcRecord.setTransFlag(1);

					   nfcRecord.setInstime(new Date());
					   zsDevnfMapper.insert(nfcRecord);
				   }
				   successnum++;
			   }catch (Exception e){
           	   	  log.error("记录"+dev.getDevicesn()+"生成收益数据报错",e);
				   failnum++;
			   }
		   }
		}
		return "生成虚拟设备"+ctime+"的收益数据成功"+successnum+"条,失败"+failnum+"条";
	}

	/**
	 * 获取前一天的时间
	 * @return
	 */
	private Date getBeforeDay() {
		Calendar cl = Calendar.getInstance();
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day - 1);
		return cl.getTime();
	}
}
