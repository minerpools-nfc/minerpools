package com.dpool.app.email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpool.app.email.mapper.EmailMapper;
import com.dpool.app.email.vo.ZsEmailSetting;

@Service
public class EmailService {

	@Autowired
	private  EmailMapper  dao;
	/**
	 * 获取邮件配置
	 * @return
	 */
	public   ZsEmailSetting  getEmailCfg() {
		List<ZsEmailSetting> list=dao.all();
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
