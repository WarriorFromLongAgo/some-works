package com.orhonit.ole.report.service.lawp.Impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportLawPDao;
import com.orhonit.ole.report.dao.enforce.ReportEnforcePersonDao;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonPieDTO;
import com.orhonit.ole.report.dto.post.PostPersonnelDTO;
import com.orhonit.ole.report.service.lawp.ReportLawPersonService;

@Service
public class ReportLawPersonServiceImpl implements ReportLawPersonService {

	
	@Autowired
	private ReportLawPDao reportLawPDao;
	
	@Autowired
	private ReportEnforcePersonDao reportEnforcePersonDao;
	
	
	public List<LawPersonDTO> getLawpCount(String areaId) {
		return reportLawPDao.getLawpCount(areaId);
	}


	
	public List<LawPersonPieDTO> getLawpDCount( Map<String, Object> params) {
		return reportLawPDao.getLawpDCounnt(params);
	}



	@Override
	public List<PostPersonnelDTO> getAllAreaPostPersonnel() {
		return reportLawPDao.getAllAreaPostPersonnel();
	}



	@Override
	public List<PostPersonnelDTO> getPostPersonnel(Map<String, Object> params) {
		List<PostPersonnelDTO> list = reportLawPDao.getPostPersonnel(params);
		return list;
	}

	@Override
	public List<PostPersonnelDTO> getPostDeptCount(Map<String,Object> params){
		List<PostPersonnelDTO> list = reportLawPDao.getPostDeptCount(params);
		return list;
	}
	

	@Override
	public List<BaseChartDTO> findAreaEnforcePersonCount() {
		return reportEnforcePersonDao.findAreaEnforcePersonCount();
	}



	@Override
	public List<BaseChartDTO> findEnforcePersonAge() {
		return reportEnforcePersonDao.findEnforcePersonAge();
	}



	@Override
	public List<BaseChartDTO> findDutyPersonCount() {
		return reportEnforcePersonDao.findDutyPersonCount();
	}



	@Override
	public List<BaseChartDTO> findPersonNation() {
		return reportEnforcePersonDao.findPersonNation();
	}



	@Override
	public List<BaseChartDTO> findEduList() {
		return reportEnforcePersonDao.findEduList();
	}



	@Override
	public List<BaseChartDTO> findpoliticalList() {
		return reportEnforcePersonDao.findpoliticalList();
	}

}
