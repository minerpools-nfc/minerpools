package com.dpool.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.dpool.app.cache.RequsetLimit;
import com.dpool.app.service.TransferApplyService;
import com.dpool.app.tranfer.DrawCrontabSrv;
import com.dpool.app.util.RSAUtils;
import com.dpool.app.util.Utils;
import com.dpool.app.vo.TransferApplyParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 提取申请打币接口
 * @return
 */
@Slf4j
@RestController
@RequestMapping("v1/nfc/")
public class TransferApplyController {

	@Value("${transfer.apply.bc.key.path:}")
	private String keyPath;

	@Autowired
	private TransferApplyService transferApplyService;
	/**
	 * 提取统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "apply")
	@ResponseBody
	public String apply(HttpServletRequest request) {
		JSONObject result = new JSONObject(true);
		result.put("result", 0);
		result.put("errmsg", "");
		if(!RequsetLimit.checkLimit(Utils.getRemortIP(request),result,"nfc/apply")){
			return result.toString();
		}
		DrawCrontabSrv.getInstance().putq("run");
		return "ok";
	}
	/**
	 * 提取申请打币
	 * @param param
	 *    "applyid":"xxx",//zs_draw_apply提取申请表id
	 *    "prikey":"xxeee" RSA私钥字符串
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "transferapply")
	@ResponseBody
	public String transfer(@RequestBody TransferApplyParam param, HttpServletRequest request) {
		JSONObject result = new JSONObject(true);
		result.put("result", 0);
		result.put("errmsg", "");
		try {
			if(!RequsetLimit.checkLimit(Utils.getRemortIP(request),result,"nfc/transferapply")){
				return result.toString();
			}
			String prikey=param.getPrikey();
			String applyid=param.getApplyid();
			if(null==prikey||null==applyid||"".equals(prikey.trim())
					||"".equals(applyid.trim())){
				result.put("result", 101);
				result.put("errmsg", "param is empty");
			}else{
				if(!decryptkey(param,result)) {
					return result.toString();
				}
				transferApplyService.startTransferDigitalMoney(param,result);
			}
		} catch (Exception e) {
			log.error("转账请求报错", e);
			result.put("result", 650);
			result.put("errmsg", "transfer failed!");
		}
		return result.toJSONString();
	}

	/**
	 * 用传入私钥和在配置文件key.txt公钥进行RSA解码得到区块链账户私钥
	 * @param param
	 * @param result
	 * @return
	 */
	private  boolean decryptkey(TransferApplyParam param,JSONObject result) {
		try {
			if(param.getPrikey()==null||"".equals(param.getPrikey())){
				log.info("RSA解密密码为空"+param.getPrikey()+" "+param.getApplyid());
				result.put("result", -1);
				result.put("errmsg", "私钥必填!");
				return false;
			}
			File file = new File(keyPath.endsWith("/") ? keyPath + "key.txt" : keyPath+ "/key.txt");
			String keyStr=txt2String(file);
			String privateKey=param.getPrikey();
			privateKey=privateKey.replaceAll("\r\n","");
			param.setPrikey(RSAUtils.decryptByPrivateKey(privateKey,keyStr));
			return true;
		}catch (Exception e) {
			result.put("result", -1);
			result.put("errmsg", "公钥输入错误，秘钥解密失败");
			log.error("对上传的加密密码进行解密出错",e);
		}
		return false;
	}

	/**
	 * 读取配置文件key.txt公钥
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String txt2String(File file) throws IOException {
		String result = "";
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
				result = result+ s;
			}
		} catch (Exception e) {
			log.error("自动打币读取key.txt出错", e);
			throw e;
		}finally {
			if(null!=br){
				br.close();
				br=null;
			}
		}
		return result;
	}

}
