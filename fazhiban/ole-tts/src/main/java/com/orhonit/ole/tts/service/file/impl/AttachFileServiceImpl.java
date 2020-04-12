package com.orhonit.ole.tts.service.file.impl;

import java.util.Map;

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

import com.orhonit.ole.tts.entity.AttachFileEntity;
import com.orhonit.ole.tts.repository.AttachFileRepository;
import com.orhonit.ole.tts.service.file.AttachFileService;

@Service
public class AttachFileServiceImpl implements AttachFileService {

	@Autowired
	private AttachFileRepository attachFileRepository;
	

	@Override
	public int getFileCount(Map<String, Object> params) {
		return Long.valueOf(this.attachFileRepository.count()).intValue();
	}

	@Override
	public Page<AttachFileEntity> getFileList(Map<String, Object> params, Integer start, Integer length) {
		
		PageRequest request = new PageRequest(start/length ,length);
		
		Specification<AttachFileEntity> spec = new Specification<AttachFileEntity>() {

			@Override
			public Predicate toPredicate(Root<AttachFileEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				Predicate result = null;
				
				if ( params.get("id") != null && !"".equals(params.get("id").toString())) {
					Path<String> id = root.get("id");   
					Predicate pid = cb.equal(id, params.get("id"));
					result = cb.and(pid);
				}
				
				if ( params.get("fileName") != null && !"".equals(params.get("fileName").toString())) {
					Path<String> name = root.get("fileName");   
					Predicate pname = cb.like(name, "%" + params.get("fileName") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				if ( params.get("caseId") != null && !"".equals(params.get("caseId").toString())) {
					Path<String> name = root.get("caseId");   
					Predicate pname = cb.equal(name, params.get("caseId"));
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				
				if ( params.get("caseStatus") != null && !"".equals(params.get("caseStatus").toString())) {
					Path<String> name = root.get("caseStatus");   
					Predicate pname = cb.equal(name, params.get("caseStatus"));
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				return result;
			}
		};  
		return this.attachFileRepository.findAll(spec, request);
	}
}
