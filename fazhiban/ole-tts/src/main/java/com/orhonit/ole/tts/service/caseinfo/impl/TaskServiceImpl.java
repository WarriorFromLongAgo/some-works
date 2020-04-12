package com.orhonit.ole.tts.service.caseinfo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.tts.dao.TaskDao;
import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.service.caseinfo.TaskService;
import com.orhonit.ole.tts.service.warn.WarnInfoService;
import com.orhonit.ole.tts.service.warn.WarnPersonService;
import com.orhonit.ole.tts.utils.ScheduleUtils;




@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
    private Scheduler scheduler;
	
	@Autowired
	private WarnInfoService warnInfoService;
	
	@Autowired
	private WarnPersonService warnPersonService;
	
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<TaskDTO> taskDTOs = taskDao.getTaskList(new HashMap<>(),null,null);
		for(TaskDTO taskDTO : taskDTOs){
			System.out.println(taskDTO);
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, Long.valueOf(taskDTO.getJobId()));
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, taskDTO);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, taskDTO);
            }
		}
	}

	@Override
	public Integer getTaskCount(Map<String, Object> params) {
		return this.taskDao.getTaskCount(params);
	}

	@Override
	public List<TaskDTO> getTaskList(Map<String, Object> params, Integer start, Integer length) {
		return this.taskDao.getTaskList(params, start, length);
	}

	@Override
	public void save(TaskDTO taskDTO) {
		taskDTO.setStatus(CommonParameters.Effect.EFFECT);
		taskDTO.setCreateTime(new Date());
		this.taskDao.save(taskDTO);
		
		ScheduleUtils.createScheduleJob(scheduler, taskDTO);
	}
	
	@Override
	public void update(TaskDTO taskDTO) {
		taskDTO.setStatus(CommonParameters.Effect.EFFECT);
		ScheduleUtils.updateScheduleJob(scheduler,taskDTO);
		this.taskDao.update(taskDTO);
	}
	
	@Override
	public void delete(Long[] jobIds){
		for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
		this.taskDao.delete(jobIds);
	}
	
	@Override
	public void run(Integer jobId) {
		ScheduleUtils.run(scheduler, queryObject(jobId));
	}

	@Override
	public TaskDTO queryObject(Integer jobId) {
		return taskDao.queryObject(Long.valueOf(jobId));
	}

	@Override
	public List<Map<String,Object>> execSql(String sql) {
		return this.taskDao.execSql(sql);
	}
	
	@Override
	public Map<String,Object> getOldPersonByPersonId(String personId) {
		return this.taskDao.getOldPersonByPersonId(personId);
	}
	
	@Override
	public Map<String,Object> getOldDeptByDeptId(String deptId) {
		return this.taskDao.getOldDeptByDeptId(deptId);
	}
	
	@Override
	public void pause(Long[] jobIds) {
		for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, CommonParameters.Effect.EFFECT);
	}

	@Override
	public void resume(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.resumeJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, CommonParameters.Effect.NOT_EFFECT);
	}

	@Override
	public TaskDTO getTaskByJobId(Integer jobId) {
		return this.taskDao.getTaskByJobId(jobId);
	}

	public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return taskDao.updateBatch(map);
    }
	
	@Override
	public List<WarnPersonEntity> getPersonListByCaseNum(String caseNum, String roleIds) {
		if(caseNum!= null && !"".equals(caseNum) && roleIds!= null && !"".equals(roleIds)){
			return taskDao.getPersonListByCaseNum(caseNum, roleIds);
		}
			return null;
	}
	
	@Override
	public List<WarnPersonEntity> getPersonListByCheckDailyNum(String checkNum, String roleIds) {
		if(checkNum!= null && !"".equals(checkNum) && roleIds!= null && !"".equals(roleIds)){
			return taskDao.getPersonListByCheckDailyNum(checkNum, roleIds);
		}
			return null;
	}
	
	@Override
	public List<WarnPersonEntity> getPersonListByCheckNum(String checkNum, String roleIds) {
		if(checkNum!= null && !"".equals(checkNum) && roleIds!= null && !"".equals(roleIds)){
			return taskDao.getPersonListByCheckNum(checkNum, roleIds);
		}
			return null;
	}
	
	@Override
	public List<WarnPersonEntity> getPersonListByDeptId(String deptId, String roleIds) {
		if(deptId!= null && !"".equals(deptId) && roleIds!= null && !"".equals(roleIds)){
			return taskDao.getPersonListByDeptId(deptId, roleIds);
		}
		return null;
	}

	@Override
	public Map<String, Object> getTable(String tableName) {
		List<Map<String,Object>> T = this.taskDao.getTable(tableName);
		Map<String,Object> table = new HashMap<String,Object>();
		T.forEach(item ->{
			table.put(item.get("column_name").toString() , item.get("column_comment"));
		});
		return table;
	}

	@Override
	public List<String> getDeptByPotenceId(String potenceId) {
		List<Map<String,String>> depts = this.taskDao.getDeptByPotenceId(potenceId);
		List<String> dept = new ArrayList<String>();
		for (Map<String, String> map : depts) {
			dept.add(map.get("dept_id"));
			dept.add(map.get("dept_id_agent"));
		}
		return dept;
	}

	@Override
	public Map<String, Object> getOldPotenceByPotenceId(String potenceId) {
		return this.taskDao.getOldPotenceByPotenceId(potenceId);
	}

	@Override
	public void saveWarnInfo(WarnInfoEntity warnInfoEntity, List<WarnPersonEntity> warnPersonEntitys) {
		List<WarnPersonEntity> warnPersonEntityList = new ArrayList<>();
		//组装需要发送的预警信息
		if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
			WarnPersonEntity warn=warnPersonEntitys.get(0);
			warnInfoEntity.setId(UUID.randomUUID().toString());
			warnInfoEntity.setRecordId(warn.getRecordId());
			warnInfoEntity.setRecordTitle(warn.getRecordTitle());
			warnInfoEntity.setRecordStatus(warn.getRecordStatus());
			warnInfoEntity.setCreateDate(new Date());
			warnInfoEntity.setCreateBy("EWT");//Early warning task
			warnInfoEntity.setCreateName("预警任务");
			this.warnInfoService.save(warnInfoEntity);
		}
		WarnPersonEntity warnPersonEntity;
		for (WarnPersonEntity warn : warnPersonEntitys) {
			warnPersonEntity=new WarnPersonEntity();
			warnPersonEntity.setId(UUID.randomUUID().toString());
			warnPersonEntity.setWarnId(warnInfoEntity.getId());
			warnPersonEntity.setIsDeal(CommonParameters.YuJChuL.WCL);
			warnPersonEntity.setDeptId(warn.getDeptId());
			warnPersonEntity.setPersonId(warn.getPersonId());
			warnPersonEntity.setCreateDate(new Date());
			warnPersonEntity.setCreateBy("EWT");
			warnPersonEntity.setCreateName("预警任务");
			warnPersonEntityList.add(warnPersonEntity);
		}
		
		if ( warnPersonEntityList != null && warnPersonEntityList.size() > 0 ) {
			this.warnPersonService.save(warnPersonEntityList);
		}
		
	}
}
