package com.orhonit.ole.enforce.service.checkQuery.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.CheckQueryDao;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.service.checkQuery.CheckQueryService;
import com.orhonit.ole.sys.service.SysDeptService;

@Service
public class CheckQueryServiceImpl implements CheckQueryService{
	
	
	@Autowired
	private CheckQueryDao checkQueryDao;
	
	@Autowired
	private SysDeptService sysDeptService;

	@Override
	public Integer getCasecount(Map<String, Object> params) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.checkQueryDao.count(params);
	}
	
	@Autowired
	public Integer getCount(Map<String, Object> params) {
		return this.checkQueryDao.getCount(params);
	}

	@Override
	public List<LssuedEntity> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.checkQueryDao.list(params, start, length);
	}

	@Override
	public List<LssuedEntity> getCaseListCopy(Map<String, Object> params, Integer start, Integer length) {
		return this.checkQueryDao.listCopy(params, start, length);
	}
	
	@Override
	public String getCheckZfryIdByCheckNum(String checkNum) {
		
		return this.checkQueryDao.getCheckZfryIdByCheckNum(checkNum);
	}


}
