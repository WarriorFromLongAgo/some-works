package com.orhonit.ole.enforce.service.lssued;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.CheckDocDTO;
import com.orhonit.ole.enforce.dto.LssuedDTO;
import com.orhonit.ole.enforce.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseListDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.sys.dto.FlowDTO;

public interface LssuedService {

	/**
	 * 保存下达通知
	 * @param caseEntity
	 * @return 
	 */
	List<FlowDTO> save(LssuedEntity lssuedEntity);

	List<LssuedEntity> getCaseList(Map<String, Object> params, Integer start, Integer length);

	Integer getCasecount(Map<String, Object> params);

	void updateCaseStatusByCaseNum(String businessKey, Integer valueOf);
	
	/**
	 * 
	 * app日常检查列表接口
	 * */
	List<ApiCaseListDTO> findLssuedList(Map<String, Object> params);
	
	/**
	 * 
	 * app日常检查详情接口
	 * */
	LssuedDetailInfoDTO findCaseInfo(Map<String, Object> params);

	/**
	 * 
	 * 根据id获取专项检查内容
	 * */
	LssuedEntity findOne(String id);

	/**
	 * 
	 * 专项检查内容-详细
	 * */
	LssuedDetailInfoDTO queryCheckByCheckId(String checkId, Map<String, Object> params);
	
	/**
	 * 专项记录保存
	 */
	void saveCheckRecord(CheckRecordEntity checkRecordEntity);
	
	/**
	 * 查询专项记录
	 * @param checkId
	 * @return
	 */
	CheckRecordEntity getRecordBycheckId(String checkId);

	List<FlowDTO> temsave(LssuedEntity lssuedEntity);
	
	List<FlowDTO> appTemsave(LssuedEntity lssuedEntity);
	
	void appSave(LssuedEntity lssuedEntity);

	/**
	 * 获取案件文书
	 * @param checkId
	 * @return
	 */
	List<CheckDocDTO> queryDocContentByCaseId(String checkId);
	
	/**
	 * 更新专项案件受理字段
	 * @param checkId
	 * @return
	 */
	LssuedEntity updateCheck(LssuedEntity lssuedEntity);

	List<LssuedDTO> getCaseSourceCheck();

	void updateCaseAcceptByCheckNum(String businessKey, String expressionText);
	
	void updateisRelateByCheckNum(String businessKey, String expressionText);

	void updateIsRelateLssued(String casekId);
	
	LssuedEntity getLssuedByCheckNum(String checkNum);

	CheckRecordEntity findRecord(String id);

	List<LssuedDTO> getCaseSourceCheck(String deptId);
	
	ApiCountDTO needDealCount(String id);
	
	ApiCountDTO caseCountByStatus(Map<String, Object> params);
	
}
