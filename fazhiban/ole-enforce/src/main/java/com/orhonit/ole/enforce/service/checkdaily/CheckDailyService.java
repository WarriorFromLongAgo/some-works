package com.orhonit.ole.enforce.service.checkdaily;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.CheckDocDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseListDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.dto.api.ApiDailyCheckDTO;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;

public interface CheckDailyService {
	FlowDTO save(CheckDailyEntity CheckEntity);
	
	CheckDailyDTO queryCheckByCheckId(String checkId, Map<String, Object> params);
	
	Integer getCheckcount(Map<String, Object> params);
	
	List<CheckDailyDTO> getCheckList(Map<String, Object> params, Integer start, Integer length);
	
	List<CheckDocDTO> queryDocContentByCheckId(String checkId);
	
	CheckDailyDTO findOneCheckDaily(String id);
	
	CheckRecordEntity findOneCheck(String id);
	/**
	 * APP获取专项检查列表
	 * @return
	 */
	List<ApiCaseListDTO> findCheckDailyList(Map<String, Object> params);
	
	/**
	 * APP获取专项检查详情
	 * @return
	 */
	ApiDailyCheckDTO findCaseInfo(Map<String, Object> params);

	/**
	 * 根据日常检查编号更新日常检查状态
	 * @param businessKey
	 * @param valueOf
	 */
	void updateCheckStatusByCheckNum(String businessKey, String valueOf);

	CheckDailyEntity findByCheckNum(String checkNum);
	
	CheckDailyEntity findByCheckId(String checkId);

	List<CheckDailyDTO> getCaseSourceCheck();

	void updateCaseAcceptByCheckNum(String businessKey, String expressionText);

	void saveAppCheckInfo(CheckDailyEntity checkEntity, User user);

	List<CheckDailyDTO> getCaseSourceCheck(String deptId);

	void updateIsRelateCheckDaily(String casekId);
	
	ApiCountDTO needDealCount(String id);
	
	ApiCountDTO caseCountByStatus(Map<String, Object> params);

	CheckDailyEntity getPartyInfo(String checkId);
	
	String getCheckNum(User user);
}
