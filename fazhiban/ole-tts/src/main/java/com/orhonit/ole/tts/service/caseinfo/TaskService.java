package com.orhonit.ole.tts.service.caseinfo;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;


public interface TaskService {

	Integer getTaskCount(Map<String, Object> params);

	List<TaskDTO> getTaskList(Map<String, Object> params, Integer start, Integer length);

	void save(TaskDTO taskDTO);
	
	void update(TaskDTO taskDTO);
	
	void run(Integer jobId);

	TaskDTO queryObject(Integer jobId);

	List<Map<String,Object>> execSql(String sql);
	
	Map<String,Object> getOldPersonByPersonId(String personId);
	
	Map<String,Object> getTable(String tableName);

	void pause(Long[] jobIds);

    TaskDTO getTaskByJobId(Integer jobId);

	void delete(Long[] jobIds);

	void resume(Long[] jobIds);
	
	List<WarnPersonEntity> getPersonListByCaseNum(String caseNum,String roleIds);
	
	List<WarnPersonEntity> getPersonListByCheckDailyNum(String checkNum,String roleIds);
	
	List<WarnPersonEntity> getPersonListByCheckNum(String checkNum,String roleIds);
	
	List<WarnPersonEntity> getPersonListByDeptId(String deptId,String roleIds);

	Map<String, Object> getOldDeptByDeptId(String deptId);
	
	List<String> getDeptByPotenceId(String potenceId);

	Map<String, Object> getOldPotenceByPotenceId(String potenceId);

	/*
	 * 保存预警和和预警人员信息
	 */
	void  saveWarnInfo(WarnInfoEntity warnInfoEntity, List<WarnPersonEntity> warnPersonEntitys);
	
}
