package com.orhon.smartcampus.modules.attendance.service.impl;

import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupTime;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceGroupTimeMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 考勤组时间数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceGroupTimeServiceImpl extends BaseServiceImpl<AttendanceGroupTimeMapper, AttendanceGroupTime> implements IAttendanceGroupTimeService {

    @Autowired
    private AttendanceGroupTimeMapper attendanceGroupTimeMapper;
    @Autowired
    private InfoService infoService;

    @Override
    public Boolean checkAttendanceGroupId(Integer attendance_group_id) {
        AttendanceGroupTime result = attendanceGroupTimeMapper.checkAttendanceGroupId(attendance_group_id);
        if (result == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceGroupTime(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean id = this.checkAttendanceGroupId(attendance_group_id);
        if (!id) {
            return "考勤组ID不存在，请检查";
        }
        //Todo 需要再验证（插入的字段）
        Long user_id = infoService.getCurrentLoginUserId();
        Integer school_id = infoService.getCurrentUserSchoolID();
        HashMap semester_id = infoService.getCurruentUsresSemster();
        boolean save = attendanceGroupTimeMapper.insertAttendanceGroupTime(school_id, (Integer) semester_id.get("id"), maps, user_id);
        if (save) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceGroupTime(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean id = this.checkAttendanceGroupId(attendance_group_id);
        if (!id) {
            return "考勤组ID不存在，请检查";
        }
        //Todo 需要再验证（插入的字段）
        Long user_id = infoService.getCurrentLoginUserId();
        boolean update = attendanceGroupTimeMapper.updateAttendanceGroupTime(maps, user_id);
        if (update) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    /**
     * 查找用户考勤时间段ID
     *
     * @param maps
     * @param attendanceGroupId
     * @return
     */
    @Override
    public AttendanceGroupTime selectAttendanceGroupTimeId(HashMap<String, Object> maps, Integer attendanceGroupId) {
        String auth_time = (String) maps.get("auth_time");
        String authTime = auth_time.substring(auth_time.length() - 8, auth_time.length());
        AttendanceGroupTime attendanceGroupTime = attendanceGroupTimeMapper.selectAttendanceGroupTimeId(attendanceGroupId, authTime);
        return attendanceGroupTime;
    }
}