package com.dpool.app.vo;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2021-03-31
*/
@Table(name="zs_dpool_interface")
public class ZsDpoolInterface   {
	
	/*
	主键	uuid
	*/
	private String interid ;
	/*
	间隔时间  分钟
	*/
	private Integer intaval ;
	/*
	新增数据用户	对于用户表
	*/
	private String addby ;
	/*
	设备获取url
	*/
	private String httpurl ;
	/*
	接口名称
	*/
	private String interName ;
	/*
	收益获取url
	*/
	private String nfcurl ;
	/*
	更新用户
	*/
	private String updateby ;
	/*
	weline账号
	*/
	private String username ;
	/*
	weline密码
	*/
	private String userpwd ;
	/*
	入库时间
	*/
	private Date addtime ;
	/*
	最后更新时间
	*/
	private Date updatetime ;
	
	public ZsDpoolInterface() {
	}
	
	/**
	* 主键	uuid
	*@return 
	*/
	public String getInterid(){
		return  interid;
	}
	/**
	* 主键	uuid
	*@param  interid
	*/
	public void setInterid(String interid ){
		this.interid = interid;
	}
	
	/**
	* 间隔时间  分钟
	*@return 
	*/
	public Integer getIntaval(){
		return  intaval;
	}
	/**
	* 间隔时间  分钟
	*@param  intaval
	*/
	public void setIntaval(Integer intaval ){
		this.intaval = intaval;
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
	* 设备获取url
	*@return 
	*/
	public String getHttpurl(){
		return  httpurl;
	}
	/**
	* 设备获取url
	*@param  httpurl
	*/
	public void setHttpurl(String httpurl ){
		this.httpurl = httpurl;
	}
	
	/**
	* 接口名称
	*@return 
	*/
	public String getInterName(){
		return  interName;
	}
	/**
	* 接口名称
	*@param  interName
	*/
	public void setInterName(String interName ){
		this.interName = interName;
	}
	
	/**
	* 收益获取url
	*@return 
	*/
	public String getNfcurl(){
		return  nfcurl;
	}
	/**
	* 收益获取url
	*@param  nfcurl
	*/
	public void setNfcurl(String nfcurl ){
		this.nfcurl = nfcurl;
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
	* weline账号
	*@return 
	*/
	public String getUsername(){
		return  username;
	}
	/**
	* weline账号
	*@param  username
	*/
	public void setUsername(String username ){
		this.username = username;
	}
	
	/**
	* weline密码
	*@return 
	*/
	public String getUserpwd(){
		return  userpwd;
	}
	/**
	* weline密码
	*@param  userpwd
	*/
	public void setUserpwd(String userpwd ){
		this.userpwd = userpwd;
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
