package com.dpool.app.cache;

import com.alibaba.fastjson.JSONObject;
import com.dpool.app.framework.config.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class RequsetLimit {

    private static long perTime;
    private static long limitCount;
    private static HashMap userIpcache=new HashMap();

    /**
     * 根据IP限制请求频率
     * @return true
     * @throws Exception
     */
    public static boolean checkLimit(String ipaddr, JSONObject result,String method){
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String key=ipaddr+method;
         String value= (String) userIpcache.get(key);
         if(value!=null&&value.indexOf("#")!=-1){
             String atime=value.split("#")[0];
             String count=value.split("#")[1];
             Date atimeD= null;
             try {
                 atimeD = formatter.parse(atime);
             } catch (ParseException e) {
                 atimeD=new Date();
             }
             Date nowD=new Date();
             Date perTimeEnd=new Date(atimeD.getTime()+perTime);
             long addcount=Long.parseLong(count)+1;
             userIpcache.put(key,atime+"#"+addcount);
             if(perTimeEnd.compareTo(nowD)>0){
                 if(addcount>limitCount){
                     log.info("Access through "+key+" is too frequent, please try again after "+(perTime/1000.0)+" seconds!");
                     result.put("result","14");
                     result.put("errmsg","Access through "+key+" is too frequent, please try again after "+(perTime/1000.0)+" seconds!");
                     return false;
                 }
             }else{
                 userIpcache.put(key,formatter.format(new Date())+"#"+1);
             }
         }else{
             userIpcache.put(key,formatter.format(new Date())+"#"+1);
         }
         return true;
    }

    /**
     * 读取配置文件配置限制时间和次数
     */
    public static void init(){
        Environment env=SpringContextUtils.getBean(Environment.class);
        perTime=Long.parseLong(env.getProperty("requsetLimit.perTime","1"))*60*1000;
        limitCount=Long.parseLong(env.getProperty("requsetLimit.limitCount","10"));
    }

}
