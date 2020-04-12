package com.orhon.smartcampus.modules.course.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.course.entity.Type;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 课程级别表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

}
