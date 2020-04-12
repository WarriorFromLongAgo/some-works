package com.orhon.smartcampus.modules.base.mapper;

import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLGrades;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 年级 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface GradesMapper extends BaseMapper<Grades> {

    GQLGrades getGradeObject(Integer grade_id);

    List<HashMap<String, Object>> getGradeList(Integer page, Integer limit, Boolean withPeriod);
}
