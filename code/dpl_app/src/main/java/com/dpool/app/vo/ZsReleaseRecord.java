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
@Table(name="zs_release_record")
public class ZsReleaseRecord {
	@AssignID
	private String releaseid ; /*主键	uuid*/
	private Integer drawStatus ; /*提取状态	1 未提币  2 提币申请中  3、 提币完成 4、提币失败*/
	private String applyid ; /*提币申请ID*/
	private String devicesn ; /*设备sn*/
	private String flowid ; /*主键	uuid*/
	private Double nfcNum ; /*获取NFC收益*/
	private Double shareRate ; /*分成比例*/
	private Double sharenum ; /*分成金额（NFC）*/
	private Double takenum ; /*提成金额（NFC） 每笔释放金额管理员抽成*/
	private Long userid ; /*用户id*/
	private Date ctime ; /*yyyy-MM-dd*/
	private Date instime ; /*入库时间*/
	private Date transtime ; /*转账时间*/
	private Date updatetime ; /*最后更新时间*/
}
