package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialDate;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceSpecialDateMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceSpecialDateService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 特殊需要上班日期数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceSpecialDateServiceImpl extends BaseServiceImpl<AttendanceSpecialDateMapper, AttendanceSpecialDate> implements IAttendanceSpecialDateService {

    @Autowired
    private AttendanceSpecialDateMapper attendanceSpecialDateMapper;
    @Autowired
    private InfoService infoService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceSpecialDate(HashMap<String, Object> maps) {
        Long user_id = infoService.getCurrentLoginUserId();
        boolean save = attendanceSpecialDateMapper.insertAttendanceSpecialDate(maps, user_id);
        if (save) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceSpecialDate(HashMap<String, Object> maps) {
        Long user_id = infoService.getCurrentLoginUserId();
        boolean save = attendanceSpecialDateMapper.updateAttendanceSpecialDate(maps, user_id);
        if (save) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }
}
