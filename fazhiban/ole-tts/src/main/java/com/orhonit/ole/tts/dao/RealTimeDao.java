package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptDTO;

@Mapper
public interface RealTimeDao {
	
	List<CaseListDTO> caseList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	Integer caseCount(@Param("params") Map<String, Object> params);

	String execFunction(@Param("functionName") String functionName, @Param("deptId") String deptId);
	
	@Select("SELECT * FROM ole_base_dept WHERE FIND_IN_SET(id,getBaseDept(#{id})) and if_effect='1' and del_flag ='0'")
	List<DeptDTO> deptTreeByDeptId(String id);
	
	@Select("SELECT * FROM ole_base_dept WHERE area_id = #{id} and if_effect='1' and del_flag ='0'")
	List<DeptDTO> deptTreeByAreaId(String id);
	
	@Select("SELECT * FROM ole_base_dept where if_effect='1' and del_flag ='0'")
	List<DeptDTO> deptTreeAll();

	/**
	 * 获取案件非常详细的信息
	 * @param caseId
	 * @return
	 */
	CaseDetailInfoDTO getCaseDetailInfo(@Param("caseId") String caseId, @Param("params") Map<String, Object> params);

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	List<CaseDocDTO> findCaseDoc(String caseId);

}
