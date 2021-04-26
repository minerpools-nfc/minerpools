queryVirdevs
===
select a.devicesn,a.nfc_num,a.lockdays,a.releasedays,a.share_rate,a.device_class
from dpools.zs_devices a where a.device_class=3 and a.nfc_num is not null



