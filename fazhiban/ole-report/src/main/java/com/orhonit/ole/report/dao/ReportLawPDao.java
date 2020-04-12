package com.orhonit.ole.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.laswp.LawPersonDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonPieDTO;
import com.orhonit.ole.report.dto.post.PostPersonnelDTO;

@Mapper
public interface ReportLawPDao {
	
	//查询呼市和各区域执法人员数量
	List<LawPersonDTO> getLawpCount(@Param("areaId") String areaId);
	
	//查询某个执法人员主体性质数量
	List<LawPersonPieDTO> getLawpDCounnt(@Param("params") Map<String, Object> params);
	
	//查询呼市和各区域行政执法人员数量
	List<PostPersonnelDTO> getAllAreaPostPersonnel();
	
	//查询某个地区的行政执法人员数量
	List<PostPersonnelDTO> getPostPersonnel(@Param("params") Map<String, Object> params);
	
	//查询某个地区的行政执法主题数
	List<PostPersonnelDTO> getPostDeptCount(@Param("params") Map<String, Object> params);
}
