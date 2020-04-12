package com.orhon.smartcampus.modules.document.mapper;

import com.alibaba.fastjson.JSON;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.document.entity.Teacher;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
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
public interface TeacherMapper extends BaseMapper<Teacher> {

    void inserts(Integer department_id, Object user_id, Integer from_user_id, Integer document_id, JSON content);
    List<HashMap<String, Object>> getDocument(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDocumentpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    List<Map<String,Object>> gqlTeachersQuery(@Param("page") Integer page ,
                                           @Param("limit") Integer limit,
                                           @Param("withSchool") Boolean withSchool
    );
}
