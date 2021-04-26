package com.dpool.app.service;

import com.dpool.app.mapper.ZsJobMapper;
import com.dpool.app.vo.ZsJob;
import com.dpool.app.vo.ZsJobLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 获取配置的定时任务数据
 */
@Slf4j
@Service
public class JobService {

	@Autowired
	private ZsJobMapper zsJobMapper;

	/**
	 * 查询符合条件的定时任务配置数据
	 * @return
	 */
	public List<ZsJob> getSysJobs() {
		try {
			ZsJob  job=new ZsJob();
			job.setJobServers(100);
			job.setJobEnable(1);
			return zsJobMapper.template(job);
		}catch (Exception e) {
			log.error("获取定时任务出错",e);
		}
		return null;
	}

	/**
	 * 根据id获取定时任务配置数据
	 * @param jobId
	 * @return
	 */
	public  List<ZsJob>  getSysJobById(String jobId) {
		try {
			ZsJob  job=new ZsJob();
			job.setJobid(jobId);
			return zsJobMapper.template(job);
		}catch (Exception e) {
			log.error("获取定时任务出错",e);
		}
		return null;
	}
	/**
	 * 修改job最后执行时间
	 * @param job
	 * @return
	 */
	public  boolean  updateSysJobById(ZsJob job) {
		try {
			if(zsJobMapper.getSQLManager().updateTemplateById(job)>0) {
				return true;
			}
		}catch (Exception e) {
			log.error("修改job最后执行时间出错。"+e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * 插入crontab执行日志
	 * @param joblog  crontab执行日志对象
	 * @return  true  插入成功 false 插入失败
	 *author maxinbo
	 */
	public  boolean  insSysJobLog(ZsJobLog joblog) {
		try {
			joblog.setInstime(new Date());
			if(zsJobMapper.getSQLManager().insert(ZsJobLog.class,joblog)>0) {
				return true;
			}
		}catch (Exception e) {
			log.error("插入crontab执行日志出错",e);
		}
		return false;
	}
	
	/**
	 * 更新crontab执行日志
	 * @param joblog  crontab执行日志对象
	 * @return  true  更新成功 false 更新失败
	 *author maxinbo
	 */
	public  boolean  updateSysJobLog(ZsJobLog joblog) {
		try {
			if(zsJobMapper.getSQLManager().updateTemplateById(joblog)>0) {
				return true;
			}
		}catch (Exception e) {
			log.error("插入crontab执行日志出错",e);
		}
		return false;
	}
}
