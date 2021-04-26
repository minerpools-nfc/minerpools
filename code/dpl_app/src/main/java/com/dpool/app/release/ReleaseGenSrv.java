package com.dpool.app.release;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.JobService;
import com.dpool.app.service.ReleaseService;
import com.dpool.app.util.AgentSvcTask;
import com.dpool.app.util.RunningTime;
import com.dpool.app.vo.ZsDeviceTockenRecord;
import com.dpool.app.vo.ZsJob;
import com.dpool.app.vo.ZsJobLog;

import lombok.extern.slf4j.Slf4j;

/**
 * 收益线性释放线程，通过定时任务配置运行时间
 * @author maxinbo
 * @add 2020年12月24日
 */
@Slf4j
public class ReleaseGenSrv extends AgentSvcTask {
    private ZsJob job = null;//定时任务
    private Calendar calendar = null;//执行时间
    private ReleaseService releaseService;
    private ZsJobLog joblog = new ZsJobLog();
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public ReleaseGenSrv(ZsJob job, Calendar calendar) {
        this.job = job;
        this.calendar = calendar;
        releaseService = SpringContextUtils.getBean(ReleaseService.class);
        this.setName("ReleaseGenSrv#");
    }

    protected void runTask() {
        log.info("开始生成线性释放数据");
        RunningTime rtime = new RunningTime();
        try {
        	String ctime=sdf.format(new Date());
        	List<ZsDeviceTockenRecord> tcs=releaseService.getTokenRecord(ctime);
            if(tcs==null||tcs.size()<=0) {
            	return;
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            joblog.setId(uuid);
            joblog.setJobid(job.getJobid());
            joblog.setExectime(calendar.getTime());;
            joblog.setStatus(1);
            String detail=releaseService.gendata(ctime,tcs);
            joblog.setJobDetail(detail);
            joblog.setJobmsg("生成线性释放数据成功");
            rtime.end();
            joblog.setSpeeds(rtime.getTakeUpTime());
            log.info("生成线性释放数据完成，耗时" + rtime.toString());
        } catch (Exception e) {
            log.error("生成线性释放数据出错", e);
            joblog.setStatus(0);
            joblog.setJobmsg("生成线性释放数据失败"+ e.getMessage());
        }finally {
        	if(joblog.getId()!=null) {
        		JobService service = SpringContextUtils.getBean(JobService.class);
                service.insSysJobLog(joblog);	
        	}
            
        }
    }
}
