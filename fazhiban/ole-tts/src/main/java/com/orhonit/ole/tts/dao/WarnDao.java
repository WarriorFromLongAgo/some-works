package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.tts.dto.WarnDTO;

@Mapper
public interface WarnDao {

	int listCount(@Param("params")Map<String, Object> params);

	List<Map<String, Object>> list(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);
	
	int baseListCount(@Param("params")Map<String, Object> params);

	List<WarnDTO> baseList(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);
	
	WarnDTO getWarnInfo(@Param("id")String id);

	Map<String, Object> getWarnById(@Param("warnId")String warnId);

	List<Map<String, Object>> getPersonByWarnId(@Param("params")Map params);

	@Update("UPDATE ole_warn_person SET is_read = 1 WHERE person_id = #{personId} AND warn_id = #{warnId}")
	void isRead(@Param("warnId")String warnId, @Param("personId")String personId);

}
