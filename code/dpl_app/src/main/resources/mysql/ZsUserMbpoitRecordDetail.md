queryMbpoitDetails
===
select rdetailid, fromuserid, touserid, mbpoint
from zs_user_mbpoit_record_detail a 
where a.transfer_status in(1) and a.mtbutch=#mtbutch#



