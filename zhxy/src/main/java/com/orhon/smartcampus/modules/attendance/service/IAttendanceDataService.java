package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceDataService extends BaseService<AttendanceData> {

    String insertAttendanceData(HashMap<String, Object> maps);

    Boolean isExists(HashMap<String, Object> maps, Long user_id);

    Boolean addAttendanceData(HashMap<String, Object> maps, Long user_id, Integer alert);

    HashMap attendancetoday();

    ArrayList<Object> attendancenearsevenday();

    List attendancedatastatistics(HashMap<String, Object> maps);
}
