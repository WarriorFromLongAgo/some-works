package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacherLeave;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 教师请假数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceTeacherLeaveService extends BaseService<AttendanceTeacherLeave> {

    String insertAttendanceTeacherLeaveService(HashMap<String, Object> maps);

    String updateAttendanceTeacherLeaveService(HashMap<String, Object> maps);
}
