package com.orhonit.ole.tts.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.tts.dto.RuleDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.service.caseinfo.TaskService;
import com.orhonit.ole.tts.service.ruleinfo.RuleService;

/**
 * 基础信息预警
 * @author ebusu
 *
 */
@Component("baseTask")
public class BaseTask {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 执法人员新增预警
	 * sql语句返回结果必须包含id、deptId
	 * @param params
	 */
	public void personAddTask(String params) {
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
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("deptId").toString(), ruleDto.getRoleId());
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_person_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 执法人员修改预警
	 * sql语句返回结果必须包含caseNum、dealMode
	 * @param params
	 */
	public void personUpdateTask(String params) {
		//params格式为 规则ID|任务ID
		String ruleId = params.split("\\|")[0];
		String jobId = params.split("\\|")[1];
		//获取规则信息
		RuleDTO ruleDto  = ruleService.getRuleById(Integer.valueOf(ruleId));
		//获取组成content信息的sql
		String sql = ruleDto.getExecSql();
		//获取循环提示子串
		String regex = "(?<=\\[)(\\S+)(?=\\])";  
		Pattern pattern = Pattern.compile (regex);  
		Matcher matcher = pattern.matcher (ruleDto.getContent());
		String forStr = null;
		String forStrBack = null;
		if (matcher.find ())  
		{  
			forStr = matcher.group (1); 
			forStrBack = forStr;
		} 
		
		//获取表结构
		Map<String,Object> table = this.taskService.getTable("ole_base_dept_person_hi");
		String subStr = null;
		List<String> cont = Arrays.asList(forStr.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
		List<Map<String,Object>> result = this.taskService.execSql(sql);
		for (Map<String, Object> item : result) {
			item.put("flag", true);
			Map<String,Object> old = this.taskService.getOldPersonByPersonId(item.get("person_id").toString());
			if(old != null){
				for(Map.Entry<String, Object> entry:old.entrySet()){
					String oldk = entry.getKey();
					Object oldv = entry.getValue();
					if(item.get(oldk) != null && !oldv.equals(item.get(oldk))){
						forStr = forStrBack;
						forStr = forStr.replace("{column_comment}", table.get(oldk).toString());
						forStr = forStr.replace("{oldValue}", oldv.toString());
						forStr = forStr.replace("{newValue}", item.get(oldk).toString());
						subStr += forStr + ",";
					}
					
				}
			}
			subStr = subStr.substring(0,subStr.length()-1);
			//生成预警信息
			String content = ruleDto.getContent();
			content = content.replace("[" + forStrBack + "]", subStr);
			cont = Arrays.asList(content.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains("{"+k+"}")){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("dept_id").toString(), ruleDto.getRoleId());
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				//zfr.setPersonId(item.get("person_id").toString());TODO
				//zfr.setDeptId(item.get("dept_id").toString());
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
			
		
		//更新deal表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_person_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 执法人员删除预警
	 * sql语句返回结果必须包含caseNum、dealMode
	 * @param params
	 */
	public void personDelTask(String params) {
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
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("deptId").toString(), ruleDto.getRoleId());
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_person_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 执法主体新增预警
	 * sql语句返回结果必须包含caseNum、dealMode
	 * @param params
	 */
	public void deptAddTask(String params) {
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
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("dept_id").toString(), ruleDto.getRoleId());
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 执法主体修改预警
	 * sql语句返回结果必须包含caseNum、dealMode
	 * @param params
	 */
	public void deptUpdateTask(String params) {
		//params格式为 规则ID|任务ID
		String ruleId = params.split("\\|")[0];
		String jobId = params.split("\\|")[1];
		//获取规则信息
		RuleDTO ruleDto  = ruleService.getRuleById(Integer.valueOf(ruleId));
		//获取组成content信息的sql
		String sql = ruleDto.getExecSql();
		//获取循环提示子串
		String regex = "(?<=\\[)(\\S+)(?=\\])";  
		Pattern pattern = Pattern.compile (regex);  
		Matcher matcher = pattern.matcher (ruleDto.getContent());
		String forStr = null;
		String forStrBack = null;
		if (matcher.find ())  
		{  
			forStr = matcher.group (1); 
			forStrBack = forStr;
		} 
		
		//获取表结构
		Map<String,Object> table = this.taskService.getTable("ole_base_dept_hi");
		String subStr = "";
		List<String> cont = Arrays.asList(forStr.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
		List<Map<String,Object>> result = this.taskService.execSql(sql);
		for (Map<String, Object> item : result) {
			item.put("flag", true);
			Map<String,Object> old = this.taskService.getOldDeptByDeptId(item.get("dept_id").toString());
			if(old != null){
				for(Map.Entry<String, Object> entry:old.entrySet()){
					String oldk = entry.getKey();
					Object oldv = entry.getValue();
					if(item.get(oldk) != null && !oldv.equals(item.get(oldk))){
						forStr = forStrBack;
						forStr = forStr.replace("{column_comment}", table.get(oldk).toString());
						forStr = forStr.replace("{oldValue}", oldv.toString());
						forStr = forStr.replace("{newValue}", item.get(oldk).toString());
						subStr += forStr + ",";
					}
				}
			}
			subStr = subStr.substring(0,subStr.length()-1);
			//生成预警信息
			String content = ruleDto.getContent();
			content = content.replace("[" + forStrBack + "]", subStr);
			cont = Arrays.asList(content.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains("{"+k+"}")){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}

			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("dept_id").toString(), ruleDto.getRoleId());
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		
		//更新deal表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 执法主体删除预警
	 * sql语句返回结果必须包含caseNum、dealMode
	 * @param params
	 */
	public void deptDelTask(String params) {
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
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<WarnPersonEntity> warnPersonEntitys = taskService.getPersonListByDeptId(item.get("dept_id").toString(), ruleDto.getRoleId());
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				//add by liuzhih
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_dept_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 权责清单新增预警
	 * sql语句返回结果必须包含
	 * @param params
	 */
	public void potenceAddTask(String params) {
		//params格式为 规则ID|任务ID
		String ruleId = params.split("\\|")[0];
		String jobId = params.split("\\|")[1];
		//获取规则信息
		RuleDTO ruleDto  = ruleService.getRuleById(Integer.valueOf(ruleId));
		//获取组成content信息的sql
		String sql = ruleDto.getExecSql();
		List<String> cont = Arrays.asList(ruleDto.getContent().replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\{,\\}]", "").split("\\|"));
		List<Map<String,Object>> result = this.taskService.execSql(sql);
		List<WarnInfoEntity> warnInfoEntities = new ArrayList<>();
		for (Map<String, Object> item : result) {
			//生成预警信息
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<String> depts = taskService.getDeptByPotenceId(item.get("potence_id").toString());
			List<WarnPersonEntity> warnPersonEntitys = new ArrayList<>();
			for (String deptId : depts) {
				if(deptId != null && !"".equals(deptId)){
					warnPersonEntitys.addAll(taskService.getPersonListByDeptId(deptId, ruleDto.getRoleId()));
				}
			}
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			HashSet h = new HashSet(warnPersonEntitys);
			warnPersonEntitys.clear();
			warnPersonEntitys.addAll(h);
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				//add by liuzhih
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
			
		}
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_potence_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 权责清单修改预警
	 * sql语句返回结果必须包含
	 * @param params
	 */
	public void potenceUpdateTask(String params) {
		//params格式为 规则ID|任务ID
		String ruleId = params.split("\\|")[0];
		String jobId = params.split("\\|")[1];
		//获取规则信息
		RuleDTO ruleDto  = ruleService.getRuleById(Integer.valueOf(ruleId));
		//获取组成content信息的sql
		String sql = ruleDto.getExecSql();
		//获取循环提示子串
		String regex = "(?<=\\[)(\\S+)(?=\\])";  
		Pattern pattern = Pattern.compile (regex);  
		Matcher matcher = pattern.matcher (ruleDto.getContent());
		String forStr = null;
		String forStrBack = null;
		if (matcher.find ())  
		{  
			forStr = matcher.group (1); 
			forStrBack = forStr;
		} 
		
		//获取表结构
		Map<String,Object> table = this.taskService.getTable("ole_base_potence_hi");
		String subStr = null;
		List<String> cont = Arrays.asList(forStr.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
		List<Map<String,Object>> result = this.taskService.execSql(sql);
		List<WarnInfoEntity> warnInfoEntities = new ArrayList<>();
		for (Map<String, Object> item : result) {
			item.put("flag", true);
			Map<String,Object> old = this.taskService.getOldPotenceByPotenceId(item.get("potence_id").toString());
			if(old != null){
				for(Map.Entry<String, Object> entry:old.entrySet()){
					String oldk = entry.getKey();
					Object oldv = entry.getValue();
					if(item.get(oldk) != null && !oldv.equals(item.get(oldk))){
						forStr = forStrBack;
						forStr = forStr.replace("{column_comment}", table.get(oldk).toString());
						forStr = forStr.replace("{oldValue}", oldv.toString());
						forStr = forStr.replace("{newValue}", item.get(oldk).toString());
						subStr += forStr + ",";
					}
					
				}
			}
			subStr = subStr.substring(0,subStr.length()-1);
			//生成预警信息
			String content = ruleDto.getContent();
			content = content.replace("[" + forStrBack + "]", subStr);
			cont = Arrays.asList(content.replaceAll("[^/{\\w}&]", "|").replaceAll("\\|+", "|").replaceAll("[\\[,\\]]", "").split("\\|"));
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains("{"+k+"}")){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}

			List<String> depts = taskService.getDeptByPotenceId(item.get("potence_id").toString());
			List<WarnPersonEntity> warnPersonEntitys = new ArrayList<>();
			for (String deptId : depts) {
				if(deptId != null && !"".equals(deptId)){
					warnPersonEntitys.addAll(taskService.getPersonListByDeptId(deptId, ruleDto.getRoleId()));
				}
			}
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			HashSet h = new HashSet(warnPersonEntitys);
			warnPersonEntitys.clear();
			warnPersonEntitys.addAll(h);
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				//add by liuzhih
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		//更新deal表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_potence_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
	
	/**
	 * 权责清单删除预警
	 * sql语句返回结果必须包含
	 * @param params
	 */
	public void potenceDelTask(String params) {
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
			item.put("flag", true);
			String content = ruleDto.getContent();
			for(Map.Entry<String, Object> entry:item.entrySet()){
				String k = entry.getKey();
				Object v = entry.getValue();
			    if(cont.contains(k)){
			    	String value = null;
			    	if(k.equals("update_date")||k.equals("create_date")||k.equals("updateDate")||k.equals("createDate")){
			    		value = v.toString().substring(0, v.toString().length()-2);
			    	}else{
			    		value = v.toString();
			    	}
			    	content=content.replace("{"+k+"}", value);
			    }
			}
			List<String> depts = taskService.getDeptByPotenceId(item.get("potence_id").toString());
			List<WarnPersonEntity> warnPersonEntitys = new ArrayList<>();
			for (String deptId : depts) {
				if(deptId != null && !"".equals(deptId)){
					warnPersonEntitys.addAll(taskService.getPersonListByDeptId(deptId, ruleDto.getRoleId()));
				}
			}
			//如果没有获取到数据则结束
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			HashSet h = new HashSet(warnPersonEntitys);
			warnPersonEntitys.clear();
			warnPersonEntitys.addAll(h);
			if(warnPersonEntitys == null || warnPersonEntitys.size() == 0){
				item.put("flag", false);
				continue;
			}
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			if(warnPersonEntitys!=null&&warnPersonEntitys.size()>0){
				warnInfoEntity.setContent(content);
				warnInfoEntity.setLevel(Integer.parseInt(ruleDto.getLevel()));
				warnInfoEntity.setWarnType(ruleDto.getCaseWarnType());
				warnInfoEntity.setType(ruleDto.getType());
				//add by liuzhih
				warnInfoEntity.setStar(CommonParameters.WarnStar.ONE);
				warnInfoEntity.setTaskId(jobId);
			}
			this.taskService.saveWarnInfo(warnInfoEntity,warnPersonEntitys);
		}
		//更新人员历史表状态为已处理
		for (Map<String, Object> item : result) {
			if((Boolean)item.get("flag")){
				String updateSQL = "UPDATE ole_base_potence_hi SET is_deal = '1' WHERE id = '" + item.get("id").toString() + "' AND is_deal='0'";
				this.taskService.execSql(updateSQL);
			}
		}
	}
}
