package com.orhonit.ole.report.dao.power;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.power.PowerAndRespDTO;

@Mapper
public interface PowerDao {
	
	List<PowerAndRespDTO> getCount();
	
	List<PowerAndRespDTO> getClassFica();
	
	List<PowerAndRespDTO> getDepartCount(@Param("areaId")String areaId);
	
	//查询各个部门的日常检查数量
	List<PowerAndRespDTO> getDayInspection(@Param("areaId")String areaId);
}
