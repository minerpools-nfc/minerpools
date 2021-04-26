package com.dpool.app.release;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.DevTocGenService;
import com.dpool.app.service.JobService;
import com.dpool.app.util.AgentSvcTask;
import com.dpool.app.util.RunningTime;
import com.dpool.app.vo.ZsJob;
import com.dpool.app.vo.ZsJobLog;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.UUID;

/**
 * 虚拟租用设备流量收益生成
 * @author yyj
 * @add 2021年04月15日
 */
@Slf4j
public class DevTocGenSrv extends AgentSvcTask {
    private ZsJob job = null;//定时任务
    private Calendar calendar = null;//执行时间
    private DevTocGenService devTocGenService;
    ZsJobLog joblog = new ZsJobLog();

    public DevTocGenSrv(ZsJob job, Calendar calendar) {
        this.job = job;
        this.calendar = calendar;
        devTocGenService = SpringContextUtils.getBean(DevTocGenService.class);
        this.setName("DevTocGenSrv#");
    }

    protected void runTask() {
        log.info("开始生成虚拟设备收益");
        RunningTime rtime = new RunningTime();
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            joblog.setId(uuid);
            joblog.setJobid(job.getJobid());
            joblog.setExectime(calendar.getTime());;
            joblog.setStatus(1);
            String detail=devTocGenService.genVirTocken();
            joblog.setJobDetail(detail);
            joblog.setJobmsg("生成虚拟设备收益成功");
            rtime.end();
            joblog.setSpeeds(rtime.getTakeUpTime());
            log.info("生成虚拟设备收益完成，耗时" + rtime.toString());
        } catch (Exception e) {
            log.error("生成虚拟设备收益出错", e);
            joblog.setStatus(0);
            joblog.setJobmsg("生成虚拟设备收益失败"+ e.getMessage());
        }finally {
            JobService service = SpringContextUtils.getBean(JobService.class);
            service.insSysJobLog(joblog);
        }
    }
}
