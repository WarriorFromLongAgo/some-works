package com.orhon.smartcampus.modules.teacher.mapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface OfficeArrangeUserMapper extends BaseMapper<OfficeArrangeUser> {
    List<HashMap<String, Object>> getOfficeArrangeTeacher(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getOfficeArrangeTeacherpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);
    void inserts(@Param("room_id") Integer room_id, @Param("user_id") Integer user_id);
    String selects(@Param("room_id") Integer room_id, @Param("user_id") Integer user_id);
}
