package com.orhon.smartcampus.modules.course.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.course.entity.Courses;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 课程的数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface CoursesMapper extends BaseMapper<Courses> {

}
