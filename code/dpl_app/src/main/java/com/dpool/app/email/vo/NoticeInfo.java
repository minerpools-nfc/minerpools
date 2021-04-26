package com.dpool.app.email.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * 
 * @author maxinbo
 * @2021年3月16日
 * @TODO
 */
@Data
public class NoticeInfo {

	private  String alarmTitle;
	private  String alarmMsg;
	private  List<String> recevAddrList=new ArrayList<String>();
}
