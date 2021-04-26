queryInterface
===
select a.httpurl,a.nfcurl,a.username,a.userpwd,a.intaval from dpools.zs_dpool_interface a 

queryNfc
===
SELECT a.devicesn,a.nfc_num,b.policyid,b.wallet_address,c.policy_type,c.total_num from dpools.zs_device_nfc_record a,dpools.zs_devices b,dpools.zs_device_draw_policy c 
where a.ctime=#ctime# and a.devicesn=b.devicesn and b.policyid=c.policyid and c.policy_status=1

queryDids
===
select a.detailid,a.devicesn,a.draw_transFlag from dpools.zs_device_draw_detail a 
where a.ctime=#ctime# and a.policyid=#policyid# and a.devicesn in ( #join(devicesns)#)

queryAlarmMbpoint
===
select a.cfgid,a.email,a.mbpoint from dpools.zs_device_alarm_config a 
where a.mbpoint is not null

queryAlarmOnlinestatus
===
select a.cfgid,a.email from dpools.zs_device_alarm_config a 
where a.onlinestatus=1

queryAlarmInterfacestatus
===
select a.cfgid,a.email from dpools.zs_device_alarm_config a 
where a.interfacestatus=1

queryDayid
===
select a.dayid,a.draw_status from dpools.zs_device_draw_day a 
where a.ctime=#ctime# and a.policyid=#policyid#

queryAlarmid
===
select a.alarmid from dpools.zs_device_alarm a 
where a.devicesn=#devicesn# and a.alarm_type=#alarmType# and a.cfg_type=#cfgType#

queryAlarmid2
===
select a.alarmid from dpools.zs_device_alarm a 
where a.devicesn is null and a.alarm_type=#alarmType# and a.cfg_type=#cfgType#

queryAlarmid3
===
select a.alarmid from dpools.zs_device_alarm a 
where a.userid=#userid# and a.alarm_type=#alarmType# and a.cfg_type=#cfgType#

queryLockNum
===
select a.prop_flag,a.prop_value from dpools.zs_sys_config a 
where a.prop_flag in ('lockdays','releasedays','default_sharetype')

queryDevsLockNum
===
select a.devicesn,a.share_rate,a.lockdays,a.releasedays from dpools.zs_devices a 
where a.devicesn in ( #join(devicesns)#)

updateDevsDelFlag
===
update dpools.zs_devices set delete_flag=2 where devicesn in ( #join(devicesns)#)

updateUserDelFlag
===
update dpools.zs_bind_users set deleteflag=2 where userid in ( #join(userids)#)

updateDevWithNull
===
update dpools.zs_devices set 
@trim(){ 
  @for(field in fields){
        @if(field.value=="null"){
            #text(field.key)#=null,
        @}
        @else{
            #text(field.key)#=#field.value#,
        @}
  @}
@}
 where devicesn=#devicesn#

updateUserWithNull
===
update dpools.zs_bind_users set 
@trim(){ 
    @for(field in fields){
        @if(field.value=="null"){
         #text(field.key)#=null,
        @}
        @else{
         #text(field.key)#=#field.value#,
        @}
    @}
@}
 where userid=#userid#