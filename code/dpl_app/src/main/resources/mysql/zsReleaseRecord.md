queryTockenRs
===
select a.flowid,a.ctime,a.devicesn,b.owerid,a.nfc_num,a.share_rate,a.sharenum,a.takenum,a.lockdays,a.releasedays,a.transtime,a.hasrelease 
from zs_device_tocken_record a,zs_devices b
where a.trans_flag=1 and a.transtime is not null and a.transtime!='' and (a.hasrelease<a.nfc_num or a.hasrelease is null) and a.devicesn=b.devicesn
and a.lockdays is not null and a.releasedays is not null and a.nfc_num is not null
and  a.transtime<=date_sub(NOW(), interval a.lockdays day) and release_status!=2  
and  a.flowid not in (select z.flowid  from zs_release_record  z  where z.ctime=#ctime#)

queryRsId
===
select a.releaseid from zs_release_record a
where a.flowid=#flowid# and ctime=#ctime#

querySumReleasenum
===
select sum(nfc_num) from zs_release_record where flowid=#flowid# and ctime<#ctime#

queryButchSumReleasenum
===
select a.flowid,sum(b.nfc_num) hasrelease,a.nfc_num from   zs_device_tocken_record  a,zs_release_record  b where  a.flowid=b.flowid

and  a.flowid in (select d.flowid from zs_release_record  d where  d.ctime=#ctime# )

GROUP BY  a.flowid