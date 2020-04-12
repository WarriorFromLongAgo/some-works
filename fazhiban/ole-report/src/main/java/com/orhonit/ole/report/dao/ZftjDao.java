package com.orhonit.ole.report.dao;

import com.orhonit.ole.report.dto.ReportIndex;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZftjDao {



	int count(@Param("params") Map<String, Object> params);
	//统计执法主体数量
	@Select("select count(DISTINCT id) from ole_base_dept where del_flag = '0' and if_effect = '1'")
	int countzfzt();
	//统计执法人员数量
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_dept_person where del_flag = '0' and if_effect = '1'")
	int countzfry();
	//统计权责数量
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_potence where del_flag = '0' and is_effect = '1'")
	int countqzsl();
	//统计法律条数
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_law_category where del_flag = '0' and is_effect = '1'")
	int countflts();


	//执法主体分布情况------报表
	@Select("SELECT a.name , COUNT(b.id) AS cnumber FROM  ole_base_area a LEFT JOIN  ole_base_dept b ON  a.id = b.area_id WHERE b.del_flag = '0'  and b.if_effect = '1' and a.parent_id <> 0   GROUP BY a.name")
	List<ReportIndex> getZfzt();
	//执法人员分布情况------报表
	@Select("SELECT a.name ,COUNT(c.dept_id) AS cnumber FROM ole_base_area a LEFT JOIN ole_base_dept b ON a.id = b.area_id LEFT JOIN ole_base_dept_person c  ON b.id = c.dept_id WHERE  c.del_flag='0'  and c.if_effect='1' and  a.parent_id <> 0   GROUP BY a.name")
	List<ReportIndex> getZfry();





}
