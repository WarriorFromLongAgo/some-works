package com.orhon.smartcampus.modules.attendance.service;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceWeek;

import java.util.HashMap;

/**
 * <p>
 * 考勤组周设置数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceWeekService extends BaseService<AttendanceWeek> {

    String insertAttendanceGroupWeek(HashMap<String, Object> maps);

    String updateAttendanceGroupWeek(HashMap<String, Object> maps);
}
