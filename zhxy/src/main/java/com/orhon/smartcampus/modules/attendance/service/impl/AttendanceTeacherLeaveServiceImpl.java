package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacherLeave;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceTeacherLeaveMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceTeacherLeaveService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 教师请假数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceTeacherLeaveServiceImpl extends BaseServiceImpl<AttendanceTeacherLeaveMapper, AttendanceTeacherLeave> implements IAttendanceTeacherLeaveService {

    @Autowired
    private IAttendanceTeacherLeaveService attendanceTeacherLeaveService;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceTeacherLeaveService(HashMap<String, Object> maps) {
        AttendanceTeacherLeave attendanceTeacherLeave = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceTeacherLeave.class);
        HashMap semester_id = infoService.getCurruentUsresSemster();
        Integer school_id = infoService.getCurrentUserSchoolID();
        Long currentUserId = infoService.getCurrentLoginUserId();
        attendanceTeacherLeave.setSemester_id((Integer) semester_id.get("id"));
        attendanceTeacherLeave.setSchool_id(school_id);
        attendanceTeacherLeave.setCreated_by(currentUserId);
        Boolean save = attendanceTeacherLeaveService.save(attendanceTeacherLeave);
        if (!save) {
            return "添加失败";
        } else {
            return "添加成功";
        }
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceTeacherLeaveService(HashMap<String, Object> maps) {
        AttendanceTeacherLeave attendanceTeacherLeave = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceTeacherLeave.class);
        Long currentUserId = infoService.getCurrentLoginUserId();
        attendanceTeacherLeave.setUpdated_by(currentUserId);
        Boolean save = attendanceTeacherLeaveService.updateById(attendanceTeacherLeave);
        if (!save) {
            return "修改失败";
        } else {
            return "修改成功";
        }
    }
}
