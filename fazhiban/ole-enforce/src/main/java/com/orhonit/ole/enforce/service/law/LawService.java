package com.orhonit.ole.enforce.service.law;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.enforce.dto.ps.PsDeptListDTO;
import com.orhonit.ole.enforce.dto.ps.PsLawDTO;


/**
 * 执法主体服务类
 * @author liuzhi
 *
 */
public interface LawService {
	/**
	 * ps按照法律类型分类统计
	 * @param 
	 * @return
	 */
	List<PsLawDTO> lawCount();
	/**
	 * ps按照法律类型查询法律名
	 * @param 
	 * @return
	 */
	List<PsLawDTO> lawByItemType(Map<String, Object> params);
	/**
	 * ps按照法律类型查询全部法律名
	 * @param 
	 * @return
	 */
	List<PsLawDTO> lawAllByItemType(Map<String, Object> params);
	/**
	 * ps按照权责类型查询法律
	 * @param 
	 * @return
	 */
	List<PsLawDTO> lawAllByProType(Map<String, Object> params);
}
