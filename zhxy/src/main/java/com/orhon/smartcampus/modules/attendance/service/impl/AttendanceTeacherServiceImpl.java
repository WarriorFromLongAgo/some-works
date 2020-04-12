package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacher;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceTeacherMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceSpecialPersonnelService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceTeacherService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤教师数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceTeacherServiceImpl extends BaseServiceImpl<AttendanceTeacherMapper, AttendanceTeacher> implements IAttendanceTeacherService {

    @Autowired
    private AttendanceTeacherMapper attendanceTeacherMapper;
    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;
    @Autowired
    private IAttendanceSpecialPersonnelService attendanceSpecialPersonnelService;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceTeacher(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean result = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!result) {
            return "考勤组ID不存在，请检查";
        }

        JSONArray users_id = (JSONArray) maps.get("user_id");
        Boolean res = attendanceSpecialPersonnelService.checkUserId(users_id);
        if (!res) {
            return "用户ID不存在，请检查";
        }
        Long currentUserId = infoService.getCurrentLoginUserId();
        for (Object user_id : users_id) {
            Boolean save = attendanceTeacherMapper.insertAttendanceTeacher(attendance_group_id, (Integer) user_id, currentUserId);
            if (!save) {
                return "添加失败";
            }
        }
        return "添加成功";
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceTeacher(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean delete = attendanceTeacherMapper.deleteAttendanceTeacher(attendance_group_id);
        if (!delete) {
            return "修改失败";
        }
        Boolean result = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!result) {
            return "考勤组ID不存在，请检查";
        }
        JSONArray users_id = (JSONArray) maps.get("user_id");
        Boolean res = attendanceSpecialPersonnelService.checkUserId(users_id);
        if (!res) {
            return "用户ID不存在，请检查";
        }
        Long currentUserId = infoService.getCurrentLoginUserId();
        for (Object user_id : users_id) {
            Boolean save = attendanceTeacherMapper.insertAttendanceTeacher(attendance_group_id, (Integer) user_id, currentUserId);
            if (!save) {
                return "修改失败";
            }
        }
        return "修改成功";
    }

    /**
     * 返回用户考勤组id
     *
     * @param user_id
     * @return 用户考勤组ID
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer selectAttendanceGroupId(Long user_id) {
        AttendanceTeacher attendanceTeacher = attendanceTeacherMapper.selectattendanceGroupId(user_id);
        return attendanceTeacher.getAttendance_group_id();
    }

    /**
     * 传入部门返回教师
     *
     * @param maps
     * @return
     */
    @Override
    public List<AttendanceTeacher> teacher(HashMap<String, Object> maps) {
        QueryWrapper<AttendanceTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", maps.get("department_id"));
        List<AttendanceTeacher> teacher = this.list(queryWrapper);
        return teacher;
    }
}
