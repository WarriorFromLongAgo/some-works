package com.orhonit.ole.report.service.recase.Impl;

import com.orhonit.ole.report.dao.cases.ReportReCaseDao;
import com.orhonit.ole.report.dto.cases.ReCaseCountDTO;
import com.orhonit.ole.report.dto.cases.ReCaseNumCountDTO;
import com.orhonit.ole.report.service.recase.RecaseService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecaseServiceImpl implements RecaseService{

	@Autowired
	private ReportReCaseDao reporReCaseDao;


	@Override
	public List<ReCaseCountDTO> findReCaseWithDept(String areaId) {
		List<ReCaseCountDTO> lawDeptList = reporReCaseDao.findAdminLawDeptList(areaId);
		List<ReCaseCountDTO> cases = reporReCaseDao.findReCaseWithDept(areaId);
		for(int i = 0 ; i < lawDeptList.size();i++){
			String deptName = lawDeptList.get(i).getName();
			lawDeptList.get(i).setValue("0");
			for(int z = 0 ; z < cases.size();z++){
				String caseDeptName = cases.get(z).getName();
				if(deptName.equals(caseDeptName)){
					lawDeptList.get(i).setValue(cases.get(z).getValue());
				}
			}
		}
		return lawDeptList;
	}

	@Override
	public List<ReCaseNumCountDTO> findReCaseNumByYearAndStatus(String status, String year) {
		return reporReCaseDao.findReCaseNumByYearAndStatus(status,year);
	}

	@Override
	public List<ReCaseNumCountDTO> findApplyPerson(String year) {
		return reporReCaseDao.findApplyPerson(year);
	}

	@Override
	public List<ReCaseNumCountDTO> findAccCase(String year) {
		return reporReCaseDao.findAccCase(year);
	}

	@Override
	public List<ReCaseNumCountDTO> findAllAccCase(String year) {
		return reporReCaseDao.findAllAccCase(year);
	}
}
