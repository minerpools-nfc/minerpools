updateRelease
===
update zs_release_record set draw_status=3 where applyid=#applyid#

queryFlowids
===
select flowid from zs_release_record where applyid=#applyid#

querySumSharenum
===
select sum(sharenum) from zs_release_record where flowid=#flowid#

queryTockenR
===
select sharenum from zs_device_tocken_record where flowid=#flowid#


queryTockenRecordByDay
===
select d.flowid,d.nfc_num,t.draw_status,sum(t.nfc_num) hasrelease,sum(t.sharenum) drawnum from   zs_device_tocken_record d,zs_release_record  t where  d.flowid=t.flowid and  d.nfc_num*d.share_rate!=d.drawnum
GROUP BY  d.flowid,t.draw_status,d.nfc_num

queryConfirmA
===
select applyid,trans_no from zs_draw_apply where trans_status=4
