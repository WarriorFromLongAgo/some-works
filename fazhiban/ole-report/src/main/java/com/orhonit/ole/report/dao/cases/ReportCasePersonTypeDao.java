package com.orhonit.ole.report.dao.cases;

import com.orhonit.ole.report.dto.cases.CasePersonTypeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportCasePersonTypeDao {

    //处罚人员类别占比分析
    List<CasePersonTypeDTO> findCasePersonTypeCount();
}
