package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacher;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤教师数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceTeacherService extends BaseService<AttendanceTeacher> {

    String insertAttendanceTeacher(HashMap<String, Object> maps);

    String updateAttendanceTeacher(HashMap<String, Object> maps);

    Integer selectAttendanceGroupId(Long user_id);

    List<AttendanceTeacher> teacher(HashMap<String, Object> maps);
}
