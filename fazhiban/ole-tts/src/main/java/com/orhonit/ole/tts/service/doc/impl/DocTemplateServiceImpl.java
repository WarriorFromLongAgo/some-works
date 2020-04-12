package com.orhonit.ole.tts.service.doc.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.orhonit.ole.common.constants.SystemConstants;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.entity.DocTemplateEntity;
import com.orhonit.ole.tts.repository.DocTemplateRepository;
import com.orhonit.ole.tts.service.doc.DocTemplateService;

@Service
public class DocTemplateServiceImpl implements DocTemplateService {

	@Autowired
	private DocTemplateRepository docTemplateRepository;
	
	@Override
	public void saveDocTemplate(DocTemplateEntity docTemplateEntity) {
		User user = UserUtil.getCurrentUser();
		docTemplateEntity.setId(UUID.randomUUID().toString());
		docTemplateEntity.setCreateBy(user.getUsername());
		docTemplateEntity.setCreateDate(new Date());
		docTemplateEntity.setCreateName(user.getNickname());
		docTemplateEntity.setIsEffect(SystemConstants.USER_STATUS_NORMAL.getCode());
		docTemplateEntity.setUpdateBy(user.getUsername());
		docTemplateEntity.setUpdateDate(new Date());
		docTemplateEntity.setUpdateName(user.getNickname());
		this.docTemplateRepository.save(docTemplateEntity);
	}

	@Override
	public DocTemplateEntity findOne(String id) {
		return this.docTemplateRepository.findOne(id);
	}

	@Override
	public int getDocTemplateCount(Map<String, Object> params) {
		return Long.valueOf(this.docTemplateRepository.count()).intValue();
	}

	@Override
	public Page<DocTemplateEntity> getDocTemplateList(Map<String, Object> params, Integer start, Integer length) {
		
		PageRequest request = new PageRequest(start/length ,length);
		
		Specification<DocTemplateEntity> spec = new Specification<DocTemplateEntity>() {

			@Override
			public Predicate toPredicate(Root<DocTemplateEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				Predicate result = null;
				
				if ( params.get("id") != null && !"".equals(params.get("id").toString())) {
					Path<String> id = root.get("id");   
					Predicate pid = cb.equal(id, params.get("id"));
					result = cb.and(pid);
					
				}
				
				if ( params.get("name") != null && !"".equals(params.get("name").toString())) {
					Path<String> name = root.get("name");   
					Predicate pname = cb.like(name, "%" + params.get("name") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				if ( params.get("code") != null && !"".equals(params.get("code").toString())) {
					Path<String> code = root.get("code");   
					Predicate pname = cb.like(code, "%" + params.get("code") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				return result;
			}
			
		};  
		
		return this.docTemplateRepository.findAll(spec, request);
	}

	@Override
	public void updateDocTemplate(DocTemplateEntity docTemplateEntity) {
		this.docTemplateRepository.save(docTemplateEntity);
	}

	@Override
	public DocTemplateEntity getTemplateByTemplateCode(String tempCode) {
		return this.docTemplateRepository.findByCode(tempCode);
	}
	
	@Override
	public Map<String, String> getTemplateCode(){
		List<DocTemplateEntity> list = this.docTemplateRepository.findAll();
		Map<String,String> map = new HashMap<String, String>();
		for (DocTemplateEntity docTemplateEntity : list) {
			map.put(docTemplateEntity.getCode(), docTemplateEntity.getId());
		}
		return map;
	}
}
