package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.tts.dto.CheckDailyDTO;
import com.orhonit.ole.tts.dto.CheckDocDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.dto.api.ApiDailyCheckDTO;

@Mapper
public interface CheckDailyDao {
Integer getCheckcount(@Param("params") Map<String, Object> params);
	
	List<CheckDailyDTO> checkList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Update("update ole_check_daily d set d.status = #{status} where t.id = #{id}")
	void updateStatus(@Param("id") String id, @Param("status") Integer status);
	
	CheckDailyDTO getCheckDetailInfo(@Param("checkId") String checkId, @Param("params") Map<String, Object> params);
	
	List<CheckDocDTO> findCheckDoc(String checkId);
	
	CheckDailyDTO findOne(String id);
	/**
	 * APP获取专项检查列表
	 * @return
	 */
	List<ApiCaseListDTO> findCheckDailyList(@Param("params") Map<String, Object> params);
	
	ApiDailyCheckDTO findCaseInfo(@Param("params") Map<String, Object> params);
	
	@Update("update ole_ef_check_daily t set t.status = #{checkStatus} where t.check_num = #{businessKey}")
	void updateCheckStatusByCheckNum(@Param("businessKey") String businessKey, @Param("checkStatus") String status);

	List<CheckDailyDTO> getCaseSourceCheck(@Param("deptId") String deptId, @Param("isRelate") Integer isRelate, @Param("caseAccept") Integer caseAccept);

	@Update("update ole_ef_check_daily t set t.case_accept = #{effect} where t.check_num = #{businessKey}")
	void updateCaseAcceptByCheckNum(@Param("businessKey") String businessKey, @Param("effect") Integer effect);
	
	@Update("UPDATE ole_ef_check_daily ck SET ck.is_relate ='0' WHERE id=( SELECT ce.check_id FROM ole_ef_case  ce WHERE ce.id =#{caseId})")	
	void updateOldIsRelate(String id);
	
	@Update("UPDATE ole_ef_check_daily ck SET ck.is_relate='1' WHERE  id=#{casekId}")
	void updateIsRelateCheckDaily(String casekId);
	
	Integer caseCountByStatus(@Param("status") Integer status);
	
	Integer needDealCount(@Param("id") String id);

	CheckDailyDTO getCheckByCheckId(@Param("checkId") String checkId, @Param("params") Map<String, Object> params);
}
