package com.orhonit.ole.enforce.service.law.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.DeptDao;
import com.orhonit.ole.enforce.dao.LawDao;
import com.orhonit.ole.enforce.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.enforce.dto.ps.PsDeptListDTO;
import com.orhonit.ole.enforce.dto.ps.PsLawDTO;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.enforce.service.law.LawService;

/**
 * 执法主体服务类
 * @author liuzhi
 */
@Service
public class LawServiceImpl  implements LawService{
	
	@Autowired
	private LawDao lawDao;

	@Override
	public List<PsLawDTO> lawCount() {
		return this.lawDao.lawCount();
	}
	@Override
	public List<PsLawDTO> lawByItemType(Map<String, Object> params) {
		return this.lawDao.lawByItemType(params);
	}
	@Override
	public List<PsLawDTO> lawAllByItemType(Map<String, Object> params) {
		return this.lawDao.lawAllByItemType(params);
	}
	@Override
	public List<PsLawDTO> lawAllByProType(Map<String, Object> params) {
		return this.lawDao.lawAllByProType(params);
	}
}
