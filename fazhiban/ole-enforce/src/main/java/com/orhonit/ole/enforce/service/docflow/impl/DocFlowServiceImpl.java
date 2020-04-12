package com.orhonit.ole.enforce.service.docflow.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.DocFlowDao;
import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.dto.DocFlowListDTO;
import com.orhonit.ole.enforce.dto.shareapi.ApiDocFlowDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocFlowEntity;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.repository.DocFlowRepository;
import com.orhonit.ole.enforce.service.docflow.DocFlowService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Service
public class DocFlowServiceImpl implements DocFlowService{

	@Autowired
	private DocFlowDao docFlowDao;
	
	@Autowired
	private DocFlowRepository docFlowRepository;
	
	@Autowired
	private DocContentRepository docContentRepository;
	
	/**
	 * 添加文书配置
	 * @return
	 */
	@Override
	public void saveDocFlow(DocFlowEntity docFlowEntity) {
		User user = UserUtil.getCurrentUser();
		docFlowEntity.setId(UUID.randomUUID().toString());
		docFlowEntity.setCreateBy(user.getUsername());
		docFlowEntity.setCreateDate(new Date());
		docFlowEntity.setCreateName(user.getNickname());
		docFlowEntity.setUpdateBy(user.getUsername());
		docFlowEntity.setUpdateDate(new Date());
		docFlowEntity.setUpdateName(user.getNickname());
		this.docFlowRepository.save(docFlowEntity);
	}
	
	/**
	 * 修改文书配置
	 * @return
	 */
	@Override
	public void updateDocFlow(DocFlowEntity docFlowEntity) {
		this.docFlowRepository.save(docFlowEntity);
	}
	
	/**
	 * 删除文书配置
	 * @return
	 */
	@Override
	public void delDocFlow(String id) {
		this.docFlowRepository.delete(id);
	}
	
	/**
	 * 获取流程节点文书
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<DocFlowDTO> getDocFlows(Map<String, Object> paramMap) {
		List<DocFlowDTO> docFlowDTOs = docFlowDao.getDocFlows(paramMap);
		return docFlowDTOs;
	}

	/**
	 * 获取文书配置列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	@Override
	public List<DocFlowListDTO> getDocFlowList(Map<String, Object> params, Integer start, Integer length) {
		return this.docFlowDao.docFlowList(params, start, length);
	}

	/**
	 * 获取列表总数
	 * @param params
	 * @return
	 */
	@Override
	public Integer getDocFlowCount(Map<String, Object> params) {
		return this.docFlowDao.count(params);
	}
	
	/**
	 * 获取模板code,name列表
	 * @return
	 */
	@Override
	public List<Map<String,String>> getListTemplate() {
		return docFlowDao.getListTemplate();
	}
	
	/**
	 * 回显文书配置
	 * @param id
	 */
	@Override
	public DocFlowEntity findOne(String id) {
		return this.docFlowRepository.findOne(id);
	}

	
	@Override
	public List<DocContentEntity> getNeedAdd(String templateId, String caseId) {
		return this.docContentRepository.findByTemplateIdAndCaseId(templateId,caseId);
	}

	@Override
	public List<ApiDocFlowDTO> getApiDocFlows(Map<String, Object> params) {
		return this.docFlowDao.getApiDocFlows(params);
	}


}
