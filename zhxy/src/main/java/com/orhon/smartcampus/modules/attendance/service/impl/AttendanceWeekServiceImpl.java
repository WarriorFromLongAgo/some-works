package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceWeek;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceWeekMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceWeekService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 考勤组周设置数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceWeekServiceImpl extends BaseServiceImpl<AttendanceWeekMapper, AttendanceWeek> implements IAttendanceWeekService {

    @Autowired
    private AttendanceWeekMapper attendanceWeekMapper;
    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceGroupWeek(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean result = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!result) {
            return "考勤组ID不存在，请检查";
        }
        Long user_id = infoService.getCurrentLoginUserId();
        JSONArray weeks = (JSONArray) maps.get("week");
        for (Object week : weeks) {
            Boolean save = attendanceWeekMapper.insertAttendanceGroupWeek(attendance_group_id, (Integer) week, user_id);
            if (!save) {
                return "添加失败";
            }
        }
        return "添加成功";
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceGroupWeek(HashMap<String, Object> maps) {
        Boolean delelte = attendanceWeekMapper.deleteAttendanceGroupWeek(maps);
        if (!delelte) {
            return "修改失败";
        }
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean result = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!result) {
            return "考勤组ID不存在，请检查";
        }
        Long user_id = infoService.getCurrentLoginUserId();
        JSONArray weeks = (JSONArray) maps.get("week");
        for (Object week : weeks) {
            Boolean save = attendanceWeekMapper.insertAttendanceGroupWeek(attendance_group_id, (Integer) week, user_id);
            if (!save) {
                return "修改失败";
            }
        }
        return "修改成功";
    }
}
