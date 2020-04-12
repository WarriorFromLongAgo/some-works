package com.orhonit.ole.tts.dao;

import com.orhonit.ole.tts.dto.RuleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-8
 * CreateTime : 下午12:54
 */
@Mapper
public interface RuleDao {

    Integer getRuleCount(@Param("params") Map<String, Object> params);

    List<RuleDTO> getRuleList(@Param("params") Map<String, Object> params, @Param("start") Integer start, @Param("length") Integer length);

    void save(RuleDTO RuleDTO);

    RuleDTO queryObject(@Param("ruleId") Long jobId);

    RuleDTO getRuleById(Integer roleId);

    void deleteRuleById(Integer ruleId);

	void update(RuleDTO ruleDTO);
}
