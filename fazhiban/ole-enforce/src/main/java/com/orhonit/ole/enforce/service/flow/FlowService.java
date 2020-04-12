package com.orhonit.ole.enforce.service.flow;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;

import com.orhonit.ole.enforce.dto.FlowCommentDTO;
import com.orhonit.ole.enforce.dto.FlowTaskCommentDTO;
import com.orhonit.ole.enforce.dto.NextOpinionDTO;
import com.orhonit.ole.enforce.dto.TaskDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseDealDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseTaskDTO;
import com.orhonit.ole.enforce.dto.api.ApiCheckTaskDTO;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.sys.dto.FlowDTO;

public interface FlowService {

	/**
	 * 启动流程
	 * @param key flow key
	 * @param businessId busiId
	 * @param variables start params
	 * @return processInstanceId
	 */
	String startFlowByKey(FlowDTO flowDTO);
	
	/**
	 * 完成任务
	 * @param pid
	 * @param createName
	 * @param variables
	 * @param comment
	 */
	void taskComplete(String pid, String createName, Map<String, Object> variables, String comment);
	
	/**
	 * 获取用户的所有代办任务
	 * @param assign
	 * @return
	 */
	List<TaskDTO> getUserTask(String assign, Integer currentPage, Integer pageSize);

	/**
	 * 按类型获取用户的所有代办任务
	 * @param assign
	 * @return
	 */
	List<TaskDTO> getUserTaskat(String assign, Integer currentPage, Integer pageSize,String type);

	/**
	 * 
	 * @param key
	 * @param businessId
	 * @return
	 */
	String getProcessInstanceIdByKeyAndBusinessId(String key , String businessId);
	
	/**
	 * 获取审批记录
	 */
	List<FlowTaskCommentDTO> getCommemntByProcessInstanceId(String pid);

	/**
	 * 完成任务
	 * @param flowDTO
	 */
	void taskComplete(FlowDTO flowDTO);

	/**
	 * 获取下一审核人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getHaveRoleOpinionByDeptId(String dept_id);
	
	/**
	 * 获取执法人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getHaveZfrRoleOpinionByDeptId(String dept_id);

	/**
	 * 获取下一审批人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getApproveUserByDeptId(String deptId);

	/**
	 * 根据任务ID获取流程定义ID及定义对象
	 * @param taskId
	 * @return
	 */
	ProcessDefinition getProcessDefinitionByTaskId(String taskId);

	/**
	 * 查看当前活动，获取当期活动对应的坐标x,y,width,height，
	 * 将4个值存放到Map<String,Object>中
	 * map集合的key：表示坐标x,y,width,height
	 * map集合的value：表示坐标对应的值
	 * @param taskId
	 * @return
	 */
	Map<String, Object> getCoordinateByTaskId(String taskId);

	/**
	 * 获取发布图片
	 * @param deployId
	 * @param imageName
	 * @return
	 */
	InputStream getResourceStream(String deployId, String imageName);
	
	/**
	 * 根据caseNum获取taskId
	 * @param caseNum
	 * @return
	 */
	String getTaskIdByCaseNum(String caseNum);

	/**
	 * 根据busikey和taskName获取历史审批意见
	 * @param busikey
	 * @param taskName
	 * @return
	 */
	List<FlowCommentDTO> getCommentByBusikeyAndTaskName(String busikey, String taskName);

	/**
	 * 获取带线的流程图
	 * @param taskId
	 * @return
	 */
	InputStream getCoordinateAndFlowSeqByTaskId(String taskId, String key, String businessId);
	
	/**
	 * 获取下一法制审核人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFzHaveRoleOpinionByDeptId(String deptId);
	
	/**
	 * 获取下一法制审批人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFzApproveUserByDeptId(String deptId);
	
	/**
	 * 获取下一法制受理人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFzHaveRoleCaseByDeptId(String deptId);
	
	/**
	 * 获取下一复议核查人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFyReviewVerificationByDeptId(String deptId);
	
	/**
	 * 获取下一复议审理人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFyReconsiderationByDeptId(String deptId);
	
	/**
	 * 获取本部门所有人员
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getPersonByDeptId(String dept_id);

	/**
	 * 获取具有听证受理人员列表
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getTzslReconsiderationByDeptId(String dept_id);
	
	/**
	 * 获取部门所在区域的法制办中的复议承办人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFyContractorByDeptIdArea(String dept_id);

	/**
	 * 获取部门所在区域得法制办中的复议科长
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFySectionChiefByDeptIdArea(String dept_id);

	/**
	 * 获取部门所在区域得法制办中的复议主任
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFyDirectorByDeptIdArea(String dept_id);

	/**
	 * 根据案件id获取当前案件的当事人
	 * @param caseId
	 * @return
	 */
	PartyInfoEntity getPartyByCaseId(String caseId);
	
	/**
	 * 获取当前登录人案件流程
	 * @param caseName
	 * @param assignee
	 * @param start
	 * @param length
	 * */
	List<ApiCaseTaskDTO> getCaseTask(Map<String, Object> params,Integer start,Integer length);
	
	/**
	 * 获取当前登录人日常检查流程
	 * @param caseName
	 * @param assignee
	 * @param start
	 * @param length
	 * */
	List<ApiCheckTaskDTO> getCheckDailyTask(Map<String, Object> params,Integer start,Integer length);
	
	/**
	 * 获取当前登录人案件流程
	 * @param caseName
	 * @param assignee
	 * @param start
	 * @param length
	 * */
	List<ApiCheckTaskDTO> getCheckTask(Map<String, Object> params,Integer start,Integer length);

	/**
	 * 获取当前登录人检查流程
	 * @param caseName
	 * @param assignee
	 * @param start
	 * @param length
	 * */
	List<LssuedEntity> getPcCheckTask(Map<String, Object> params,Integer start,Integer length);
	
	/**
	 * 统计当前登录人检查流程
	 * @param caseName
	 * @param assignee
	 * @param start
	 * @param length
	 * */
	int getPcCheckcount(Map<String, Object> params);
	
	/**
	 * 根据部门id获取部门下所有人员
	 * @param deptId
	 * @return
	 */
	List<ApiCaseDealDTO> getPerson(String deptId);

	/**
	 * 根据当前登录人所在部门性质获取有审核权限的人员列表
	 * @return
	 */
	List<NextOpinionDTO> getHaveRoleOpinionByDeptProperty(String dept_id);

	/**
	 * 根据当前登录人所在部门性质获取有审批权限的人员列表
	 * @return
	 */
	List<NextOpinionDTO> getApproveUserByDeptProperty(String dept_id);

	/**
	 * 根据当前登陆人获取待办任务汇总信息
	 * @return
	 */
	Map<String, String> getIndexCount();

}
