package com.orhonit.ole.tts.service.caseinfo;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.CaseDetailDTO;
import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseInfoDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptPersonDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.FlowDealDTO;
import com.orhonit.ole.tts.dto.LssuedDTO;
import com.orhonit.ole.tts.dto.NextOpinionDTO;
import com.orhonit.ole.tts.dto.PersonAppDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseInfoDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.dto.api.ApiCheckTypeDTO;
import com.orhonit.ole.tts.dto.api.ApiYujDTO;
import com.orhonit.ole.tts.dto.ps.AreaAndPotAndProPotDTO;
import com.orhonit.ole.tts.dto.ps.AreaAndPotDTO;
import com.orhonit.ole.tts.dto.ps.LoginUserDTO;
import com.orhonit.ole.tts.dto.ps.PotenceListDTO;
import com.orhonit.ole.tts.dto.ps.PsCaseDTO;
import com.orhonit.ole.tts.dto.ps.PsCheckDTO;
import com.orhonit.ole.tts.dto.ps.RigAndPotListDTO;
import com.orhonit.ole.tts.entity.AttachFileEntity;
import com.orhonit.ole.tts.entity.CaseEntity;
import com.orhonit.ole.tts.utils.PageList;

public interface CaseService {

	/**
	 * 保存案件
	 * @param caseEntity
	 */
	FlowDTO save(CaseEntity caseEntity);
	
	/**
	 * app案件上报
	 * @param caseEntity
	 */
	CaseEntity add(CaseEntity caseEntity);

	/**
	 * 获取案件内容
	 * @param id
	 * @return
	 */
	CaseInfoDTO findOne(String id);
	
	/**
	 * 获取当前登录人执法主体以及下属主体下的所有执法人员
	 * @return
	 */
	String getDeptUserByCurrentUser();
	
	
	/**
	 * 获取总数
	 * @param params
	 * @return
	 */
	Integer getCasecount(Map<String, Object> params);
	
	/**
	 * 获取总数
	 * @param params
	 * @return
	 */
	Integer findCasecount(Map<String, Object> params);

	Integer needDealCount(String id);
	
	Integer caseCountByStatus(int status);
	/**
	 * 获取列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * ps 手机端查询各地区案件列表
	 * @param caseId
	 * @return
	 */
	List<PsCaseDTO> apiCaseByAreaId(Map<String, Object> params);
	
	/**
	 * 获取案件详细信息，包括当事人
	 * @param caseId
	 * @return
	 */
	CaseDetailDTO findCaseDetail(Map<String, Object> params);
	
	/**
	 * 案件结案
	 * @param flowDealDTO  流程处理类
	 */
	void caseClosed(FlowDealDTO flowDealDTO);
	
	/**
	 * 修改案件状态
	 * @param caseId  案件编号
	 * @param caseStatus  案件状态
	 */
	void updateCaseStatus(String caseId, Integer caseStatus);

	/**
	 * 获取详细信息
	 * @param caseId
	 * @param params
	 * @return
	 */
	CaseDetailInfoDTO queryCaseByCaseId(String caseId, Map<String, Object> params);
	
	/**
	 * 获取案件文书
	 * @param caseId
	 * @return
	 */
	List<CaseDocDTO> queryDocContentByCaseId(String caseId);
	
	/**
	 * 修改案件状态
	 * @param caseId  案件编号
	 * @param caseStatus  案件状态
	 */
	void updateCaseStatusByCaseNum(String caseNum, Integer caseStatus, Integer flowType);

	/**
	 * 修改暂存状态的案件内容
	 * 且把暂存状态更改为初步核实
	 * @param caseEntity
	 */
	void update(CaseEntity caseEntity);

	
	/**
	 * PS按条件获取列表
	 * @param  
	 *  
	 * @return
	 */
	PageList<PsCaseDTO> tjCaseList(PsCaseDTO caseDTO);
	/**
	 * PS按条件查询案件详情
	 * @param  
	 *  
	 * @return
	 */
	PageList<PsCaseDTO> tjPcCaseList(PsCaseDTO caseDTO);
	/**
	 * PS
	 * 根据案件名称，当事人姓名、电话、身份证查询案件
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectCase(PsCaseDTO caseDTO);
	/**
	 * PS
	 * 查询各部门案件统计
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> selectCaseList( Map<String, Object> params);
	/**
	 * PS
	 * 查询各部门日常检查统计
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> selectDailyCaseList(Map<String, Object> params);
	/**
	 * PS
	 * 查询各地区日常检查统计
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> dailyList();
	/**
	 * PS
	 * 查询各地区专项检查统计
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> specialList();
	/**
	 * PS
	 * 根据deptId查询日常检查列表
	 * @param 
	 * @return
	 */
	PageList<PsCheckDTO> selectDailyBydeptId(PsCheckDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询日常检查列表
	 * @param 
	 * @return
	 */
	PageList<PsCheckDTO> ApiSelectDailyBydeptId(PsCheckDTO caseDTO);
	/**
	 * PS
	 * 根据deptId查询专项检查列表
	 * @param 
	 * @return
	 */
	PageList<PsCheckDTO> selectSpecialBydeptId(PsCheckDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询专项检查列表
	 * @param 
	 * @return
	 */
	PageList<PsCheckDTO> ApiSelectSpecialBydeptId(PsCheckDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询一般案件列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectGeneralCaseList(PsCaseDTO caseDTO);
	/**
	 * API
	 * Api根据登录人的部门查询一般简易案件最新列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectGeneralSimple(PsCaseDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询重大案件列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectSeriousCaseList(PsCaseDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询已结案案件列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectCasecaseList(PsCaseDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询简易案件列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> selectSimpleCaseList(PsCaseDTO caseDTO);
	/**
	 * API
	 * 根据deptId查询本年所有案件列表
	 * @param 
	 * @return
	 */
	PageList<PsCaseDTO> caseAllList(PsCaseDTO caseDTO);
	
	/**
	 * PS
	 * 查询各部门专项检查统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> selectSpecialCaseList(Map<String, Object> params);
	/**
	 * API
	 * 日常检查统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> LdDailyNum(Map<String, Object> params);
	/**
	 * API
	 * 一般案件统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> generalCaseNum(Map<String, Object> params);
	/**
	 * API
	 * 简易案件统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> simpleCaseNum(Map<String, Object> params);
	/**
	 * API
	 * 重大案件统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> SeriousCaseNum(Map<String, Object> params);
	/**
	 * API
	 * 日常检查统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> LdDailyNumByDept(Map<String, Object> params);
	
	/**
	 * API
	 * 日常检查统计分部门
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> dailyByDeptAll(Map<String, Object> params);
	/**
	 * API
	 * 专项检查统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> LdSpecialNum(Map<String, Object> params);
	
	/**
	 * API
	 * 案件统计
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> caseAll(Map<String, Object> params);
	/**
	 * API
	 * 专项检查统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> LdSpecialNumByDept(Map<String, Object> params);
	/**
	 * API
	 * 专项检查统计分部门
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> specialByDeptAll(Map<String, Object> params);
	/**
	 * API
	 * 本年案件统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> caseAllByArea(Map<String, Object> params);
	/**
	 * API
	 * 本年案件统计分部门
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> caseAllByAreaAll(Map<String, Object> params);
	/**
	 * API
	 * 本年预警统计分区域
	 * @param params
	 * @return
	 */
	List<ApiYujDTO> yujCountByArea(Map<String, Object> params);
	/**
	 * API
	 * 本年预警统计分部门
	 * @param params
	 * @return
	 */
	List<ApiYujDTO> yujCountByAreaAll(Map<String, Object> params);
	/**
	 * API
	 * 检查状态查询
	 * @param params
	 * @return
	 */
	List<ApiCheckTypeDTO> checkStatusByDeptId(Map<String, Object> params);
	/**
	 * API
	 * 一般案件统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> generalCaseNumByDept(Map<String, Object> params);
	/**
	 * API
	 * 简易案件统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> simpleCaseNumByDept(Map<String, Object> params);
	/**
	 * API
	 * 重大案件统计分区域
	 * @param params
	 * @return
	 */
	List<PsCaseDTO> SeriousCaseByDept(Map<String, Object> params);
	/**
	 * 根据案件编号获取案件信息
	 * @param caseNum
	 * @return
	 */
	CaseEntity getCaseByCaseNum(String caseNum);
	
	/**
	 * app
	 * 获取案件列表
	 * @param params
	 * @return
	 */
	List<ApiCaseListDTO> findCaseList(Map<String, Object> params);
	
	/**
	 * app
	 * 获取案件详情
	 * @param params
	 * @return
	 */
	ApiCaseInfoDTO findCaseInfo(Map<String, Object> params);
	
	/**
	 * ps查询各区域下案件数量
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> psCaseList();
	/**
	 * API查询各区域下已结案案件数量
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> caseCaseNumByArea(Map<String, Object> params);
	/**
	 * API查询该部门下已结案案件数量
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> caseCaseNumByDept(Map<String, Object> params);
	/**
	 * PC各辖区案件数量统计
	 * @param 
	 * @return
	 */
	List<PsCaseDTO> psPcCaseList();
	/**
	 * ps 
	 * 权责列表查询
	 */
	List<PotenceListDTO> selectPotenceList(Map<String, Object> plMap);
	/**
	 * ps 
	 * 权责列表查询
	 */
	List<PotenceListDTO> selectPotenceListBydeptId(Map<String, Object> plMap);

	Integer getQueryCasecount(Map<String, Object> params);

	List<CaseListDTO> getQueryCaseList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * ps
	 * 各个地区权责清单统计
	 * @return
	 */
	List<AreaAndPotDTO> AreaAndPotSelect();
	/**
	 * ps
	 * 一个地区下的一个部门或全部部门的权责分类统计
	 * @param areaId 区域ID
	 * @param deptId 部门ID
	 * @return
	 */

	List<AreaAndPotAndProPotDTO> getAreaAndPotAndProPot(Map<String, Object> areaAndPotAndProPotMap);
	/**
	 * ps
	 * 权责清单（条形图）
	 * @return
	 */
	List<RigAndPotListDTO> RigAndPotList();


	
	/**
	 * 立案关联权责
	 * @return 
	 */
	CaseEntity updatePotence(CaseEntity caseEntity);
	
	CaseEntity updatePotenceAndSource(CaseEntity caseEntity);
	/**
	 * ps根据案件Id查询案件详情
	 * @return
	 * */
	PsCaseDTO psCase(Map<String, Object> params);

	/**
	 * ps 权责详细信息
	 * @return
	 * */
	AreaAndPotDTO potenceDetail(Map<String, Object> map);


	Map<String, Integer> getLeaderCaseCount(PersonAppDTO personAppDTO, List<Integer> caseStatusList);

    List<CaseListDTO> getLeaderApprove(PersonAppDTO personAppDTO, List<Integer> caseStatusList, String caseName, String start, String length);

	LssuedDTO findCheckName(String caseSource, String checkId);

	DeptPersonDTO getByCaseId(String id);
	
	/**
	 * 获取下一复议核查人
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getFyReviewVerificationByDeptId(String deptId);
	/**
	 * 获取具有听证受理人员列表
	 * @param dept_id
	 * @return
	 */
	List<NextOpinionDTO> getTzslReconsiderationByDeptId(String dept_id);
	/**
	 * 公示案件登录
	 * @param dept_id
	 * @return
	 */
	LoginUserDTO login(LoginUserDTO User);
	/**
	 * ps陈述申辩案件列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListCssb(Map<String, Object> map);
	/**
	 * ps陈述申辩公司案件列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListCssbGs(Map<String, Object> map);
	/**
	 *  ps申请复议列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListSqfy(Map<String, Object> map);
	/**
	 *  ps申请复议列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListSqfyGs(Map<String, Object> map);
	
	/**
	 * ps获取当前登录人申请听证案件列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListSqtz(Map<String, Object> map);
	/**
	 * ps获取当前登录人申请听证公司案件列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getListSqtzGs(Map<String, Object> map);
	/**
	 * 生成卷宗目录
	 * @param caseId
	 */
	void createCaseCateLog(String caseId);
	
	/**
	 * 获取文件列表
	 * 
	 * @param caseId
	 */
	List<AttachFileEntity> caseFileList(String caseId, String baseUrl);
	
	/**
	 * 保存每个人物的相关信息
	 * @param flowDTO
	 * @param isStart
	 */
	void saveTaskEntity(FlowDTO flowDTO, Boolean isStart);
	
	/**
	 * 完成任务
	 * @param flowDTO
	 */
	void taskComplete(FlowDTO flowDTO);
	
	/**
	 * 
	 * @param key
	 * @param businessId
	 * @return
	 */
	String getProcessInstanceIdByKeyAndBusinessId(String key , String businessId);

	/**
	 * 保存是否公示字段
	 * @return 
	 * 
	 */
	void updateIsPs(CaseEntity caseEntity);
	
	/**
	 * 案件相关附件拷贝到归档目录
	 * 
	 * @param caseId
	 */
	void caseFileCopy(String caseId);
	
	/**
	 * APP  案件立案  更新权责，案源，办案时限
	 * **/
	CaseEntity updataCaseInfo(CaseEntity caseEntity);
	/**
	 * APP 重大详情案件
	 * **/
	PsCaseDTO getCaseByCaseId(Map<String, Object> caseIdMp);
	/**
	 * APP 日常检查案件
	 * **/
	PsCheckDTO getDailyByCaseId(Map<String, Object> caseIdMp);
	/**
	 * APP 专项检查案件
	 * **/
	PsCheckDTO getSpecialByCaseId(Map<String, Object> caseIdMp);
	
	/**
	 * 获取当前部门及其下级部门
	 * @param deptId
	 * @return
	 */
	String execFunction(String deptId);
	
	/**
	 * 根据案件状态和区划统计案件数量
	 * @param status
	 * @return
	 */
	List<Map<String,Object>> getCaseCountAreaByStatus(Map<String,Object> params);
	
	/**
	 * 根据案件状态和区划统计预警数量
	 * @param status
	 * @return
	 */
	List<Map<String,Object>> getWarnCountForArea(Map<String,Object> params);

	/**
	 * 根据区划信息统计日常检查数量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getCheckDailyCountForArea(Map<String, Object> params);

	/**
	 * 根据区划信息统计专项检查数量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getCheckCountForArea(Map<String, Object> params);

	/**
	 * 按案件状态统计案件数量
	 * @param params
	 */
	String getCaseCountByStatus(Map<String, Object> params);

	/**
	 * 根据流程类型统计案件数量
	 * @param params
	 * @return
	 */
	String getCaseCountByDeal(Map<String, Object> params);

	String getCheckCountByDeptId(Map<String, Object> params);

	String getCheckDailyCountByDeptId(Map<String, Object> params);
	
	/**
	 * API
	 * 本年所有预警统计
	 * @param params
	 * @return
	 */
	List<ApiYujDTO> yujCount(Map<String, Object> params);

	List<CaseInfoDTO> getCasePositionList(Map<String, Object> params);
}
