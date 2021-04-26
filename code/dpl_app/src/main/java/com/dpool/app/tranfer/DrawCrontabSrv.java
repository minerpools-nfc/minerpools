package com.dpool.app.tranfer;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.TransferApplyService;
import com.dpool.app.util.AgentSvcTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 收益释放，提取  释放状态、提取状态 设置
 * @author maxinbo
 * @2021年3月30日
 * @TODO
 */
@Slf4j
public class DrawCrontabSrv extends AgentSvcTask{
	private static DrawCrontabSrv  instance;
	private DrawCrontabSrv() {
		this.setName("DrawCrontabSrv");
	}
	public  static synchronized DrawCrontabSrv  getInstance() {
		if(instance==null) {
			instance=new DrawCrontabSrv();
		}
		return instance;
	}
	
	protected void runTask() {
		TransferApplyService transferApplyService=SpringContextUtils.getBean(TransferApplyService.class);
		while(!shutdown) {
			try {
				String val=(String)this.getq(1000);
				if("run".equals(val)) {
					transferApplyService.butchClaTocken();
				}
			}catch (Exception e) {
				log.error("定时轮询更新收益释放、提取信息出错",e);
			}
		}
	}
}
