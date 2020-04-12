package com.orhonit.ole.tts.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.tts.dto.RuleDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.service.caseinfo.TaskService;
import com.orhonit.ole.tts.service.ruleinfo.RuleService;
import com.orhonit.ole.tts.service.warn.WarnInfoService;
import com.orhonit.ole.tts.service.warn.WarnPersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 日常案件预警
 * @author ebusu
 *
 */
@Component("checkDailyTask")
@Slf4j
public class CheckDailyTask {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 日常检查预警
	 * @param params
	 */
	public void checkTask(String params){
		//params格式为 规则ID|任务ID
		String ruleId = params.split("\\|")[0];
		String jobId = params.split("\\|")[1];
		//获取规则信息
		RuleDTO ruleDto  = ruleService.getRuleById(Integer.valueOf(ruleId));
		//获取组成content信息的sql
		String sql = ruleDto.getExecSql();
		List<String> cont = Arrays.asList(ruleDto.getContent().replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\{,\\}]", "").split("\\|"));
		List<Map<String,Object>> result = this.taskService.execSql(sql);
		for (Map<String, Object> item : result) {
			//生成预警信息
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("dealTime")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			//获取需要发送的执法人员列表
			List<WarnPersonEntity> warnPersonEntitys= taskService.getPersonListByCheckDailyNum(item.get("caseNum").toString(), ruleDto.getRoleId());
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setRecordCode(item.get("caseNum").toString());
				//add by liuzhih
				warnInfoEntity.setFlowType(CommonParameters.FlowType.DAILY_CHECK);
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		//更新deal表状态为已处理
		if(result != null && result.size()>0){
			String updateSQL = "UPDATE ole_ef_case_deal SET is_deal = '1' WHERE deal_mode = '" + result.get(0).get("dealMode").toString() + "' AND is_deal='0'";
			this.taskService.execSql(updateSQL);
		}
	}
}
