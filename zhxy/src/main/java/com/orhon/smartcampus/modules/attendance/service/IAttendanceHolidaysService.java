package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.modules.attendance.entity.AttendanceHolidays;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 节假日管理 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceHolidaysService extends BaseService<AttendanceHolidays> {

    String insertAttendanceHolidaysRest(HashMap<String, Object> maps);

    String updateAttendanceHolidaysRest(HashMap<String, Object> maps);
}
