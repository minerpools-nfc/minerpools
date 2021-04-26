package com.dpool.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpool.app.email.EmailSendSrv;
import com.dpool.app.email.vo.NoticeInfo;
import com.dpool.app.mapper.DalarmMapper;
import com.dpool.app.mapper.DevsMapper;
import com.dpool.app.mapper.ZbuMapper;
import com.dpool.app.mapper.ZsDevnfMapper;
import com.dpool.app.util.HmacSha256Util;
import com.dpool.app.vo.ZsBindUsers;
import com.dpool.app.vo.ZsDeviceAlarm;
import com.dpool.app.vo.ZsDeviceAlarmConfig;
import com.dpool.app.vo.ZsDeviceTockenRecord;
import com.dpool.app.vo.ZsDevices;
import com.dpool.app.vo.ZsDpoolInterface;
import com.github.kevinsawicki.http.HttpRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步用户设备信息、流量和收益信息
 *
 * @Author yyj
 * @Date 2021-03-15 13:19
 */
@Service
@Slf4j
public class DbpoolSycService {

    @Value("${dbpoolSycService.secretkey}")
    private String secretkey;
    @Autowired
    DevsMapper devsMapper;

    @Autowired
    ZsDevnfMapper zsDevnfMapper;

    @Autowired
    DalarmMapper dalarmMapper;

    @Autowired
    ZbuMapper zbuMapper;


    /**
     * 获取接口配置
     * @return
     */
    public  ZsDpoolInterface getInterfacecfg() {
    	return devsMapper.getSQLManager().selectSingle("zsDevices.queryInterface", null, ZsDpoolInterface.class);
    }
    /**
     * 同步设备
     */
   public  synchronized void syncDevice(ZsDpoolInterface intercfg
		   ,HashMap<String, String> alarmMap,ZsDeviceAlarmConfig zaconfig) {
	   try {
		   log.info("开始同步账号"+intercfg.getUsername()+"所管理设备数据");
           List<Map> deviceList = getPostData(intercfg.getHttpurl(), intercfg.getUsername()
        		   , intercfg.getUserpwd(), "device", alarmMap,zaconfig);
           if (deviceList != null) {
               //获取所有设备表数据进行对比
               Map allDevs=getAllDevices();
               try {
                   setDeviceDelFlag(deviceList,allDevs);
               } catch (Exception e) {
                   log.error("setDeviceDelFlag报错:", e);
               }
               HashMap<String, String> lockNums = getLockNum();
               for (Map od : deviceList) {
                   try {
                       insertOrUpdateDev(od, alarmMap, lockNums,allDevs,zaconfig);
                   } catch (Exception e) {
                       log.error("insertOrUpdateDev报错:", e);
                   }
               }
           }
           log.info("同步账号"+intercfg.getUsername()+"所管理设备数据完成!");
       } catch (Exception e) {
           log.error("同步设备报错:", e);
       }
   }
    /**
     * 同步用户数据
     * @param intercfg 接口配置
     * @param alarmMap 库中所有告警Map
     * @param zaconfig 预警配置
     */
   public void syncDataUser(ZsDpoolInterface intercfg, HashMap<String, String> alarmMap
   		,ZsDeviceAlarmConfig zaconfig) {
       try {
       	 log.info("开始同步账号"+intercfg.getUsername()+"所管理用户数据");
           List<Map> userlist = getPostData(intercfg.getHttpurl(), intercfg.getUsername()
           		, intercfg.getUserpwd(), "user", alarmMap,zaconfig);
           if (userlist != null) {
               //获取全部用户
               Map uersMap=getAllUsers();
               try {
                   setUserDelFlag(userlist,uersMap);
                   
               } catch (Exception e) {
                   log.error("setDeviceDelFlag报错:", e);
               }
               for (Map od : userlist) {
                   try {
                       insertOrUpdateUser(od, alarmMap,uersMap,zaconfig);
                   } catch (Exception e) {
                       log.error("insertOrUpdateUser报错:", e);
                   }
               }
           }
           log.info("同步账号"+intercfg.getUsername()+"所管理用户数据完成");
       } catch (Exception e) {
           log.error("getDataUser报错:", e);
       }
   }

    /**
     * 获取全局配置，设置的锁仓时长、线性释放时长、默认分成比例值
     * @return
     */
    private HashMap<String, String> getLockNum() {
        HashMap<String, String> lockM = new HashMap<>();
        try {
            List<Map> lockls = devsMapper.getSQLManager().select("zsDevices.queryLockNum", Map.class, null);

            if (lockls != null && lockls.size() > 0) {
                for (Map lp : lockls) {
                    String pflag = (String) lp.get("propFlag");
                    String pvalue = (String) lp.get("propValue");
                    lockM.put(pflag, pvalue);
                }
            }
        } catch (Exception e) {
            log.error("获取锁仓参数报错", e);
        }
        return lockM;
    }

    /**
     * 获取库中所有告警
     *
     * @return
     */
    public HashMap<String, String> getAllAlarm() {
        HashMap<String, String> alarmMap = new HashMap<>();
        try {
            List<ZsDeviceAlarm> zsDevalarmList = devsMapper.getSQLManager().all(ZsDeviceAlarm.class);

            if (zsDevalarmList != null) {
                for (ZsDeviceAlarm alarm : zsDevalarmList) {
                    if(null!=alarm.getDevicesn()){
                        alarmMap.put(alarm.getDevicesn() + "" + alarm.getCfgType(), alarm.getAlarmid());
                    }else if(null!=alarm.getUserid()){
                        alarmMap.put(alarm.getUserid() + "" + alarm.getCfgType(), alarm.getAlarmid());
                    }else if(null!=alarm.getCfgType()){
                        alarmMap.put(alarm.getCfgType(), alarm.getAlarmid());
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取告警失败", e);
        }
        return alarmMap;
    }

    /**
     * 告警配置
     * @return
     * @throws Exception
     */
    public  ZsDeviceAlarmConfig  getAlarmConfig() throws Exception{
       List<ZsDeviceAlarmConfig> list= devsMapper.getSQLManager().all(ZsDeviceAlarmConfig.class);
       if(list!=null&&list.size()>0) {
    	   return list.get(0);
       }
       return null;
    }

    /**
     * 获取所有用户信息
     * @return
     */
    private Map getAllUsers() {
        HashMap<Long, ZsBindUsers> usersMap = new HashMap<>();
        try {
            List<ZsBindUsers> usersList = devsMapper.getSQLManager().all(ZsBindUsers.class);

            if (usersList != null) {
                for (ZsBindUsers user : usersList) {
                    usersMap.put(user.getUserid(),user);
                }
            }
        } catch (Exception e) {
            log.error("getAllUsers失败", e);
        }
        return usersMap;
    }

    /**
     * 设置用户是否被删除了
     * @param dl
     * @param uersMap
     */
    private void setUserDelFlag(List<Map> dl, Map<Long, ZsBindUsers> uersMap) {
        List<Long> userids=new ArrayList<>();
        for (Map od : dl) {
            Long userid =null;
            if(od.get("userid") instanceof Integer){
                userid = Long.parseLong(od.get("userid").toString());
            }else{
                userid = (Long) od.get("userid");
            }
            userids.add(userid);
        }
        List<Long> filterSns=new ArrayList<>();
        if(userids.size()>0){
            for (Long key : uersMap.keySet()) {
                if(!userids.contains(key)){
                    filterSns.add(key);
                }
            }
        }
        if(filterSns.size()>0){
            Map p=new HashMap();
            p.put("userids",filterSns);
            devsMapper.getSQLManager().update("zsDevices.updateUserDelFlag",p);
        }
    }

    /**
     * 插入或者更新用户数据
     * @param data
     * @param alarmMap
     * @param uersMap
     */
    private void insertOrUpdateUser(Map data, HashMap<String, String> alarmMap
    		, Map uersMap,ZsDeviceAlarmConfig zaconfig) {
        Long userid =null;
        if(data.get("userid") instanceof Integer){
            userid = Long.parseLong(data.get("userid").toString());
        }else{
            userid = (Long) data.get("userid");
        }
        if (null == userid) {
            log.info("user:" + data + "userid");
            return;
        }
        String loginname = (String) data.get("loginname");
        String nickname = (String) data.get("nickname");
        String firstname = (String) data.get("firstname");
        String lastname = (String) data.get("lastname");
        String appid = (String) data.get("appid");
        String partnerid = (String) data.get("partnerid");
        BigDecimal mbpoint = (BigDecimal) data.get("mbpoint");
        Integer userStatus = (Integer) data.get("userstatus");
        ZsBindUsers oldzdr = (ZsBindUsers) uersMap.get(userid);
      
        ZsBindUsers zdr = new ZsBindUsers();
        zdr.setUserid(userid);
        zdr.setLoginname(loginname);
        zdr.setNickname(nickname);
        zdr.setFirstname(firstname);
        zdr.setLastname(lastname);
        zdr.setAppid(appid);
        zdr.setPartnerid(partnerid);
        zdr.setMbpoint(mbpoint);
        zdr.setUserStatus(userStatus);
        zdr.setPhone(data.get("phone")==null?"": (String) data.get("phone"));
        zdr.setEmail( data.get("email")==null?"":(String) data.get("email"));
        if(null!=mbpoint){
        	String alarmId = alarmMap.get(userid + "mbpoint");
            dealMbpointAlarm(mbpoint, userid, zdr, alarmId, zaconfig);
        }
        //zdr.setMbpointAlarm();
        zdr.setDeleteflag(1);
        Boolean isUpdate = false;
        if (null != oldzdr) {
        	if(zdr.getUserStatus()!=null&&oldzdr.getUserStatus()!=1) {
        		zdr.setUserStatus(null);
        	}
            isUpdate = true;
        }
        if (isUpdate) {
            Map changeFs=userChangeFields(zdr,oldzdr);
            if(null!=changeFs&&changeFs.size()>0){
                changeFs.put("updatetime",new Date());
                Map p=new HashMap();
                p.put("userid",userid);
                p.put("fields",changeFs);
                devsMapper.getSQLManager().update("zsDevices.updateUserWithNull",p);
            }
        } else {
            zdr.setInstime(new Date());
            zbuMapper.insert(zdr);
        }
    }

    /**
     * 判断用户信息是否变化
     * @param zdr
     * @param oldzdr
     * @return
     */
    private Map userChangeFields(ZsBindUsers zdr, ZsBindUsers oldzdr) {
        Map fieldChange=new HashMap();
        String loginname=zdr.getLoginname();
        String oldloginname=oldzdr.getLoginname();
        if((loginname==null&&oldloginname==null)||(loginname!=null&&oldloginname!=null&&loginname.equals(oldloginname))){

        }else{
            if(zdr.getLoginname()!=null){
                fieldChange.put("loginname",zdr.getLoginname());
            }else{
                fieldChange.put("loginname","null");
            }
        }

        String nickname=zdr.getNickname();
        String oldnickname=oldzdr.getNickname();
        if((nickname==null&&oldnickname==null)||(nickname!=null&&oldnickname!=null&&nickname.equals(oldnickname))){

        }else{
            if(zdr.getNickname()!=null){
                fieldChange.put("nickname",zdr.getNickname());
            }else{
                fieldChange.put("nickname","null");
            }
        }

        String firstname=zdr.getFirstname();
        String oldfirstname=oldzdr.getFirstname();
        if((firstname==null&&oldfirstname==null)||(firstname!=null&&oldfirstname!=null&&firstname.equals(oldfirstname))){

        }else{
            if(zdr.getFirstname()!=null){
                fieldChange.put("firstname",zdr.getFirstname());
            }else{
                fieldChange.put("firstname","null");
            }
        }
        String lastname=zdr.getLastname();
        String oldlastname=oldzdr.getLastname();
        if((lastname==null&&oldlastname==null)||(lastname!=null&&oldlastname!=null&&lastname.equals(oldlastname))){

        }else{
            if(zdr.getLastname()!=null){
                fieldChange.put("lastname",zdr.getLastname());
            }else{
                fieldChange.put("lastname","null");
            }
        }

        String appid=zdr.getAppid();
        String oldappid=oldzdr.getAppid();
        if((appid==null&&oldappid==null)||(appid!=null&&oldappid!=null&&appid.equals(oldappid))){

        }else{
            if(zdr.getAppid()!=null){
                fieldChange.put("appid",zdr.getAppid());
            }else{
                fieldChange.put("appid","null");
            }
        }

        String partnerid=zdr.getPartnerid();
        String oldpartnerid=oldzdr.getPartnerid();
        if((partnerid==null&&oldpartnerid==null)||(partnerid!=null&&oldpartnerid!=null&&partnerid.equals(oldpartnerid))){

        }else{
            if(zdr.getPartnerid()!=null){
                fieldChange.put("partnerid",zdr.getPartnerid());
            }else{
                fieldChange.put("partnerid","null");
            }
        }

        BigDecimal mbpoint=zdr.getMbpoint();
        BigDecimal oldmbpoint=oldzdr.getMbpoint();
        if((mbpoint==null&&oldmbpoint==null)||(mbpoint!=null&&oldmbpoint!=null&&mbpoint.compareTo(oldmbpoint)==0)){

        }else{
            if(zdr.getMbpoint()!=null){
                fieldChange.put("mbpoint",zdr.getMbpoint());
            }else{
                fieldChange.put("mbpoint","null");
            }
        }
        Integer mbpointAlarm=zdr.getMbpointAlarm();
        Integer oldmbpointAlarm=oldzdr.getMbpointAlarm();
        if((mbpointAlarm==null&&oldmbpointAlarm==null)||(mbpointAlarm!=null&&oldmbpointAlarm!=null&&mbpointAlarm==oldmbpointAlarm)){

        }else{
            if(zdr.getMbpointAlarm()!=null){
                fieldChange.put("mbpoint_alarm",zdr.getMbpointAlarm());
            }else{
                fieldChange.put("mbpoint_alarm","null");
            }
        }

        String phone=zdr.getPhone();
        String oldphone=oldzdr.getPhone();
        if((phone==null&&oldphone==null)||(phone!=null&&oldphone!=null&&phone.equals(oldphone))){

        }else{
            if(zdr.getPhone()!=null){
                fieldChange.put("phone",zdr.getPhone());
            }else{
                fieldChange.put("phone","null");
            }
        }
        String email=zdr.getEmail();
        String oldemail=oldzdr.getEmail();
        if((email==null&&oldemail==null)||(email!=null&&oldemail!=null&&email.equals(oldemail))){

        }else{
            if(zdr.getEmail()!=null){
                fieldChange.put("email",zdr.getEmail());
            }else{
                fieldChange.put("email","null");
            }
        }

        Integer deleteFlag=zdr.getDeleteflag();
        Integer olddeleteFlag=oldzdr.getDeleteflag();
        if((deleteFlag==null&&olddeleteFlag==null)||(deleteFlag!=null&&olddeleteFlag!=null&&deleteFlag==olddeleteFlag)){
            zdr.setDeleteflag(null);
        }else{
            if(zdr.getDeleteflag()!=null){
                fieldChange.put("deleteflag",zdr.getDeleteflag());
            }else{
                fieldChange.put("deleteflag","null");
            }
        }

        return fieldChange;
    }

    /**
     * 处理积分预警，如果积分不足则发送邮件给用户
     * @param mbpoint
     * @param userid
     * @param zdr
     * @param alarmId
     * @param zaconfig
     */
    private void dealMbpointAlarm(BigDecimal mbpoint, Long userid, ZsBindUsers zdr
    		, String alarmId,ZsDeviceAlarmConfig zaconfig) {
            String key = getMKey();
            if(zaconfig.getMbpoint()==null) {
            	return;//不告警
            }
            if (mbpoint.doubleValue()<=Double.parseDouble(zaconfig.getMbpoint())) {
                zdr.setMbpointAlarm(1);
                String uName=userid + "";
                if (zdr.getLoginname() != null) {
                    uName=zdr.getLoginname();
                } else if (zdr.getFirstname() != null && zdr.getLastname() != null) {
                    uName=zdr.getFirstname() + zdr.getLastname();
                }
                String msg = "用户[" + uName + "]积分不足,当前剩余积分"+mbpoint+"分";
                int alarmType = 1;
                String cfgType = key;
                Map p = new HashMap();
                p.put("userid", userid);
                p.put("alarmType", alarmType);
                p.put("cfgType", cfgType);
                ZsDeviceAlarm alarm = new ZsDeviceAlarm();
                String id = devsMapper.getSQLManager().selectSingle("zsDevices.queryAlarmid3", p, String.class);
                Boolean isUpdate = false;
                if (id != null) {
                    alarm.setAlarmid(id);
                    isUpdate = true;
                } else {
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    alarm.setAlarmid(uuid);
                }
                String alarmTitle="积分预警";
                if (isUpdate) {
                    alarm.setLasttime(new Date());
                    alarm.setAlarmMsg(msg);
                    alarm.setServity(6);
                    dalarmMapper.updateTemplateById(alarm);
                } else {
                    alarm.setCfgType(cfgType);
                    alarm.setAlarmType(alarmType);
                    alarm.setServity(6);
                    alarm.setAlarmMsg(msg);
                    alarm.setFirsttime(new Date());
                    alarm.setInstime(new Date());
                    alarm.setUserid(userid);
                    alarm.setAlarmTitle(alarmTitle);
                    dalarmMapper.insert(alarm);
                    sendEmail(key, msg, alarmTitle,zaconfig);
                }

            } else {
                zdr.setMbpointAlarm(0);
                if (alarmId != null && alarmId.trim().length() > 0) {
                    devsMapper.getSQLManager().deleteById(ZsDeviceAlarm.class, alarmId);
                }

            
        }
    }

    /**
     * 积分预警key值
     * @return
     */
    private String getMKey() {
        return "mbpoint";
    }

    /**
     * 在线状态key值
     * @return
     */
    private String getOKey() {
        return "onlinestatus";
    }

    /**
     * 接口不通key值
     * @return
     */
    private String getTKey() {
        return "interfacestatus";
    }


    /**
     * 获取库中所有设备信息
     * @return
     */
    private Map getAllDevices() {
        HashMap<String, ZsDevices> devsMap = new HashMap<>();
        try {
            List<ZsDevices> devicesList = devsMapper.getSQLManager().all(ZsDevices.class);

            if (devicesList != null) {
                for (ZsDevices device : devicesList) {
                    devsMap.put(device.getDevicesn(),device);
                }
            }
        } catch (Exception e) {
            log.error("getAllDevices失败", e);
        }
        return devsMap;
    }

    /**
     * 设置设备删除标志字段
     * @param dl
     * @param allDevs
     */
    private void setDeviceDelFlag(List<Map> dl, Map<String, ZsDevices> allDevs) {
        List<String> devicesns=new ArrayList<>();
        for (Map od : dl) {
            String devicesn = (String) od.get("devicesn");
            devicesns.add(devicesn);
        }
        List<String> filterSns=new ArrayList<>();
        if(devicesns.size()>0){
            for (String key : allDevs.keySet()) {
                 if(!devicesns.contains(key)){
                     filterSns.add(key);
                 }
            }
        }
        if(filterSns.size()>0){
            Map p=new HashMap();
            p.put("devicesns",filterSns);
            devsMapper.getSQLManager().update("zsDevices.updateDevsDelFlag",p);
        }
    }

    /**
     * 同步NFC收益和流量数据
     * @param interCfg  接口配置
     * @param alarmMap  告警数据
     */
    public  void syncdataNfcflow(ZsDpoolInterface interCfg, HashMap<String, String> alarmMap
    		,ZsDeviceAlarmConfig zaconfig) {
        try {
        	log.info("开始同步账号"+interCfg.getUsername()+"所管理设备收益数据");
            List<Map> dl = getPostData(interCfg.getNfcurl(), interCfg.getUsername()
            		, interCfg.getUserpwd(), "nfcflow", alarmMap,zaconfig);
            if (dl != null) {
                Map devsLockM = getDevsLockNum(dl);
                for (Map od : dl) {
                    try {
                        insertOrUpdateNfc(od, devsLockM);
                    } catch (Exception e) {
                        log.error("insertOrUpdateNfc报错:", e);
                    }
                }
            }
            log.info("同步账号"+interCfg.getUsername()+"所管理设备收益数据完成");
        } catch (Exception e) {
            log.error("getdataNfcflow报错:", e);
        }
    }

    /**
     * 获取设备表锁仓参数
     * @param dl
     * @return
     */
    private Map getDevsLockNum(List<Map> dl) {
        Map<String, Map> dln = new HashMap<>();
        try {
            List<String> devsns = new ArrayList<>();
            for (Map od : dl) {
                String devicesn = (String) od.get("devicesn");
                if (null != devicesn && !"".equals(devicesn.trim())) {
                    devsns.add(devicesn);
                }
            }
            Map p = new HashMap();
            p.put("devicesns", devsns);
            List<Map> lockls = devsMapper.getSQLManager().select("zsDevices.queryDevsLockNum", Map.class, p);
            if (lockls != null && lockls.size() > 0) {
                for (Map lp : lockls) {
                    String devicesn = (String) lp.get("devicesn");
                    dln.put(devicesn, lp);
                }
            }
        } catch (Exception e) {
            log.error("getDevsLockNum报错:", e);
        }
        return dln;
    }

    /**
     * 插入或者更新收益表
     * @param data
     * @param devsLockM
     * @throws ParseException
     */
    private void insertOrUpdateNfc(Map data, Map devsLockM) throws ParseException {
        String ctime = (String) data.get("ctime");
        if (null == ctime || "".equals(ctime.trim())) {
            log.info("ctime:" + data + "ctime为空");
            return;
        }
        String devicesn = (String) data.get("devicesn");
        if (null == devicesn || "".equals(devicesn.trim())) {
            log.info("nfc:" + data + "devicesn为空");
            return;
        }

        BigDecimal slowBill = (BigDecimal) data.get("slow_bill");
        BigDecimal mbpoint = (BigDecimal) data.get("mbpoint");
        BigDecimal nfcNum = (BigDecimal) data.get("nfc_num");
        String transferNo = (String) data.get("transfer_no");
        Integer transferStatus = (Integer) data.get("trans_flag");
        String transferTimeStr = (String) data.get("transtime");
        String md5id = ctime + "_" + devicesn;
        md5id = DigestUtils.md5Hex(md5id);
        ZsDeviceTockenRecord nfcRecord = zsDevnfMapper.single(md5id);
        Boolean isUpdate = false;
        if (null != nfcRecord) {
            isUpdate = true;
        }
        nfcRecord = new ZsDeviceTockenRecord();
        nfcRecord.setFlowid(md5id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nfcRecord.setCtime(sdf.parse(ctime));
        nfcRecord.setFlowBill(slowBill.doubleValue());
        nfcRecord.setMbpoint(mbpoint.doubleValue());
        nfcRecord.setNfcNum(nfcNum.doubleValue());
        nfcRecord.setDrawnum(0.0);
        nfcRecord.setDrawStatus(1);
        nfcRecord.setReleaseStatus(1);
        nfcRecord.setHasrelease(0.0);
        nfcRecord.setDevicesn(devicesn);
        nfcRecord.setTransferNo(transferNo);
        nfcRecord.setTransFlag(transferStatus);
        if (null != transferTimeStr && !"".equals(transferTimeStr.trim())) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nfcRecord.setTranstime(sdf2.parse(transferTimeStr));
        }
        if (null != devsLockM && devsLockM.size() > 0) {
            Map odl = (Map) devsLockM.get(devicesn);
            if(odl==null) {
            	return;
            }
            Integer lockdays = (Integer) odl.get("lockdays");
            Integer releasedays = (Integer) odl.get("releasedays");
            BigDecimal shareRate = (BigDecimal) odl.get("shareRate");
            nfcRecord.setLockdays(lockdays);
            nfcRecord.setReleasedays(releasedays);
            nfcRecord.setShareRate(shareRate.doubleValue());
            BigDecimal sharenum = nfcNum.multiply(shareRate).divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP);
            nfcRecord.setSharenum(sharenum.doubleValue());
            BigDecimal takenum = nfcNum.subtract(sharenum);
            nfcRecord.setTakenum(takenum.doubleValue());
        }
        if (isUpdate) {
            nfcRecord.setUpdatetime(new Date());
            zsDevnfMapper.updateTemplateById(nfcRecord);
        } else {
            nfcRecord.setInstime(new Date());
            zsDevnfMapper.insert(nfcRecord);
        }
    }

    /**
     * 通过发送post请求获取设备或者用户或者收益数据
     * @param nfcurl
     * @param username
     * @param userpwd
     * @param type
     * @param alarmMap
     * @return
     * @throws Exception
     */
    private List<Map> getPostData(String nfcurl, String username
    		, String userpwd, String type, HashMap<String, String> alarmMap,ZsDeviceAlarmConfig zaconfig) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject json = new JSONObject();
        json.put("ctime", sdf.format(getBeforeDay()));
        String random = System.currentTimeMillis() + "";
        json.put("type", type);
        json.put("loginname", username);
        String data = "loginname=" + username + "&pwd=" + userpwd + "&random=" + random;
        String MD5val = DigestUtils.md5Hex(data);
        String sig = HmacSha256Util.HMACSHA256(MD5val, secretkey);
        json.put("sig", sig);
        String url = nfcurl + "&random=" + random;
        String jsonStr = sendPost(json, url, alarmMap,zaconfig);
        log.info("接口返回："+jsonStr);
        if (null == jsonStr) {
            log.error("sendPost:json:" + json + " url:" + url + "报错:" + jsonStr);
            dealAlarm(getTKey(), "接口连接异常", alarmMap,zaconfig);
            return null;
        }
        JSONObject jsonR = JSON.parseObject(jsonStr);
        int result = jsonR.get("result") == null ? -1 : ((Integer) jsonR.get("result")).intValue();
        if (0 == result) {
            List<Map> dl = (List<Map>) jsonR.get("data");
            if (null != dl && dl.size() > 0) {
            	 dealAlarm(getTKey(), "接口连接异常", alarmMap,zaconfig); 
                return dl;
            }
        } else {
            log.error("sendPost:json:" + json + " url:" + url + "报错:" + jsonStr);
        }
        return null;
    }

    /**
     * 插入或者更新设备表数据
     * @param data
     * @param alarmMap
     * @param lockNums
     * @param allDevs
     * @throws ParseException
     */
    private void insertOrUpdateDev(Map data, HashMap<String, String> alarmMap
    		, HashMap<String, String> lockNums, Map allDevs,ZsDeviceAlarmConfig zaconfig) throws ParseException {

        String devicesn = (String) data.get("devicesn");
        if (null == devicesn || "".equals(devicesn.trim())) {
            log.info("dev:" + data + "devicesn为空");
            return;
        }
        Integer devicestatus = (Integer) data.get("devicestatus");
        Integer onlineStatus = (Integer) data.get("online_status");
        BigDecimal calcvalue = (BigDecimal) data.get("calcvalue");
        if (calcvalue == null) calcvalue = new BigDecimal("50");
        Integer calcmode = (Integer) data.get("calcmode");
        if (calcmode == null) calcmode = 1;
        Long ownerid =null;
        if(data.get("ownerid") instanceof Integer){
            ownerid = Long.parseLong(data.get("ownerid").toString());
        }else{
            ownerid = (Long) data.get("ownerid");
        }
        String owerName = (String) data.get("ower_name");
        String buytime = (String) data.get("buytime");
        Long refereruserid = (Long) data.get("refereruserid");
        String referercode = (String) data.get("referercode");
        String referername = (String) data.get("referername");
        Integer deviceclass = (Integer) data.get("deviceclass");
        String onlineTime = (String) data.get("online_time");
        String offlineTime = (String) data.get("offline_time");
        String deviceIp = (String) data.get("device_ip");
        String deviceVip = (String) data.get("device_vip");
        String deviceLocaltion = (String) data.get("device_localtion");

        ZsDevices oldzdr = (ZsDevices) allDevs.get(devicesn);
        Boolean isUpdate = false;
        ZsDevices zdr = new ZsDevices();
        zdr.setDevicestatus(devicestatus);
        if (null != oldzdr) {
        	
            isUpdate = true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        zdr.setDevicesn(devicesn);
        
        if (null == onlineStatus) onlineStatus = 0;
        if(deviceclass!=null&&deviceclass.intValue()==2) {
        	onlineStatus=1;
        }
        zdr.setOnlineStatus(onlineStatus);
        dealOnlineAlarm(onlineStatus, devicesn, alarmMap,zaconfig);
        zdr.setCalval(calcvalue);
        zdr.setCalmodel(calcmode);
        zdr.setOwerid(ownerid);
        zdr.setOwerName(owerName);
        zdr.setDeviceClass(deviceclass);

        if (null != buytime && !"".equals(buytime.trim())) {
            zdr.setBuytime(sdf.parse(buytime));
        }
        zdr.setRefereruserid(refereruserid);
        zdr.setReferercode(referercode);
        zdr.setReferername(referername);

        if (null != onlineTime) {
            zdr.setOnlineTime(sdf.parse(onlineTime));
        }
        if (null != offlineTime) {
            zdr.setOfflineTime(sdf.parse(offlineTime));
        }
        zdr.setDeviceIp(deviceIp);
        zdr.setDeviceVip(deviceVip);
        zdr.setDeviceLocaltion(deviceLocaltion);
        zdr.setDeleteFlag(1);
        if (isUpdate) {
            Map changeFs=devChangeFields(oldzdr,zdr);
            if(null!=changeFs&&changeFs.size()>0){
                Map p=new HashMap();
                p.put("devicesn",devicesn);
                p.put("fields",changeFs);
                devsMapper.getSQLManager().update("zsDevices.updateDevWithNull",p);
            }
        } else {
            if (null != lockNums && lockNums.size() > 0) {
                String defaultSharetype = lockNums.get("default_sharetype");
                zdr.setShareRate(new BigDecimal(defaultSharetype));
                String lockdays = lockNums.get("lockdays");
                zdr.setLockdays(Integer.parseInt(lockdays));
                String releasedays = lockNums.get("releasedays");
                zdr.setReleasedays(Integer.parseInt(releasedays));
            }
            zdr.setCreatetime(new Date());
            devsMapper.insert(zdr);
        }
    }

    /**
     * 获取设备记录改变的值
     * @param oldzdr
     * @param zdr
     * @return
     */
    private Map devChangeFields(ZsDevices oldzdr, ZsDevices zdr) {
        Map fieldChange=new HashMap();
        Integer devicestatus=zdr.getDevicestatus();
        Integer olddevicestatus=oldzdr.getDevicestatus();

        if((devicestatus==null&&olddevicestatus==null)||(devicestatus!=null&&olddevicestatus!=null&&devicestatus==olddevicestatus)){

        }else{
            if(zdr.getDevicestatus()!=null){
                fieldChange.put("devicestatus",zdr.getDevicestatus());
            }else{
                fieldChange.put("devicestatus","null");//字符串null表示要设置未null
            }
        }

        Integer deviceclass=zdr.getDeviceClass();
        Integer olddeviceclass=oldzdr.getDeviceClass();
        if((deviceclass==null&&olddeviceclass==null)||(deviceclass!=null&&olddeviceclass!=null&&deviceclass==olddeviceclass)){

        }else{
            if(zdr.getDeviceClass()!=null){
                fieldChange.put("device_class",zdr.getDeviceClass());
            }else{
                fieldChange.put("device_class","null");//字符串null表示要设置未null
            }
        }


        String deviceIp=zdr.getDeviceIp();
        String olddeviceIp=oldzdr.getDeviceIp();
        if((deviceIp==null&&olddeviceIp==null)||(deviceIp!=null&&olddeviceIp!=null&&deviceIp.equals(olddeviceIp))){

        }else{
            if(zdr.getDeviceIp()!=null){
                fieldChange.put("device_ip",zdr.getDeviceIp());
            }else{
                fieldChange.put("device_ip","null");//字符串null表示要设置未null
            }
        }

        String deviceVip=zdr.getDeviceVip();
        String olddeviceVip=oldzdr.getDeviceVip();
        if((deviceVip==null&&olddeviceVip==null)||(deviceVip!=null&&olddeviceVip!=null&&deviceVip.equals(olddeviceVip))){
            zdr.setDeviceVip(null);
        }else{
            if(zdr.getDeviceVip()!=null){
                fieldChange.put("device_vip",zdr.getDeviceVip());
            }else{
                fieldChange.put("device_vip","null");//字符串null表示要设置未null
            }
        }
        String deviceLocaltion=zdr.getDeviceLocaltion();
        String olddeviceLocaltion=oldzdr.getDeviceLocaltion();
        if((deviceLocaltion==null&&olddeviceLocaltion==null)||(deviceLocaltion!=null&&olddeviceLocaltion!=null&&deviceLocaltion.equals(olddeviceLocaltion))){

        }else{
            if(zdr.getDeviceLocaltion()!=null){
                fieldChange.put("device_localtion",zdr.getDeviceLocaltion());
            }else{
                fieldChange.put("device_localtion","null");//字符串null表示要设置未null
            }
        }
        String owerName=zdr.getOwerName();
        String oldowerName=oldzdr.getOwerName();
        if((owerName==null&&oldowerName==null)||(owerName!=null&&oldowerName!=null&&owerName.equals(oldowerName))){

        }else{
            if(zdr.getOwerName()!=null){
                fieldChange.put("ower_name",zdr.getOwerName());
            }else{
                fieldChange.put("ower_name","null");//字符串null表示要设置未null
            }
        }

        Long ownerid=zdr.getOwerid();
        Long oldownerid=oldzdr.getOwerid();
        if((ownerid==null&&oldownerid==null)||(ownerid!=null&&oldownerid!=null&&ownerid.compareTo(oldownerid)==0)){

        }else{
            if(zdr.getOwerid()!=null){
                fieldChange.put("owerid",zdr.getOwerid());
            }else{
                fieldChange.put("owerid","null");//字符串null表示要设置未null
            }
        }

        Integer onlineStatus=zdr.getOnlineStatus();
        Integer oldonlineStatus=oldzdr.getOnlineStatus();
        if((onlineStatus==null&&oldonlineStatus==null)||(onlineStatus!=null&&onlineStatus!=null&&onlineStatus==oldonlineStatus)){

        }else{
            if(zdr.getOnlineStatus()!=null){
                fieldChange.put("online_status",zdr.getOnlineStatus());
            }else{
                fieldChange.put("online_status","null");//字符串null表示要设置未null
            }
        }
        Date offlineTime=zdr.getOfflineTime();
        Date oldofflineTime=oldzdr.getOfflineTime();
        if((offlineTime==null&&oldofflineTime==null)||(offlineTime!=null&&oldofflineTime!=null&&offlineTime.compareTo(oldofflineTime)==0)){

        }else{
            if(zdr.getOfflineTime()!=null){
                fieldChange.put("offline_time",zdr.getOfflineTime());
            }else{
                fieldChange.put("offline_time","null");//字符串null表示要设置未null
            }
        }

        BigDecimal calcvalue=zdr.getCalval();
        BigDecimal oldcalcvalue=oldzdr.getCalval();
        if((calcvalue==null&&oldcalcvalue==null)||(calcvalue!=null&&oldcalcvalue!=null&&calcvalue.compareTo(oldcalcvalue)==0)){

        }else{
            if(zdr.getCalval()!=null){
                fieldChange.put("calval",zdr.getCalval());
            }else{
                fieldChange.put("calval","null");//字符串null表示要设置未null
            }
        }

        Integer calcmode=zdr.getCalmodel();
        Integer oldcalcmode=oldzdr.getCalmodel();
        if((calcmode==null&&oldcalcmode==null)||(calcmode!=null&&oldcalcmode!=null&&calcmode==oldcalcmode)){

        }else{
            if(zdr.getCalmodel()!=null){
                fieldChange.put("calmodel",zdr.getCalmodel());
            }else{
                fieldChange.put("calmodel","null");//字符串null表示要设置未null
            }
        }
        Date onlineTime=zdr.getOnlineTime();
        Date oldonlineTime=oldzdr.getOnlineTime();
        if((onlineTime==null&&oldonlineTime==null)||(onlineTime!=null&&oldonlineTime!=null&&onlineTime.compareTo(oldonlineTime)==0)){

        }else{
            if(zdr.getOnlineTime()!=null){
                fieldChange.put("online_time",zdr.getOnlineTime());
            }else{
                fieldChange.put("online_time","null");//字符串null表示要设置未null
            }
        }
        Integer deleteFlag=zdr.getDeleteFlag();
        Integer olddeleteFlag=oldzdr.getDeleteFlag();
        if((deleteFlag==null&&olddeleteFlag==null)||(deleteFlag!=null&&olddeleteFlag!=null&&deleteFlag==olddeleteFlag)){

        }else{
            if(zdr.getDeleteFlag()!=null){
                fieldChange.put("delete_flag",zdr.getDeleteFlag());
            }else{
                fieldChange.put("delete_flag","null");//字符串null表示要设置未null
            }
        }

        return fieldChange;
    }

    /**
     * 设备离线预警检查
     * @param onlineStatus
     * @param devicesn
     * @param alarmMap
     */
    private void dealOnlineAlarm(Integer onlineStatus, String devicesn
    		, HashMap<String, String> alarmMap,ZsDeviceAlarmConfig zaconfig) {
    	 log.info("sn:"+devicesn+" onlineStatus="+onlineStatus);
    	 String alarmId = alarmMap.get(devicesn + "onlinestatus");
    	if (null == onlineStatus || onlineStatus.intValue() == 0) {
            String key = getOKey();
            String msg = "设备[" + devicesn + "]离线";
            log.info(msg);
            dealAlarm(key, msg, devicesn,alarmId,zaconfig);
        } else {
           
            if (alarmId != null && alarmId.trim().length() > 0) {
                devsMapper.getSQLManager().deleteById(ZsDeviceAlarm.class, alarmId);
            }
        }
    }

    /**
     * 发送post请求
     * @param json
     * @param url
     * @param alarmMap
     * @return
     */
    public String sendPost(JSONObject json, String url, HashMap<String, String> alarmMap,ZsDeviceAlarmConfig zaconfig) {
        log.info("sendPost:json:" + json + " url:" + url);
        String resultStr = null;
        int retrynum = 0;//重试次数，重试三次
        while (retrynum < 3) {
            try {
            	HttpRequest req= new HttpRequest(url, "POST") ;
                req.contentType("application/json", "UTF-8");
                req.trustAllCerts();
                req.trustAllHosts();
                req.connectTimeout(60000);//连接超时
                req.readTimeout(60000);//读取超时
                req.send(json.toJSONString().getBytes());
                 resultStr=req.body();
                 return resultStr;
            } catch (Exception e) {
                log.error("sendPost:json:" + json + " url:" + url, e);
                retrynum++;
                if (retrynum == 3) {
                    dealAlarm(getTKey(), "接口连接异常", alarmMap,zaconfig);
                }
                resultStr = null;
            }
        }
        
        log.info("resultStr:" + resultStr);
        return resultStr;
    }
  
    /**
     * 处理接口不通预警
     * @param key
     * @param msg
     * @param alarmMap
     */
    private void dealAlarm(String key, String msg, HashMap<String, String> alarmMap
    		,ZsDeviceAlarmConfig zaconfig) {
      
        if (zaconfig.getInterfacestatus()!=null&&zaconfig.getInterfacestatus().intValue()==1) {
            int alarmType = 1;
            String cfgType = key;
            Map p = new HashMap();
            p.put("alarmType", alarmType);
            p.put("cfgType", cfgType);
            ZsDeviceAlarm alarm = new ZsDeviceAlarm();
            String id = devsMapper.getSQLManager().selectSingle("zsDevices.queryAlarmid2", p, String.class);
            Boolean isUpdate = false;
            if (id != null) {
                alarm.setAlarmid(id);
                isUpdate = true;
            } else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                alarm.setAlarmid(uuid);
            }
            if (isUpdate) {
                alarm.setLasttime(new Date());
                alarm.setAlarmMsg(msg);
                alarm.setServity(6);
                dalarmMapper.updateTemplateById(alarm);
            } else {
                String alarmTitle="接口状态预警";
                alarm.setCfgType(cfgType);
                alarm.setAlarmType(1);
                alarm.setServity(6);
                alarm.setAlarmMsg(msg);
                alarm.setFirsttime(new Date());
                alarm.setInstime(new Date());
                alarm.setAlarmTitle(alarmTitle);
                dalarmMapper.insert(alarm);
                sendEmail(key, msg, alarmTitle,zaconfig);
            }

        } else {
            String alarmId = alarmMap.get("interfacestatus");
            if (alarmId != null && alarmId.trim().length() > 0) {
                devsMapper.getSQLManager().deleteById(ZsDeviceAlarm.class, alarmId);
            }
        }
    }

    /**
     * 处理设备离线预警
     * @param key
     * @param msg
     * @param devicesn
     */
    private void dealAlarm(String key, String msg, String devicesn,String alarmId,ZsDeviceAlarmConfig zaconfig) {
        
            int alarmType = 1;
            String cfgType = key;
            Map p = new HashMap();
            p.put("devicesn", devicesn);
            p.put("alarmType", alarmType);
            p.put("cfgType", cfgType);
            ZsDeviceAlarm alarm = new ZsDeviceAlarm();

            if (alarmId!=null&&alarmId.length()>0) {
                alarm.setAlarmid(alarmId);
                alarm.setLasttime(new Date());
                alarm.setAlarmMsg(msg);
                alarm.setServity(6);
                dalarmMapper.updateTemplateById(alarm);
            } else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                alarm.setAlarmid(uuid);
                String alarmTitle="设备离线";
                alarm.setDevicesn(devicesn);
                alarm.setCfgType(cfgType);
                alarm.setAlarmType(alarmType);
                alarm.setServity(6);
                alarm.setAlarmMsg(msg);
                alarm.setFirsttime(new Date());
                alarm.setInstime(new Date());
                alarm.setAlarmTitle(alarmTitle);
                dalarmMapper.insert(alarm);
                sendEmail(key, msg, alarmTitle,zaconfig);
            }
          

        
    }

    /**
     * 预警发送邮件
     * @param key
     * @param msg
     * @param title
     */
    private void sendEmail(String key, String msg, String title,ZsDeviceAlarmConfig zaconfig) {
        String email = zaconfig.getEmail();
        if (null != email && !"".equals(email.trim())) {
            NoticeInfo noticeInfo = new NoticeInfo();
            noticeInfo.setAlarmMsg(msg);
            noticeInfo.setAlarmTitle(title);
            noticeInfo.setRecevAddrList(Arrays.asList(email.split(",")));
            EmailSendSrv.getInstance().putq(noticeInfo);
        }
    }

    /**
     * 获取前一天的时间
     * @return
     */
    private Date getBeforeDay() {
        Calendar cl = Calendar.getInstance();
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        return cl.getTime();
    }

}
