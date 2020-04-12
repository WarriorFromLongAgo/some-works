package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.entity.WarnPersonEntity;

@Mapper
public interface TaskDao {

	Integer getTaskCount(@Param("params") Map<String, Object> params);

	List<TaskDTO> getTaskList(@Param("params") Map<String, Object> params, @Param("start") Integer start, @Param("length") Integer length);

	void save(TaskDTO taskDTO);

	TaskDTO queryObject(@Param("jobId") Long jobId);

	List<Map<String,Object>> execSql(@Param("sql") String sql);
	
	Map<String,Object> getOldPersonByPersonId(@Param("personId") String personId);
	
	Map<String,Object> getOldDeptByDeptId(@Param("deptId") String deptId);
	
	Map<String,Object> getOldPotenceByPotenceId(@Param("potenceId") String potenceId); 
	
	@Select("SELECT column_name,column_comment FROM information_schema.columns WHERE table_name = #{tableName}")
	List<Map<String,Object>> getTable(@Param("tableName") String tableName);
	
	int updateBatch(Map<String, Object> map);

	void update(TaskDTO taskDTO);

    TaskDTO getTaskByJobId(Integer jobId);

	void delete(@Param("jobId") Long[] jobIds);
	
	List<WarnPersonEntity> getPersonListByCaseNum(@Param("caseNum")String caseNum,@Param("roleIds") String roleIds);
	
	List<WarnPersonEntity> getPersonListByCheckDailyNum(@Param("checkNum")String checkNum,@Param("roleIds") String roleIds);
	
	List<WarnPersonEntity> getPersonListByCheckNum(@Param("checkNum")String checkNum,@Param("roleIds") String roleIds);
	
	List<WarnPersonEntity> getPersonListByDeptId(@Param("deptId")String deptId,@Param("roleIds") String roleIds);
	
	@Select("SELECT dept_id,dept_id_agent FROM ole_base_potence_dept WHERE potence_id = #{potenceId}")
	List<Map<String, String>> getDeptByPotenceId(@Param("potenceId")String potenceId);
}
