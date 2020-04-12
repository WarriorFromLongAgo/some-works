package com.orhon.smartcampus.modules.document.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.document.entity.Student;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    List<Map<String,Object>> getStudents(@Param("page") Integer page , @Param("limit") Integer limit);
}
