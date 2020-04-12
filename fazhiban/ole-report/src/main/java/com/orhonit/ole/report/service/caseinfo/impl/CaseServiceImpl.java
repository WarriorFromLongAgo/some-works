package com.orhonit.ole.report.service.caseinfo.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.CaseDao;
import com.orhonit.ole.report.dto.CaseDetailDTO;
import com.orhonit.ole.report.dto.CaseInfoDTO;
import com.orhonit.ole.report.entity.CaseEntity;
import com.orhonit.ole.report.entity.DocTemplateEntity;
import com.orhonit.ole.report.repository.CaseRepository;
import com.orhonit.ole.report.service.caseinfo.CaseService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Service
public class CaseServiceImpl implements CaseService {
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private CaseDao caseDao;

	@Override
	public void save(CaseEntity caseEntity) {
		User user = UserUtil.getCurrentUser();
		caseEntity.setCreateBy(user.getUsername());
		caseEntity.setCreateDate(new Date());
		caseEntity.setCreateName(user.getUsername());
		caseEntity.setUpdateBy(user.getUsername());
		caseEntity.setUpdateDate(new Date());
		caseEntity.setUpdateName(user.getUsername());
		caseEntity.setId(UUID.randomUUID().toString());
		// 指派人
		caseEntity.setCaseZpr(user.getId().toString());
		// 指派时间
		caseEntity.setCaseZpdate(new Date());
		this.caseRepository.save(caseEntity);
	}

	@Override
	public Page<CaseEntity> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		PageRequest request = new PageRequest(start/length ,length);
		
		Specification<CaseEntity> spec = new Specification<CaseEntity>() {

			@Override
			public Predicate toPredicate(Root<CaseEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				Predicate result = null;
				
				if ( params.get("id") != null && !"".equals(params.get("id").toString())) {
					Path<String> id = root.get("id");   
					Predicate pid = cb.equal(id, params.get("id"));
					result = cb.and(pid);
					
				}
				
				if ( params.get("caseName") != null && !"".equals(params.get("caseName").toString())) {
					Path<String> name = root.get("caseName");   
					Predicate pname = cb.like(name, "%" + params.get("caseName") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				return result;
			}
			
		};  
		
		return this.caseRepository.findAll(spec, request);
	}

	@Override
	public CaseInfoDTO findOne(String id) {
		List<CaseDetailDTO> caseDetailDTOs = this.caseDao.findCase(id);
		CaseEntity caseEntity = this.caseRepository.findOne(id);
		CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
		BeanUtils.copyProperties(caseEntity, caseInfoDTO);
		caseInfoDTO.setCaseDetailDTOs(caseDetailDTOs);
		return caseInfoDTO;
	}

}
