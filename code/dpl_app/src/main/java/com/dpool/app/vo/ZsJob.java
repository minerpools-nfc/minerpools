package com.dpool.app.vo;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


/* 
* 
* gen by beetlsql 2021-03-22
*/
@Data
@Table(name="zs_job")
public class ZsJob {
	@AssignID
	private String jobid ; /*主键 uuid*/
	private Integer execresult ; /*最后执行结果,1 成功 0 失败*/
	private Integer jobEnable ; /*是否启用,1 启用  0 停止*/
	private Integer jobServers ; /*服务号*/
	private String actPath ; /*线程路径*/
	private String execmsg ; /*执行结果*/
	private String jobCron ; /*执行策略,crontab格式*/
	private String jobName ; /*任务名称*/
	private String updateby ; /*更新用户*/
	private Date endtime ; /*任务结束时间*/
	private Date exectime ; /*最后执行时间*/
	private Date starttime ; /*任务开始时间*/
	private Date updatetime ; /*最后更新时间*/
}
