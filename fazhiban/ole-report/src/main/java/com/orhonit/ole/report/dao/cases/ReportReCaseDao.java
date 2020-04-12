package com.orhonit.ole.report.dao.cases;

import com.orhonit.ole.report.dto.cases.ReCaseCountDTO;
import com.orhonit.ole.report.dto.cases.ReCaseNumCountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 复议案件统计Dao
 */
@Mapper
public interface ReportReCaseDao {

    //查询行政执法单位列表
    List<ReCaseCountDTO> findAdminLawDeptList(@Param("areaId")String areaId);

	//查询各区域复议案件数量
    List<ReCaseCountDTO> findReCaseWithDept(@Param("areaId")String areaId);

    //复议案件量分析--查询某年复议案件某个状态的数量
    List<ReCaseNumCountDTO> findReCaseNumByYearAndStatus(@Param("status")String status,@Param("year")String year);

    //复议申请人类别占比分析
    List<ReCaseNumCountDTO> findApplyPerson(@Param("year")String year);

    //受理案件占比
    List<ReCaseNumCountDTO> findAccCase(@Param("year")String year);

    //所有受理案件占比
    List<ReCaseNumCountDTO> findAllAccCase(@Param("year")String year);
}
