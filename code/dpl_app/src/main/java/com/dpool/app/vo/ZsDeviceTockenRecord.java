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
@Table(name="zs_device_tocken_record")
public class ZsDeviceTockenRecord   {
	@AssignID
	private String flowid ; /*主键	为ctime+devicesn的md5值*/
	private Integer lockdays ; /*锁仓时长（天)*/
	private Integer releasedays ; /*线性释放时长（天）*/
	private Integer transFlag ; /*转账状态,1 成功  2失败   0 待转账  3划转中*/
	private String devicesn ; /*设备sn*/
	private Double flowBill ; /*托管流量*/
	private Double hasrelease ; /*已释放数量*/
	private Double mbpoint ; /*消耗积分*/
	private Double nfcNum ; /*获取NFC收益*/
	private Double shareRate ; /*分成比例*/
	private Double sharenum ; /*分成金额（NFC）*/
	private Double takenum ; /*提成金额（NFC）每笔释放金额管理员抽成*/
	private String transferNo ; /*转账流水号,区块链转账时生成的，转账成功后回填*/
	private Date ctime ; /*yyyy-MM-dd*/
	private Date instime ; /*入库时间*/
	private Date transtime ; /*转账时间*/
	private Date updatetime ; /*最后更新时间*/

	private Integer releaseStatus ;/*释放状态  1 未释放、2、释放完成（释放量==收益）3部分完成*/
	private Double drawnum ; /*'已提取数量*/
	private Integer drawStatus ;/*提取状态  1 未提取、2、提取完成（提取量==释放量）、3、部分完成*/
	private Date lastDrawtime ; /*'最后提取时间'*/
	private Long owerid;

	private Integer deviceClass;/*设备类型  1  物理token  2 云设备,3 虚拟租用设备*/
}
