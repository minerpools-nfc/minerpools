package com.dpool.app.vo;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.util.Date;


/* 
* 
* gen by beetlsql 2021-03-26
*/
@Data
@Table(name="zs_draw_apply")
public class ZsDrawApply {
	@AssignID
	private String applyid ; /*主键	uuid*/
	private Integer transStatus ; /*提取状态	1 提币申请中 2 提币完成*/
	private String addby ; 
	private BigDecimal drawNum ; /*提成金额*/
	private String remark ; /*备注*/
	private BigDecimal shareNum ; /*提取金额*/
	private String updateby ; 
	private String userid ; /*申请人*/
	private String walletAddress ; /*钱包地址*/
	private Date addtime ; /*入库时间*/
	private Date updatetime ; /*最后更新时间*/

	private String transNo ; /*交易hash*/
	private Date transTime ; /*交易时间*/
}
