package com.orhonit.ole.report.dao.cases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.DeptCaseCountDTO;

/**
 * 对应的mapper.xml：ReportDeptCaseCountMapper.xml
 * <p>Title: ReportCaseDeptDao</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Thinkpad
 * @date 2018年3月1日 下午5:55:31
 */
@Mapper
public interface ReportCaseDeptDao {
	
	//查询所有的执法部门列表
	List<DeptCaseCountDTO> findAllDept(@Param("areaId")String areaId);
	
	//查询各行政部门处理行政案件
	List<DeptCaseCountDTO> findDeptAndCase(@Param("year")String year,@Param("areaId")String areaId);
}
