package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialPersonnel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 特殊情况不需要打卡数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceSpecialPersonnelMapper extends BaseMapper<AttendanceSpecialPersonnel> {

    AttendanceSpecialPersonnel checkUserId(Integer user_id);

    boolean insertAttendanceSpecialPersonnel(@Param("maps") HashMap<String, Object> maps, Integer user_id, Long created_by);

    boolean updateAttendanceSpecialPersonnel(@Param("maps") HashMap<String, Object> maps, Long currentUserId);
}
