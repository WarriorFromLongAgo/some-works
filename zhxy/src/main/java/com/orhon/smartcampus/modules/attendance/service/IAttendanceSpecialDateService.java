package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialDate;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 特殊需要上班日期数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceSpecialDateService extends BaseService<AttendanceSpecialDate> {

    String insertAttendanceSpecialDate(HashMap<String, Object> maps);

    String updateAttendanceSpecialDate(HashMap<String, Object> maps);
}
