package com.dpool.app.vo;
import lombok.Data;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;


/*
 *
 * gen by beetlsql 2021-03-22
 */
@Data
@Table(name="zs_bind_users")
public class ZsBindUsers   {
	@AssignID
	private Long userid ;
	private Integer mbpointAlarm ; /*积分是否充足  1  积分不足   0积分充足*/
	private Integer userStatus ; /*用户状态 0-正常 1-未激活 2-锁定 3-作废 4-到期*/
	private String appid ; /*应用ID*/
	private String email ; /*邮件地址 (唯一)*/
	private String firstname ; /*用户姓*/
	private String lastname ; /*用户名*/
	private String loginname ; /*登录名*/
	private String loginpwd ; /*默认为空，界面设置*/
	private BigDecimal mbpoint ; /*用户积分*/
	private String nickname ; /*昵称*/
	private String partnerid ; /*合作伙伴ID*/
	private String phone ; /*手机号码 (唯一)*/
	private Date instime ; /*入库时间*/
	private Date updatetime ; /*最后更新时间*/

	private Integer deleteflag;/*删除标志  1正常 2 删除*/
}
