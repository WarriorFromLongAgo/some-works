package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceHolidays;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceHolidaysMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceHolidaysService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 节假日管理 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceHolidaysServiceImpl extends BaseServiceImpl<AttendanceHolidaysMapper, AttendanceHolidays> implements IAttendanceHolidaysService {

    @Autowired
    private IAttendanceHolidaysService attendanceHolidaysService;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceHolidaysRest(HashMap<String, Object> maps) {
        AttendanceHolidays attendanceHolidays = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceHolidays.class);
        Long user_id = infoService.getCurrentLoginUserId();
        attendanceHolidays.setCreated_by(user_id);
        Boolean save = attendanceHolidaysService.save(attendanceHolidays);
        if (!save) {
            return "添加失败";
        } else {
            return "添加成功";
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceHolidaysRest(HashMap<String, Object> maps) {
        AttendanceHolidays attendanceHolidays = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceHolidays.class);
        Long user_id = infoService.getCurrentLoginUserId();
        attendanceHolidays.setUpdated_by(user_id);
        Boolean save = attendanceHolidaysService.updateById(attendanceHolidays);
        if (!save) {
            return "修改失败";
        } else {
            return "修改成功";
        }
    }
}
