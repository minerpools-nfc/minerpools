package com.dpool.app.tranfer;

import java.util.Calendar;
import java.util.Date;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.TransferApplyService;
import com.dpool.app.util.AgentSvcTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 提取申请转账确认线程
 * @author yyj
 * @2021年3月31日
 * @TODO
 */
@Slf4j
public class TransferApplyConfirmSrv extends AgentSvcTask{
    private static long defaultSleepTime=60000;//一分钟
    private Date runstartDate=null;
    private Date runendDate=null;
    private TransferApplyService transferApplyService=null;

    public TransferApplyConfirmSrv() {
        transferApplyService=SpringContextUtils.getBean(TransferApplyService.class);
        this.setName("TransferApplyConfirmSrv#");
    }
    protected  void runTask() {
        Calendar jobca=Calendar.getInstance();
        int sec=jobca.get(Calendar.SECOND);
        try {
            log.info("休眠"+(60-sec)+"秒后启动提请转账确认线程");
            Thread.sleep((60-sec)*1000L);
        } catch (InterruptedException e2) {

        }
        log.info("启动提请转账确认线程!");
        while(!shutdown){
            runstartDate=new Date();
            try{
                transferApplyService.startConfirm();
            }catch(Exception e){
                log.error("提请转账确认轮询出错",e);
            }finally {
                try {
                    runendDate=new Date();
                    if(defaultSleepTime-(runendDate.getTime()-runstartDate.getTime())>0){
                        Thread.sleep(defaultSleepTime-(runendDate.getTime()-runstartDate.getTime()));
                    }
                }catch (Exception e) {
                    log.error("提请转账确认接口定时sleep出错",e);
                }
            }
        }
    }
}
