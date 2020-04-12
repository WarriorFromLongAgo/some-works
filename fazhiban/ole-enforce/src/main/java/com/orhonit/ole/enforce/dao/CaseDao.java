package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.enforce.dto.CaseDTO;
import com.orhonit.ole.enforce.dto.CaseDetailDTO;
import com.orhonit.ole.enforce.dto.CaseDetailInfoDTO;
import com.orhonit.ole.enforce.dto.CaseDocDTO;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.DeptPersonDTO;
import com.orhonit.ole.enforce.dto.LssuedDTO;
import com.orhonit.ole.enforce.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.enforce.dto.NextOpinionDTO;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.PotenceDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseDocDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseInfoDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseListDTO;
import com.orhonit.ole.enforce.dto.api.ApiCheckTypeDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.dto.api.ApiDailyCheckDTO;
import com.orhonit.ole.enforce.dto.api.ApiYujDTO;
import com.orhonit.ole.enforce.dto.ps.LoginUserDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.dto.ps.PsCheckDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;

@Mapper
public interface CaseDao {

	String getCaseZpr(String id);

	/**
	 * 获取案件详细信息，包括当事人
	 * 
	 * @param caseId
	 * @return
	 */
	CaseDetailDTO findCaseDetail(@Param("params") Map<String, Object> params);

	List<DeptPersonDTO> getDeptUserByCurrentUser(@Param("deptId") String deptId, @Param("roleId") Integer roleId);

	List<CaseListDTO> caseList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);

	List<CaseListDTO> caseQuery(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
//	List<CaseListDTO> caseQueryApp(@Param("params") Map<String, Object> params, @Param("start") Integer start,
//			@Param("length") Integer length);
	List<CaseListDTO> caseQueryApp(@Param("params") Map<String, Object> params);

	Integer count(@Param("params") Map<String, Object> params);
	
	ApiCountDTO caseCountByStatus(@Param("params") Map<String, Object> param);
	
	ApiCountDTO needDealCount(@Param("id") String id);
	
	ApiCountDTO caseSubmitCount(@Param("id") String id);
	ApiCountDTO caseSubmitCountByDeptid(@Param("params") Map<String, Object> param);
	
	
	Integer caseCount(@Param("params") Map<String, Object> params);

	Integer caseQuerycount(@Param("params") Map<String, Object> params);
	
	Integer caseQuerycountApp(@Param("params") Map<String, Object> params);
	
	
	ApiCaseInfoDTO getCaseByCaseNum(@Param("caseNum") String caseNum);

	/**
	 * 修改案件状态
	 * 
	 * @param caseId
	 *            案件ID
	 * @param caseStatus
	 *            案件状态
	 */
	@Update("update ole_ef_case t set t.case_status = #{caseStatus} where t.id = #{caseId}")
	void updateCaseStatus(@Param("caseId") String caseId, @Param("caseStatus") Integer caseStatus);

	/**
	 * 修改案件状态
	 * 
	 * @param caseId
	 *            案件编号
	 * @param caseStatus
	 *            案件状态
	 */
	@Update("update ole_ef_case t set t.case_status = #{caseStatus} where t.case_num = #{caseNum}")
	void updateCaseStatusByCaseNum(@Param("caseNum") String caseNum, @Param("caseStatus") Integer caseStatus);

	/**
	 * 获取案件非常详细的信息
	 * 
	 * @param caseId
	 * @return
	 */
	CaseDetailInfoDTO getCaseDetailInfo(@Param("caseId") String caseId, @Param("params") Map<String, Object> params);

	/**
	 * 获取案件详情(反显)
	 * 
	 * @param caseId
	 * @return
	 */
	CaseDTO getCaseDTO(@Param("caseId") String caseId);

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	List<CaseDocDTO> findCaseDoc(String caseId);

	/**
	 * PS按条件查询案件详情
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> tjCaseList(PsCaseDTO caseDTO);

	/**
	 * API按条件查询一般案件详情
	 * 
	 * @param deptId areaId
	 * @return
	 */
	List<PsCaseDTO> selectGeneralCaseList(PsCaseDTO caseDTO);
	
	/**
	 * Api根据登录人的部门查询一般简易案件最新列表
	 * 
	 * @param deptId areaId
	 * @return
	 */
	List<PsCaseDTO> selectGeneralSimple(PsCaseDTO caseDTO);

	/**
	 * API按条件查询重大案件详情
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> selectSeriousCaseList(PsCaseDTO caseDTO);
	
	List<PsCaseDTO> selectSeriousCaseListApp(PsCaseDTO caseDTO);	
//	List<PsCaseDTO> selectSeriousCaseListMap(Map<String, Object> map);

	/**
	 * API按条件查询已结案案件详情
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> selectCasecaseList(PsCaseDTO caseDTO);

	/**
	 * API按条件查询简易案件详情
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> selectSimpleCaseList(PsCaseDTO caseDTO);
	
	/**
	 * API按条件查询本年所有案件列表
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> caseAllList(PsCaseDTO caseDTO);
	
	/**
	 * API按条件查询本年所有案件列表 
	 * 
	 * @param deptId
	 *            areaId
	 * @return CaseListDTO对象集合
	 */
//	List<PsCaseDTO> caseAllListMap(PsCaseDTO caseDTO);//PsCaseDTO caseDTO
	List<CaseListDTO> caseAllListMap(Map<String, Object> map);//PsCaseDTO caseDTO
	
	/**
	 * PS PC 案件列表按条件查询
	 * 
	 * @param deptId
	 *            areaId
	 * @return
	 */
	List<PsCaseDTO> tjPcCaseList(PsCaseDTO caseDTO);

	/**
	 * PS查询案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getMessageNum(PsCaseDTO caseDTO);

	/**
	 * API查询一般案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getGeneralNum(PsCaseDTO caseDTO);
	
	/**
	 * Api根据登录人的部门查询一般简易案件最新列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int selectGeneralSimpleNum(PsCaseDTO caseDTO);

	/**
	 * API查询重大案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getSeriousNum(PsCaseDTO caseDTO);
	
	public int getSeriousNumApp(PsCaseDTO caseDTO);
	public int getSeriousNumMap(Map<String, Object> map);

	/**
	 * API查询已结案案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getCasecaseNum(PsCaseDTO caseDTO);

	/**
	 * API查询简易案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getSimpleNum(PsCaseDTO caseDTO);
	/**
	 * API查询本年所有案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int caseAllListNum(PsCaseDTO caseDTO);
	
//	public Integer caseAllListNumMap(PsCaseDTO caseDTO);
	public Integer caseAllListNumMap(@Param("params") Map<String, Object> map);
	
	/**
	 * PS PC 案件列表查询总数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getPcMessageNum(PsCaseDTO caseDTO);

	/**
	 * PS查询案件列表总条数
	 * 
	 * @param areaId
	 *            区域name
	 * @return
	 */
	public int getSelectNum(PsCaseDTO caseDTO);

	/**
	 * PS查询日常检查列表总条数
	 * 
	 * @param
	 * @return
	 */
	public int getDailyNum(PsCheckDTO caseDTO);

	/**
	 * Api查询日常检查列表总条数
	 * 
	 * @param
	 * @return
	 */
	public Integer ApiDailyNum(PsCheckDTO caseDTO);
	
	public int ApiDailyNumMap(Map<String, Object> map);

	/**
	 * API查询日常检查列表总条数
	 * 
	 * @param
	 * @return
	 */
	public int ApiGetDailyNum(PsCheckDTO caseDTO);

	/**
	 * PS查询专项检查列表总条数
	 * 
	 * @param
	 * @return
	 */
	public int getSpecialNum(PsCheckDTO caseDTO);

	/**
	 * API查询专项检查列表总条数
	 * 
	 * @param
	 * @return
	 */
	public int ApiSpecialNum(PsCheckDTO caseDTO);
	
	public int ApiSpecialNumMap(Map<String, Object> map);

	/**
	 * PS根据案件名称，当事人姓名、电话、身份证查询案件
	 * 
	 * @return
	 */
	List<PsCaseDTO> selectCase(PsCaseDTO caseDTO);

	/**
	 * PS查询各部门案件统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> selectCaseList(@Param("params") Map<String, Object> params);

	/**
	 * PS查询各部门日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> selectDailyCaseList(@Param("params") Map<String, Object> params);

	/**
	 * Api查询领导端日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdDdailyNum();

	/**
	 * Api查询部门日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> ApiDailyCase(@Param("params") Map<String, Object> params);

	/**
	 * PS查询地区门日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> dailyList();

	/**
	 * API查询地区门日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> ApiDailyList();

	/**
	 * PS查询地区门专项检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> specialList();

	/**
	 * 根据deptId查询日常检查列表
	 * 
	 * @return
	 */
	List<PsCheckDTO> selectDailyBydeptId(PsCheckDTO caseDTO);

	/**
	 * API根据deptId查询日常检查列表
	 * 
	 * @return
	 */
	List<PsCheckDTO> ApiSelectDailyBydeptId(PsCheckDTO caseDTO);
	
	List<PsCheckDTO> ApiSelectDailyBydeptIdMap(Map<String, Object> map);

	/**
	 * 根据deptId查询专项检查列表
	 * 
	 * @return
	 */
	List<PsCheckDTO> selectSpecialBydeptId(PsCheckDTO caseDTO);

	/**
	 * API根据deptId查询专项检查列表
	 * 
	 * @return
	 */
	List<PsCheckDTO> ApiSelectSpecialBydeptId(PsCheckDTO caseDTO);
	
	List<PsCheckDTO> ApiSelectSpecialBydeptIdMap(Map<String, Object> map);
	
	/**
	 * API领导端专项检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdSpecialNum();

	/**
	 * PS查询各部门专项检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> selectSpecialCaseList(@Param("params") Map<String, Object> params);

	/**
	 * API日常检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdDailyNum(@Param("params") Map<String, Object> params);
	
	List<PsCaseDTO> LdDailyNumMap(@Param("params") Map<String, Object> params);

	/**
	 * API一般案件统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> generalCaseNum(@Param("params") Map<String, Object> params);

	/**
	 * API简易案件统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> simpleCaseNum(@Param("params") Map<String, Object> params);

	/**
	 * API重大案件统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> SeriousCaseNum(@Param("params") Map<String, Object> params);

	/**
	 * API日常检查统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdSpecialNumByDept(@Param("params") Map<String, Object> params);
	
	/**
	 * API日常检查统计分部门
	 * 
	 * @return
	 */
	List<PsCaseDTO> specialByDeptAll(@Param("params") Map<String, Object> params);
	
	/**
	 * API日常检查统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> caseAllByArea(@Param("params") Map<String, Object> params);
	
	/**
	 * API日常检查统计分部门
	 * 
	 * @return
	 */
	List<PsCaseDTO> caseAllByAreaAll(@Param("params") Map<String, Object> params);
	
	/**
	 * API预警统计分区域
	 * 
	 * @return
	 */
	List<ApiYujDTO> yujCountByArea(@Param("params") Map<String, Object> params);
	
	/**
	 * API检查状态查询
	 * 
	 * @return
	 */
	List<ApiCheckTypeDTO> checkStatusByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * API预警统计分部门
	 * 
	 * @return
	 */
	List<ApiYujDTO> yujCountByAreaAll(@Param("params") Map<String, Object> params);

	/**
	 * API一般案件统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> generalCaseNumByDept(@Param("params") Map<String, Object> params);
	
	/**
	 * API一般和简易案件统计分部门
	 * 
	 * @return
	 */
	List<PsCaseDTO> generalCaseDept(@Param("params") Map<String, Object> params);

	/**
	 * API简易案件统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> simpleCaseNumByDept(@Param("params") Map<String, Object> params);

	/**
	 * API重大案件统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> SeriousCaseByDept(@Param("params") Map<String, Object> params);

	/**
	 * API日常检查统计分区域
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdDailyNumByDept(@Param("params") Map<String, Object> params);
	
	/**
	 * API日常检查统计分部门
	 * 
	 * @return
	 */
	List<PsCaseDTO> dailyByDeptAll(@Param("params") Map<String, Object> params);

	/**
	 * API专项检查统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> LdSpecialNum(@Param("params") Map<String, Object> params);
	
	List<PsCaseDTO> LdSpecialNumMap(@Param("params") Map<String, Object> params);

	/**
	 * API本年所有案件统计
	 * 
	 * @return
	 */
	List<PsCaseDTO> caseAll(@Param("params") Map<String, Object> params);
	/**
	 * 根据主体ID查询案件列表
	 * 
	 * @return
	 */
	List<PsCaseDTO> selectCaseListBydeptId(PsCaseDTO caseDTO);

	/**
	 * APP获取案件列表
	 * 
	 * @return
	 */
	List<ApiCaseListDTO> findCaseList(@Param("params") Map<String, Object> params);

	/**
	 * ps 手机端查询各地区案件列表
	 * 
	 * @return
	 */
	List<PsCaseDTO> apiCaseByAreaId(@Param("params") Map<String, Object> params);

	/**
	 * APP获取案件详情
	 * 
	 * @return
	 */
	ApiCaseInfoDTO findCaseInfo(@Param("params") Map<String, Object> params);

	/**
	 * 查询文书内容
	 * 
	 * @return
	 */
	ApiCaseDocDTO findDocInfo(@Param("params") Map<String, Object> params);

	/**
	 * ps各辖区案件数量统计
	 * 
	 * @param
	 * 
	 * 
	 * @return
	 */
	List<PsCaseDTO> psCaseList();

	/**
	 * API各辖区已结案案件数量统计
	 * 
	 * @param
	 * 
	 * 
	 * @return
	 */
	List<PsCaseDTO> caseCaseNumByArea(@Param("params") Map<String, Object> params);

	/**
	 * API该部门已结案案件数量统计
	 * 
	 * @param
	 * 
	 * 
	 * @return
	 */
	List<PsCaseDTO> caseCaseNumByDept(@Param("params") Map<String, Object> params);

	/**
	 * PC各辖区案件数量统计
	 * 
	 * @param
	 * 
	 * 
	 * @return
	 */
	List<PsCaseDTO> psPcCaseList();

	/**
	 * ps根据案件Id查询案件详情
	 * 
	 * @return
	 */
	PsCaseDTO psCase(@Param("params") Map<String, Object> params);

	int getLeaderCaseCount(@Param("personAppDTO") PersonAppDTO personAppDTO,
			@Param("caseStatusList") String caseStatusList);

	List<CaseListDTO> getLeaderApprove(@Param("personAppDTO") PersonAppDTO personAppDTO,
			@Param("caseStatusList") List<Integer> caseStatusList, @Param("caseName") String caseName,
			@Param("start") Integer start, @Param("length") Integer length);

	CheckDailyDTO newCheckDaily(CheckDailyDTO checkDailyDTO);

	@Select("SELECT dy.id AS id,dy.check_title AS checkTitle FROM ole_ef_check_daily dy WHERE dy.id=#{checkId}")
	LssuedDTO findCheckDailyName(String checkId);

	@Select("SELECT ck.id AS id,ck.check_title AS checkTitle FROM ole_ef_check ck WHERE ck.id=#{checkId}")
	LssuedDTO findLssuedName(String checkId);

	DeptPersonDTO getByCaseId(String id);

	String execFunction(@Param("functionName") String functionName, @Param("deptId") String deptId);

	@Update("update ole_ef_case t set t.case_status = #{caseStatus}, t.flow_type = #{flowType} where t.case_num = #{caseNum}")
	void updateCaseStatusAndFlowTypeByCaseNum(@Param("caseNum") String caseNum, @Param("caseStatus") Integer caseStatus,
			@Param("flowType") Integer flowType);

	List<NextOpinionDTO> getHaveRoleOpinionByDeptId(@Param("deptId") String deptId, @Param("roleId") Integer roleId);

	LoginUserDTO login(LoginUserDTO User);

	List<CaseListDTO> getListCssb(@Param("params") Map<String, Object> map);

	List<CaseListDTO> getListCssbGs(@Param("params") Map<String, Object> map);

	List<CaseListDTO> getListSqfy(@Param("params") Map<String, Object> map);

	List<CaseListDTO> getListSqfyGs(@Param("params") Map<String, Object> map);

	List<CaseListDTO> getListSqtz(@Param("params") Map<String, Object> map);

	List<CaseListDTO> getListSqtzGs(@Param("params") Map<String, Object> map);

	@Update("UPDATE ole_ef_case ce SET ce.is_ps=#{params.isPs},ce.punish_type=#{params.punishType},ce.punish_cash=#{params.punishCash},ce.punish_bill=#{params.punishBill} WHERE ce.case_num =#{params.id}")
	void updateCaseIsps(@Param("params") CaseEntity caseEnfity);

	/**
	 * 重大案件详情
	 * 
	 * @param caseId
	 * @return
	 */
	PsCaseDTO getCaseByCaseId(@Param("caseIdMp") Map<String, Object> caseIdMp);

	/**
	 * 日常检查详情
	 * 
	 * @param caseId
	 * @return
	 */
	ApiDailyCheckDTO getDailyByCaseId(@Param("caseIdMp") Map<String, Object> caseIdMp);

	/**
	 * 专项检查详情
	 * 
	 * @param caseId
	 * @return
	 */
	LssuedDetailInfoDTO getSpecialByCaseId(@Param("caseIdMp") Map<String, Object> caseIdMp);

	/**
	 * 根据案件状态和区划统计案件数量
	 * @param status
	 * @return
	 */
	List<Map<String, Object>> getCaseCountAreaByStatus(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据案件状态和区划统计预警数量
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getWarnCountForArea(@Param("params")Map<String, Object> params);

	/**
	 * 根据区划统计日常案件数量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getCheckDailyCountForArea(@Param("params")Map<String, Object> params);

	/**
	 * 根据区划统计专项检查案件数量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getCheckCountForArea(@Param("params")Map<String, Object> params);

	/**
	 * 按部门统计日常检查数量
	 * @param params
	 * @return
	 */
	String getCheckDailyCountByDeptId(Map<String, Object> params);

	/**
	 * 按部门统计专项检查数量
	 * @param params
	 * @return
	 */
	String getCheckCountByDeptId(Map<String, Object> params);

	/**
	 * 按案件状态统计数量
	 * @param params
	 * @return
	 */
	String getCaseCountByStatus(Map<String, Object> params);
	
	/**
	 * 按流程编号统计案件数量
	 * @param params
	 * @return
	 */
	String getCaseCountByDeal(Map<String, Object> params);
	/**
	 * API本年所有案件统计
	 * 
	 * @return
	 */
	List<ApiYujDTO> yujCount(@Param("params") Map<String, Object> params);
	
	List<ApiYujDTO> yujCountMap(@Param("params") Map<String, Object> params);
	
	@Select("select count(*) from ole_ef_attach_file where case_num=#{caseNum} AND file_type='mp4'")
	int getMp4CountCountByCaseNum(@Param("caseNum")String caseNum);
	
	@Select("select count(*) from ole_ef_attach_file where case_num=#{caseNum} AND file_type='mp3'")
	int getMp3CountCountByCaseNum(@Param("caseNum")String caseNum);
	
	@Select("select count(*) from ole_ef_attach_file where case_num=#{caseNum} AND (file_type='jpg' or file_type='png')")
	int getPicCountByCaseNum(@Param("caseNum")String caseNum);

	List<PotenceDTO> getPotenceDTO(@Param("caseId")String caseId);
}
