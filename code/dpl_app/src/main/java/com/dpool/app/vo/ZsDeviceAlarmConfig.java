package com.dpool.app.vo;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2021-04-01
*/
@Table(name="zs_device_alarm_config")
public class ZsDeviceAlarmConfig   {
	
	/*
	主键	uuid
	*/
	private String cfgid ;
	/*
	接口状态预警1 开启 2 关闭
	*/
	private Integer interfacestatus ;
	/*
	离线状态预警	1 开启 2 关闭
	*/
	private Integer onlinestatus ;
	/*
	新增数据用户	对于用户表
	*/
	private String addby ;
	/*
	预警接收邮箱,多个逗号分隔
	*/
	private String email ;
	/*
	积分预警阔值
	*/
	private String mbpoint ;
	/*
	更新用户
	*/
	private String updateby ;
	/*
	入库时间
	*/
	private Date addtime ;
	/*
	最后更新时间
	*/
	private Date updatetime ;
	
	public ZsDeviceAlarmConfig() {
	}
	
	/**
	* 主键	uuid
	*@return 
	*/
	public String getCfgid(){
		return  cfgid;
	}
	/**
	* 主键	uuid
	*@param  cfgid
	*/
	public void setCfgid(String cfgid ){
		this.cfgid = cfgid;
	}
	
	/**
	* 接口状态预警1 开启 2 关闭
	*@return 
	*/
	public Integer getInterfacestatus(){
		return  interfacestatus;
	}
	/**
	* 接口状态预警1 开启 2 关闭
	*@param  interfacestatus
	*/
	public void setInterfacestatus(Integer interfacestatus ){
		this.interfacestatus = interfacestatus;
	}
	
	/**
	* 离线状态预警	1 开启 2 关闭
	*@return 
	*/
	public Integer getOnlinestatus(){
		return  onlinestatus;
	}
	/**
	* 离线状态预警	1 开启 2 关闭
	*@param  onlinestatus
	*/
	public void setOnlinestatus(Integer onlinestatus ){
		this.onlinestatus = onlinestatus;
	}
	
	/**
	* 新增数据用户	对于用户表
	*@return 
	*/
	public String getAddby(){
		return  addby;
	}
	/**
	* 新增数据用户	对于用户表
	*@param  addby
	*/
	public void setAddby(String addby ){
		this.addby = addby;
	}
	
	/**
	* 预警接收邮箱,多个逗号分隔
	*@return 
	*/
	public String getEmail(){
		return  email;
	}
	/**
	* 预警接收邮箱,多个逗号分隔
	*@param  email
	*/
	public void setEmail(String email ){
		this.email = email;
	}
	
	/**
	* 积分预警阔值
	*@return 
	*/
	public String getMbpoint(){
		return  mbpoint;
	}
	/**
	* 积分预警阔值
	*@param  mbpoint
	*/
	public void setMbpoint(String mbpoint ){
		this.mbpoint = mbpoint;
	}
	
	/**
	* 更新用户
	*@return 
	*/
	public String getUpdateby(){
		return  updateby;
	}
	/**
	* 更新用户
	*@param  updateby
	*/
	public void setUpdateby(String updateby ){
		this.updateby = updateby;
	}
	
	/**
	* 入库时间
	*@return 
	*/
	public Date getAddtime(){
		return  addtime;
	}
	/**
	* 入库时间
	*@param  addtime
	*/
	public void setAddtime(Date addtime ){
		this.addtime = addtime;
	}
	
	/**
	* 最后更新时间
	*@return 
	*/
	public Date getUpdatetime(){
		return  updatetime;
	}
	/**
	* 最后更新时间
	*@param  updatetime
	*/
	public void setUpdatetime(Date updatetime ){
		this.updatetime = updatetime;
	}
	

}
