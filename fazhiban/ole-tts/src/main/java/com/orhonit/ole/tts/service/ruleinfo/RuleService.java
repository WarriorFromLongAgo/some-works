package com.orhonit.ole.tts.service.ruleinfo;

import com.orhonit.ole.tts.dto.RuleDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-8
 * CreateTime : 下午12:47
 */
public interface RuleService {
    Integer getRuleCount(Map<String, Object> params);

    List<RuleDTO> getRuleList(Map<String, Object> params, Integer start, Integer length);

    void save(RuleDTO ruleDTO);

    void run(String ruleId);

    RuleDTO getRuleById(Integer roleId);

    void deleteRuleById(Integer ruleId);

	void update(RuleDTO ruleDTO);
}
