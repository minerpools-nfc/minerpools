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
@Table(name="zs_job_log")
public class ZsJobLog {
	@AssignID
	private String id ; /*主键 uuid*/
	private Integer status ; /*执行结果 1 成功 0 失败 */
	private String jobDetail ; /*执行详细内容*/
	private String jobid ; /*对应定时任务主键*/
	private String jobmsg ; /*执行内容*/
	private Long speeds ; /*消耗时间 ms*/
	private Date exectime ; /*执行时间*/
	private Date instime ; /*入库时间*/
}
