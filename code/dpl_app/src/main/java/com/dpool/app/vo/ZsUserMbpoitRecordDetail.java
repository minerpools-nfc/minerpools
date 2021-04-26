package com.dpool.app.vo;

import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.util.Date;


/* 
* 
* gen by beetlsql 2021-03-19
*/
@Data
@Table(name="zs_user_mbpoit_record_detail")
public class ZsUserMbpoitRecordDetail {
	
	private String rdetailid ; /*设备id	uuid*/
	private Integer transferStatus ; /*转让状态*/
	private String faildMsg ; /*转让失败原因*/
	private Long fromuserid ; /*转出用户id*/
	private BigDecimal mbpoint ; /*转让积分*/
	private String recordid ; /*对应分红设置表主键*/
	private Long touserid ; /*转入用户*/
	private Date instime ; /*入库时间*/
	private Date transfertime ; /*分红时间	yyyy-MM-dd*/
	private Date updatetime ; /*最后更新时间*/
}
