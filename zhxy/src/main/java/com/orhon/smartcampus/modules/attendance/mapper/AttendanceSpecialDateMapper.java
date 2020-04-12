package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 特殊需要上班日期数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceSpecialDateMapper extends BaseMapper<AttendanceSpecialDate> {

    boolean insertAttendanceSpecialDate(@Param("maps") HashMap<String, Object> maps, Long user_id);

    boolean updateAttendanceSpecialDate(@Param("maps") HashMap<String, Object> maps, Long user_id);
}
