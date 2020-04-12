package com.orhonit.ole.enforce.service.docflow;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.dto.DocFlowListDTO;
import com.orhonit.ole.enforce.dto.shareapi.ApiDocFlowDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocFlowEntity;

public interface DocFlowService{

	/**
	 * 添加文书配置
	 * @param docFlowEntity
	 * @return
	 */
	void saveDocFlow(DocFlowEntity docFlowEntity);
	
	/**
	 * 修改文书配置
	 * @param docFlowEntity
	 * @return
	 */
	void updateDocFlow(DocFlowEntity docFlowEntity);
	
	/**
	 * 删除文书配置
	 * @param id
	 * @return
	 */
	void delDocFlow(String id);
	
	/**
	 * 获取流程节点文书
	 * @param flowNode
	 * @return
	 */
	List<DocFlowDTO> getDocFlows(Map<String, Object> paramMap);
	
	/**
	 * 获取文书配置列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<DocFlowListDTO> getDocFlowList(Map<String, Object> params, Integer start, Integer length);
	
	/**
	 * 获取总数
	 * @param params
	 * @return
	 */
	Integer getDocFlowCount(Map<String, Object> params);
	
	/**
	 * 获取模板code,name
	 * @return
	 */
	List<Map<String,String>> getListTemplate();
	
	/**
	 * 回显文书配置
	 * @param id
	 */
	DocFlowEntity findOne(String id);
	
	/**
	 * 获取文书内容
	 * @param templateId
	 * @param caseId
	 */
	List<DocContentEntity> getNeedAdd(String templateId, String caseId);

	/**
	 * 获取文书模版列表及模版内容
	 * @param params
	 * @return
	 */
	List<ApiDocFlowDTO> getApiDocFlows(Map<String, Object> params);
} 