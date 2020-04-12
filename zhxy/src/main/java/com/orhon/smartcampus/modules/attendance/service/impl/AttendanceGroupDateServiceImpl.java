package com.orhon.smartcampus.modules.attendance.service.impl;

import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupDate;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceGroupDateMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupDateService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 考勤时间段数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceGroupDateServiceImpl extends BaseServiceImpl<AttendanceGroupDateMapper, AttendanceGroupDate> implements IAttendanceGroupDateService {

    @Autowired
    private AttendanceGroupDateMapper attendanceGroupDateMapper;
    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceGroupDate(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean id = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!id) {
            return "考勤组ID不存在，请检查";
        }
        Long user_id = infoService.getCurrentLoginUserId();
        boolean save = attendanceGroupDateMapper.insertAttendanceGroupDate(maps, user_id);
        if (save) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceGroupDate(HashMap<String, Object> maps) {
        Integer attendance_group_id = (Integer) maps.get("attendance_group_id");
        Boolean id = attendanceGroupTimeService.checkAttendanceGroupId(attendance_group_id);
        if (!id) {
            return "考勤组ID不存在，请检查";
        }
        Long user_id = infoService.getCurrentLoginUserId();
        boolean update = attendanceGroupDateMapper.updateAttendanceGroupDate(maps, user_id);
        if (update) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }
}
