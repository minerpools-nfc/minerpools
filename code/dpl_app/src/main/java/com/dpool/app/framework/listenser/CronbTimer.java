package com.dpool.app.framework.listenser;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.support.CronSequenceGenerator;

import com.dpool.app.dbpoolsyc.DbpoolSycSyncSrv;
import com.dpool.app.framework.config.JobConfiguration;
import com.dpool.app.framework.config.SpringContextUtils;
import com.dpool.app.service.DbpoolSycService;
import com.dpool.app.service.JobService;
import com.dpool.app.tranfer.DrawCrontabSrv;
import com.dpool.app.util.AgentSvcTask;
import com.dpool.app.util.RunningTime;
import com.dpool.app.vo.ZsDpoolInterface;
import com.dpool.app.vo.ZsJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 
* <p>Title: CronbTimer.java</p>  
* <p>Description: crm定时器，指定时间执行任务</p>  
* <p>Copyright: Copyright (c) 2020</p>  
* @author maxinbo  
* @date 2020年3月12日  
* @version 1.0
 */
@Slf4j
public class CronbTimer extends AgentSvcTask {
	private static final long defaultSleepTime=60000;//一分钟
	private JobService service;
	
 	protected void runTask() {
		service=SpringContextUtils.getBean(JobService.class);
		JobConfiguration tcn=SpringContextUtils.getBean(JobConfiguration.class);
		DbpoolSycService dbpoolsycService=SpringContextUtils.getBean(DbpoolSycService.class);
		Calendar jobca=Calendar.getInstance();
		int sec=jobca.get(Calendar.SECOND);
		try {
			log.info("休眠"+(60-sec)+"秒后启动任务轮询线程!");
			Thread.sleep((60-sec)*1000L);
		} catch (InterruptedException e2) {}
	    log.info("启动定时任务轮询线程!");
		while(!shutdown){
			RunningTime runningTime = new RunningTime();
			try{
				Calendar  ca=Calendar.getInstance();
				int minite=ca.get(Calendar.MINUTE);
				List<ZsJob> jobList=null;
				if(tcn.testJobid!=null&&!"".equals(tcn.testJobid)) {
					jobList=service.getSysJobById(tcn.testJobid);
					if(null!=jobList&&jobList.size()>0) {
						startThread(jobList.get(0),jobList.get(0).getActPath(), ca);
					}
					break;
				}else {
						jobList=service.getSysJobs();
				}

				if(jobList==null||jobList.size()==0) {
					log.warn("当前未配置任何NFCFLOW相关统计任务");
					continue;
				}
				startJob(jobList, ca);
				if(minite%5==0) {
					//每五分钟定时 更新收益释放和提取
					DrawCrontabSrv.getInstance().putq("run");
				}
//				接口配置
				ZsDpoolInterface interCfg=dbpoolsycService.getInterfacecfg();
				if(interCfg!=null) {
					if(minite==0||minite%interCfg.getIntaval().intValue()==0) {
						(new DbpoolSycSyncSrv("all",interCfg)).start();
					}
				}
			}catch(Exception e){
				log.error("定时任务轮询出错",e);
			}finally {
				try {
					runningTime.end();
				if(defaultSleepTime-(runningTime.getTakeUpTime())>0){
					Thread.sleep(defaultSleepTime-(runningTime.getTakeUpTime()));
				}}catch (Exception e) {}
			}
		}
	}

	/**
	 * for循环轮休数据库中job是否有需要当前时间执行的
	 * @param jobList
	 *author maxinbo
	 */
	private   void startJob(List<ZsJob>  jobList,Calendar calendar) {
		try {

			for(ZsJob sysjob:jobList) {
				if(checkJob(sysjob, calendar)) {
					startThread(sysjob,sysjob.getActPath(),calendar);
				}

			}
		}catch (Exception e) {
			log.error("for循环轮休数据库中job是否有需要当前时间执行出错",e);
		}
	}

	/**
	 * 根据类路径启动线程报错
	 * @param classpath
	 *author maxinbo
	 */
	private  void startThread(ZsJob sysjob,String classpath,Calendar calendar) {
		ZsJob job = new ZsJob();
		try {
			Class  classobj=Class.forName(classpath);
			Constructor constructor = classobj.getDeclaredConstructor(new Class[]{ZsJob.class,Calendar.class});
			constructor.setAccessible(true);
			AgentSvcTask thread=(AgentSvcTask)constructor.newInstance(sysjob,calendar);
			thread.start();
			job.setJobid(sysjob.getJobid());
			job.setExecresult(1);
			job.setExecmsg("");
		}catch (Exception e) {
			job.setExecresult(0);
			job.setExecmsg("根据类路径"+classpath+"启动线程报错.");
			log.error("根据类路径"+classpath+"启动线程报错",e);
		}finally {
			job.setExectime(calendar.getTime());
			service.updateSysJobById(job);
		}
	}

	/**
	 * 检查指定job在当前时间是否执行
	 * @param sysjob  job对象
	 * @param calendar  当前时间
	 * @return  true 执行  false不执行
	 *author maxinbo
	 */
	private boolean  checkJob(ZsJob sysjob,Calendar calendar) {
		try {
			if(sysjob.getStarttime()!=null&&sysjob.getStarttime().getTime()>calendar.getTimeInMillis()) {
				return false;
			}
			if(sysjob.getEndtime()!=null&&sysjob.getEndtime().getTime()<calendar.getTimeInMillis()) {
				return false;
			}
			String nowtime=new SimpleDateFormat( "yyyy-MM-dd HH:mm").format(calendar.getTime());
			String cron = sysjob.getJobCron();
			if(null==cron) {
				log.info("job中cron表达式为空，jobid="+sysjob.getJobid()+",jobname="+sysjob.getJobName());
				return false;
			}else {
				String []data = cron.split(" ");
				cron = data.length>6?cron.substring(0,cron.lastIndexOf(data[6])-1):cron;
			}
			calendar.add(Calendar.MINUTE,-1);
			CronSequenceGenerator cronb=new CronSequenceGenerator(cron);
			Calendar beforetime=Calendar.getInstance();
			beforetime.setTime(calendar.getTime());
			beforetime.add(Calendar.MINUTE,-1);
			Date date=cronb.next(beforetime.getTime());
			beforetime.add(Calendar.MINUTE,1);
			Date date1=cronb.next(beforetime.getTime());
			String runtime=new SimpleDateFormat( "yyyy-MM-dd HH:mm").format(date);
			String runtime_avgminite=new SimpleDateFormat( "yyyy-MM-dd HH:mm").format(date1);
			log.info(sysjob.getJobName()+": 当前时间 "+nowtime+" 执行时间 " +runtime+"/"+runtime_avgminite);
			if(runtime.equals(nowtime)||runtime_avgminite.equals(nowtime)) {
				return true;
			}
		}catch (Exception e) {
			log.error("检查指定job在当前时间是否执行出错",e);
		}finally {
			calendar.add(Calendar.MINUTE,1);
		}

		return false;

	}
}
