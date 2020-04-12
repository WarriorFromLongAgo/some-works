package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 考勤教师数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceTeacherMapper extends BaseMapper<AttendanceTeacher> {

    Boolean insertAttendanceTeacher(Integer attendance_group_id, Integer user_id, Long currentUserId);

    Boolean deleteAttendanceTeacher(Integer attendance_group_id);

    AttendanceTeacher selectattendanceGroupId(Long user_id);
}
