package com.dpool.app.release;

import java.util.Calendar;
import java.util.UUID;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.JobService;
import com.dpool.app.service.ReleaseService;
import com.dpool.app.util.AgentSvcTask;
import com.dpool.app.util.RunningTime;
import com.dpool.app.vo.ZsJob;
import com.dpool.app.vo.ZsJobLog;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成提取状态，--暂时没用
 * @author maxinbo
 * @add 2020年12月24日
 */
@Slf4j
public class TockenGenSrv extends AgentSvcTask {
    private ZsJob job = null;//定时任务
    private Calendar calendar = null;//执行时间
    private ReleaseService releaseService;
    ZsJobLog joblog = new ZsJobLog();

    public TockenGenSrv(ZsJob job, Calendar calendar) {
        this.job = job;
        this.calendar = calendar;
        releaseService = SpringContextUtils.getBean(ReleaseService.class);
        this.setName("TockenGenSrv#");
    }

    protected void runTask() {
        log.info("开始生成提取状态");
        RunningTime rtime = new RunningTime();
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            joblog.setId(uuid);
            joblog.setJobid(job.getJobid());
            joblog.setExectime(calendar.getTime());;
            joblog.setStatus(1);
            String detail=releaseService.genDrawdata();
            joblog.setJobDetail(detail);
            joblog.setJobmsg("生成提取状态成功");
            rtime.end();
            joblog.setSpeeds(rtime.getTakeUpTime());
            log.info("生成提取状态完成，耗时" + rtime.toString());
        } catch (Exception e) {
            log.error("生成提取状态出错", e);
            joblog.setStatus(0);
            joblog.setJobmsg("生成提取状态失败"+ e.getMessage());
        }finally {
            JobService service = SpringContextUtils.getBean(JobService.class);
            service.insSysJobLog(joblog);
        }
    }
}
