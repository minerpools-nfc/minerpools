package com.dpool.app.email.vo;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


/* 
* 
* gen by beetlsql 2021-03-16
*/
@Table(name="dpools.zs_email_setting")
public class ZsEmailSetting   {
	
	/*
	主键	uuid
	*/
	private String emailid ;
	/*
	端口
	*/
	private Integer eamilport ;
	/*
	新增数据用户	对于用户表
	*/
	private String addby ;
	/*
	邮箱用户
	*/
	private String authname ;
	/*
	邮箱密码
	*/
	private String authpwd ;
	/*
	显示名称
	*/
	private String emailName ;
	/*
	邮箱地址
	*/
	private String emailaddr ;
	/*
	发件服务器	Eg :smtp.exmail.qq.com
	*/
	private String hostname ;
	/*
	ssl 发送模式
	*/
	private String sslEnable ;
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
	
	public ZsEmailSetting() {
	}
	
	/**
	* 主键	uuid
	*@return 
	*/
	public String getEmailid(){
		return  emailid;
	}
	/**
	* 主键	uuid
	*@param  emailid
	*/
	public void setEmailid(String emailid ){
		this.emailid = emailid;
	}
	
	/**
	* 端口
	*@return 
	*/
	public Integer getEamilport(){
		return  eamilport;
	}
	/**
	* 端口
	*@param  eamilport
	*/
	public void setEamilport(Integer eamilport ){
		this.eamilport = eamilport;
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
	* 邮箱用户
	*@return 
	*/
	public String getAuthname(){
		return  authname;
	}
	/**
	* 邮箱用户
	*@param  authname
	*/
	public void setAuthname(String authname ){
		this.authname = authname;
	}
	
	/**
	* 邮箱密码
	*@return 
	*/
	public String getAuthpwd(){
		return  authpwd;
	}
	/**
	* 邮箱密码
	*@param  authpwd
	*/
	public void setAuthpwd(String authpwd ){
		this.authpwd = authpwd;
	}
	
	/**
	* 显示名称
	*@return 
	*/
	public String getEmailName(){
		return  emailName;
	}
	/**
	* 显示名称
	*@param  emailName
	*/
	public void setEmailName(String emailName ){
		this.emailName = emailName;
	}
	
	/**
	* 邮箱地址
	*@return 
	*/
	public String getEmailaddr(){
		return  emailaddr;
	}
	/**
	* 邮箱地址
	*@param  emailaddr
	*/
	public void setEmailaddr(String emailaddr ){
		this.emailaddr = emailaddr;
	}
	
	/**
	* 发件服务器	Eg :smtp.exmail.qq.com
	*@return 
	*/
	public String getHostname(){
		return  hostname;
	}
	/**
	* 发件服务器	Eg :smtp.exmail.qq.com
	*@param  hostname
	*/
	public void setHostname(String hostname ){
		this.hostname = hostname;
	}
	
	/**
	* ssl 发送模式
	*@return 
	*/
	public String getSslEnable(){
		return  sslEnable;
	}
	/**
	* ssl 发送模式
	*@param  sslEnable
	*/
	public void setSslEnable(String sslEnable ){
		this.sslEnable = sslEnable;
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
