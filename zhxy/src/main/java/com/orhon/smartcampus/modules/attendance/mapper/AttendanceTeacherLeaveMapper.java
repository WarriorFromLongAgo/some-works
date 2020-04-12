package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacherLeave;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 教师请假数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceTeacherLeaveMapper extends BaseMapper<AttendanceTeacherLeave> {
}
