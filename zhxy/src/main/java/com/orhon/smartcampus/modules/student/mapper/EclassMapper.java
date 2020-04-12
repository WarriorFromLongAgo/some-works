package com.orhon.smartcampus.modules.student.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhon.smartcampus.modules.student.entity.Eclass;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 班级表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface EclassMapper extends BaseMapper<Eclass> {

	List<Eclass> getSchoolByEclass(@Param("maps")HashMap<String, Object> maps);

}
