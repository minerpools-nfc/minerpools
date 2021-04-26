package com.dpool.app.controller;

import javax.servlet.http.HttpServletRequest;

import com.dpool.app.cache.RequsetLimit;
import com.dpool.app.service.TransferService;
import com.dpool.app.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.dpool.app.vo.TransferParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 积分转让接口
 * @return
 */
@Slf4j
@RestController
@RequestMapping("v1/nfc/")
public class TransferController {

	@Autowired
	private TransferService transferService;

	/**
	 * 用户积分转让
	 * @param param
	 *    "mtbutch":"xxx",//zs_user_mbpoit_record_detail 的mtbutch 字段值 批次
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "transfermbp")
	@ResponseBody
	public String transfermbp(@RequestBody TransferParam  param, HttpServletRequest request) {
		JSONObject result = new JSONObject(true);
		result.put("result", 0);
		result.put("errmsg", "");
		try {
			if(!RequsetLimit.checkLimit(Utils.getRemortIP(request),result,"nfc/transfermbp")){
				return result.toString();
			}
			String mtbutch=param.getMtbutch();
			if(null==mtbutch||"".equals(mtbutch.trim())){
				result.put("result", 101);
				result.put("errmsg", "mtbutch is empty");
			}else{
				transferService.startTransferMbp(param,result);
			}
		} catch (Exception e) {
			log.error("转账请求报错", e);
			result.put("result", 650);
			result.put("errmsg", "transfer failed!");
		}
		return result.toJSONString();
	}
}
