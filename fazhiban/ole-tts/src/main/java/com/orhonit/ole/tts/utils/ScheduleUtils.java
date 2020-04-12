package com.orhonit.ole.tts.utils;

import com.google.gson.Gson;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.entity.ScheduleJobEntity;
import com.orhonit.ole.tts.exception.TTSException;


import org.quartz.*;

/**
 * 定时任务工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午12:44:59
 */
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_";
    
    /**
     * 获取触发器key
     */
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 获取jobKey
     */
    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
        	e.printStackTrace();
            throw new TTSException(500,"getCronTrigger异常，请检查qrtz开头的表，是否有脏数据");
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, TaskDTO taskDTO) {
        try {
        	//构建job
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(Long.valueOf(taskDTO.getJobId()))).build();

            //构建cron
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskDTO.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            //根据cron，构建一个CronTrigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(Long.valueOf(taskDTO.getJobId()))).
                    withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, new Gson().toJson(taskDTO));

            scheduler.scheduleJob(jobDetail, trigger);
            
            //暂停任务
            if(taskDTO.getStatus() == CommonParameters.Effect.EFFECT){
            	pauseJob(scheduler, Long.valueOf(taskDTO.getJobId()));
            }
        } catch (SchedulerException e) {
            throw new TTSException(200,"创建定时任务失败");
        }
    }
    
    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, TaskDTO taskDTO) {
        try {
            TriggerKey triggerKey = getTriggerKey(Long.valueOf(taskDTO.getJobId()));

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskDTO.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, Long.valueOf(taskDTO.getJobId()));
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, new Gson().toJson(taskDTO));
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(taskDTO.getStatus() == CommonParameters.Effect.EFFECT){
            	pauseJob(scheduler, Long.valueOf(taskDTO.getJobId()));
            }
            
        } catch (SchedulerException e) {
            throw new TTSException(500,"更新定时任务失败");
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, TaskDTO taskDTO) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(ScheduleJobEntity.JOB_PARAM_KEY, new Gson().toJson(taskDTO));
        	
            scheduler.triggerJob(getJobKey(Long.valueOf(taskDTO.getJobId())), dataMap);
        } catch (SchedulerException e) {
            throw new TTSException(500,"立即执行定时任务失败");
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new TTSException(500,"暂停定时任务失败");
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new TTSException(500,"暂停定时任务失败");
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new TTSException(500,"删除定时任务失败");
        }
    }

  
}
