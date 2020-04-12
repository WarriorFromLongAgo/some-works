 package com.orhonit.ole.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.report.dto.AreaDTO;

@Mapper
public interface ReportAreaDao {
	
	@Select("SELECT * FROM ole_base_area a where a.parent_id in ('15','17') and a.`is_effect` = '1'")
	List<AreaDTO> findArea();
	
	@Select("SELECT a.`name`,a.area FROM ole_base_area a where a.parent_id in ('15','17') and a.`is_effect` = '1'")
	List<AreaDTO> findAreaSize();

}
