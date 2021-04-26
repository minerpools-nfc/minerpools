package com.dpool.app.vo;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


/* 
* 
* gen by beetlsql 2021-03-18
*/
@Data
@Table(name="zs_device_alarm")
public class ZsDeviceAlarm {
	@AssignID
	private String alarmid ; /*主键	uuid*/
	private Integer alarmType ; /*类型  	1 发生告警 0 恢复/删除告警*/
	private Integer servity ; /*告警等级 1 、恢复 5、通知 6、严重 7、紧急*/
	private String alarmMsg ; /*告警内容*/
	private String cfgType ; /*预计类型 interfacestatus 接口,onlinestatus 在线,mbpoint 积分*/
	private String devicesn ; /*设备SN/用户loginname*/
	private Date firsttime ; /*首次发生时间*/
	private Date instime ; /*入库时间*/
	private Date lasttime ; /*最后发生时间*/
	private Long userid ;/*用户ID*/
	private String alarmTitle ;/*告警标题*/
}
