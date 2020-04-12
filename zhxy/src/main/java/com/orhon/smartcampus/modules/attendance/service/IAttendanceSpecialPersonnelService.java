package com.orhon.smartcampus.modules.attendance.service;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialPersonnel;

import java.util.HashMap;

/**
 * <p>
 * 特殊情况不需要打卡数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceSpecialPersonnelService extends BaseService<AttendanceSpecialPersonnel> {
    Boolean checkUserId(JSONArray user_id);

    String insertAttendanceSpecialPersonnel(HashMap<String, Object> maps);

    String updateAttendanceSpecialPersonnel(HashMap<String, Object> maps);
}
