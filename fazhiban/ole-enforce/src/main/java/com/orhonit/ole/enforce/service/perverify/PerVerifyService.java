package com.orhonit.ole.enforce.service.perverify;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.PerVerifyInfoDTO;

public interface PerVerifyService {

	/**
	 * 获取总数
	 * @param params
	 * @return
	 */
	Integer getCasecount(Map<String, Object> params);

	/**
	 * 获取列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 保存初步核实数据
	 * @param perVerifyInfoDTO
	 */
	void savePerVerify(PerVerifyInfoDTO perVerifyInfoDTO);
	
	/**
	 * 查询是否有当事人信息
	 * @param CaseNum
	 * @return
	 */
	public Boolean haveParty(String CaseNum);

}
