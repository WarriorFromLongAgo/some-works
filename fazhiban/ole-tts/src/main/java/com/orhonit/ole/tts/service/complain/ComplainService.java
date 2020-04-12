package com.orhonit.ole.tts.service.complain;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.ComplainDTO;

public interface ComplainService {

	/**
	 * 根据条件获取投诉的列表
	 * @param caseId
	 * @return
	 */
	List<ComplainDTO> getList(Map<String,Object> params,Integer start,Integer length);

	/**
	 * 根据条件获取投诉总数
	 * @param flowDTO
	 * @param isStart
	 */
	int getListCount(Map<String,Object> params);
	
	/**
	 * 根据ID获取投诉详情
	 * @param id
	 * @return
	 */
	ComplainDTO getComplain(Integer id);
}
