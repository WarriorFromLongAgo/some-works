package com.orhonit.ole.report.service.lawp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonPieDTO;
import com.orhonit.ole.report.dto.post.PostPersonnelDTO;

/**
 * 执法人员服务类
 * <p>Title: ReportLawPersonService</p>
 * <p>Description: 执法人员相关查询服务接口</p>
 * <p>Company: </p> 
 * @author 田俊文
 * @date 2018年2月2日 下午3:48:06
 */
public interface ReportLawPersonService {
	
	//查询呼市和各区域执法人员数量
	List<LawPersonDTO> getLawpCount(String areaId);
	
	//查询某个执法人员主体性质数量
	List<LawPersonPieDTO> getLawpDCount(Map<String, Object> params);
	
	//查询呼市和各区域行政执法人员数量
	List<PostPersonnelDTO> getAllAreaPostPersonnel();
	
	//查询某个地区的行政执法类型的人员数量
	List<PostPersonnelDTO> getPostPersonnel( Map<String, Object> params);
	
	//查询某个地区的行政执法主体数
	List<PostPersonnelDTO> getPostDeptCount(Map<String,Object> params);
	
	//查询各区域执法人员总数
	List<BaseChartDTO> findAreaEnforcePersonCount();
	
	//查询执法人员各年龄段分布
	List<BaseChartDTO> findEnforcePersonAge();
	
	//查询各职位执法人员数量
	List<BaseChartDTO> findDutyPersonCount();
	
	//查询人员表中的各种民族数量
	List<BaseChartDTO> findPersonNation();
	
	//学历分布
	List<BaseChartDTO> findEduList();
	
	//政治面貌分布
	List<BaseChartDTO> findpoliticalList();
	
}
