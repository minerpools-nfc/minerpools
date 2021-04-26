package com.dpool.app.email;

import org.apache.commons.mail.SimpleEmail;

import com.alibaba.fastjson.JSONObject;
import com.dpool.app.email.service.EmailService;
import com.dpool.app.email.vo.NoticeInfo;
import com.dpool.app.email.vo.ZsEmailSetting;
import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.util.AgentSvcTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一发送邮件
 * @author maxinbo
 * @2021年3月16日
 * @TODO
 */
@Slf4j
public class EmailSendSrv  extends AgentSvcTask {

	private  EmailService  service;
	
	private static EmailSendSrv instance;
	private  EmailSendSrv() {
		this.setName("email#notice");
		service=SpringContextUtils.getBean(EmailService.class);
	}
	public synchronized static EmailSendSrv  getInstance() {
		if(instance==null) {
			instance=new EmailSendSrv();
		}
		return instance;
	}
	/**
	 * 邮件run
	 */
	protected  void runTask() {
		while(!shutdown) {
			try {
				NoticeInfo info=(NoticeInfo)this.getq(1000);
				if(info!=null) {
					transferSendMail(info);
				}
			}catch (Exception e) {
				log.error("邮件发送主方法报错",e);
			}
		}
	}
	/**
     * 自动打币发邮件
     *
     * @param detail
     * @return
     */
    public void transferSendMail(NoticeInfo info) {
         if (null != info && info.getRecevAddrList().size() > 0&&info.getAlarmMsg()!=null) {
        	ZsEmailSetting  setinfo= service.getEmailCfg();
            try {
            	if(setinfo==null) {
            		log.warn("系统未配置发送邮件信息");
            		return ;
            	}
                SimpleEmail simpleEmail = new SimpleEmail();
                simpleEmail.setHostName(setinfo.getHostname());
                simpleEmail.setAuthentication(setinfo.getAuthname(), setinfo.getAuthpwd());
                simpleEmail.setSSLOnConnect(true);
                simpleEmail.setSmtpPort(setinfo.getEamilport());
                simpleEmail.setFrom(setinfo.getEmailaddr(), setinfo.getEmailName(), "UTF-8");
                simpleEmail.addTo(info.getRecevAddrList().toArray(new String[info.getRecevAddrList().size()]));
                simpleEmail.setSubject(info.getAlarmTitle());
                simpleEmail.setCharset("utf-8");
                simpleEmail.setMsg(info.getAlarmMsg());
                simpleEmail.send();
                log.info("向"+info.getRecevAddrList()+"发送邮件成功; title="+info.getAlarmTitle()+" msg="+info.getAlarmMsg());
            } catch (Exception e) {
            	log.warn( "发送原因:"+JSONObject.toJSONString(info)+"  :  " + e.getMessage());
                log.info("邮箱发送报错", e);
               
            }
        } else {
            log.warn("告警未配置 任何接收邮件"+JSONObject.toJSONString(info));
        }
    }
}
