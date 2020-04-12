package com.orhonit.ole.report.dao.check;

import com.orhonit.ole.report.dto.check.CheckDTO;
import com.orhonit.ole.report.dto.check.ReportMonthlyDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportOnlyCheckDao {

    //获取各部门专项检查数量
    List<CheckDTO> findOnlyCheck(@Param("areaId")String areaId);
    
    //获取各部门的检查总数（日常检查）
    List<ReportMonthlyDTO> findDayAndSpecialCount(@Param("areaId")String areaId );
    
    //获取各部门的检查总数（专项检查）
    List<ReportMonthlyDTO> findCheckAndSpecialcount(@Param("areaId")String areaId );
    
    //获取某个部门的每月的检查总量(日常检查)
    List<ReportMonthlyDTO> findDayAndMonthly(@Param("params") Map<String, Object> params);
    
    //获取某个部门的每月的检查总量（专项检查）
    List<ReportMonthlyDTO> findSpecialAndMonthly(@Param("params") Map<String, Object> params);
}
