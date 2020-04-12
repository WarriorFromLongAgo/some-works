package com.orhonit.ole.report.service.lawYear.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.LawYearDao;
import com.orhonit.ole.report.dto.LawYearDTO;
import com.orhonit.ole.report.service.lawYear.LawService;
@Service
public class LawServiceImpl implements LawService {

@Autowired
private LawYearDao lawYearDao;
	/**
	 * //新增法律法规年度统计 
	 */
	@Override
	public List<LawYearDTO> selectLaw() {
		
		return this.lawYearDao.selectLaw();
	}
	/**
	 * //新增法律法规名称
	 */
	@Override
	public List<LawYearDTO> selectLawName() {
		
		return this.lawYearDao.selectLawName();
	}

}
