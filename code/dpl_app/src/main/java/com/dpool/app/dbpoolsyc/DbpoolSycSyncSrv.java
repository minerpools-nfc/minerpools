package com.dpool.app.dbpoolsyc;

import java.util.HashMap;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.DbpoolSycService;
import com.dpool.app.util.AgentSvcTask;
import com.dpool.app.vo.ZsDeviceAlarmConfig;
import com.dpool.app.vo.ZsDpoolInterface;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步用户设备信息、流量和收益信息线程，默认每分钟同步一次
 * 
 * @author yyj
 * @add 2021-03-15 13:19
 */
@Slf4j
public class DbpoolSycSyncSrv extends AgentSvcTask {
	private DbpoolSycService dbpoolsycService = null;
	private ZsDpoolInterface interCfg;
	private String syncFlag = "all";// 同步标志 all 同步设备、用户、收益 device 同步设备 user同步用户 token收益
    public DbpoolSycSyncSrv(String syncFlag, ZsDpoolInterface interCfg) {
		this.syncFlag = syncFlag;
		this.interCfg = interCfg;
		dbpoolsycService = SpringContextUtils.getBean(DbpoolSycService.class);
		this.setName("DbpoolSycSyncSrv#");

	}

	protected void runTask() {

		try {
			if (interCfg == null) {
				interCfg = dbpoolsycService.getInterfacecfg();
			}
			// 读取当前告警
			HashMap<String, String> alarmMap = dbpoolsycService.getAllAlarm();
			ZsDeviceAlarmConfig zaconfig =dbpoolsycService.getAlarmConfig();
			if ("all".equals(syncFlag) || "device".equals(syncFlag)) {
				// 同步设备
				dbpoolsycService.syncDevice(interCfg, alarmMap,zaconfig);
			}
			if ("all".equals(syncFlag) || "user".equals(syncFlag)) {
				// 同步用户
				dbpoolsycService.syncDataUser(interCfg, alarmMap,zaconfig);
			}
			if ("all".equals(syncFlag) || "token".equals(syncFlag)) {
				// 同步收益
				dbpoolsycService.syncdataNfcflow(interCfg, alarmMap,zaconfig);
			}

		} catch (Exception e) {
			log.error("同步数据出错", e);
		}

	}
}
