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
@Table(name="zs_devices")
public class ZsDevices   {
    @AssignID
	private String devicesn ; /*设备sn*/
	private Integer calmodel ; /*份数	算力份数*/
	private Integer deviceClass ; /*设备类型  1  物理token  2 云设备*/
	private Integer devicestatus ; /*设备状态	设备状态(0-未激活/1-正常/2-锁定/3-作废)*/
	private Integer lockdays ; /*锁仓时长（天）*/
	private Integer onlineStatus ; /*设备是否在线	1 在线  0离线*/
	private Integer releasedays ; /*线性释放时长（天）*/
	private Integer typeid ; /*设备分组id*/
	private BigDecimal calval ; /*算力	Mb/s*/
	private String deviceIp ; /*设备ip*/
	private String deviceLocaltion ; /*设备所在位置*/
	private String deviceVip ; /*虚拟ip*/
	private String owerName ; /*设备拥有者*/
	private Long owerid ; /*设备owerid*/
	private String referercode ; /*推荐码*/
	private String referername ; /*推荐人名字*/
	private Long refereruserid ; /*推荐人ID*/
	private BigDecimal shareRate ; /*分成比例*/
	private String walletAddress ; /*NFC地址*/
	private Date buytime ; /*购买时间*/
	private Date createtime ; /*创建时间*/
	private Date offlineTime ; /*最后下线时间*/
	private Date onlineTime ; /*最后在线时间*/

	private Integer deleteFlag;/*删除标志  1正常 2 删除*/
	private BigDecimal nfcNum;
}
