package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialPersonnel;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceSpecialPersonnelMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceSpecialPersonnelService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>
 * 特殊情况不需要打卡数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceSpecialPersonnelServiceImpl extends BaseServiceImpl<AttendanceSpecialPersonnelMapper, AttendanceSpecialPersonnel> implements IAttendanceSpecialPersonnelService {


    @Autowired
    private AttendanceSpecialPersonnelMapper attendanceSpecialPersonnelMapper;

    @Autowired
    private InfoService infoService;

    @Override
    public Boolean checkUserId(JSONArray usersid) {
        for (Object userid : usersid) {
            AttendanceSpecialPersonnel result = attendanceSpecialPersonnelMapper.checkUserId((Integer) userid);
            if (result == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    @ResponseBody
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceSpecialPersonnel(HashMap<String, Object> maps) {
        JSONArray users_id = (JSONArray) maps.get("user_id");
        Boolean result = this.checkUserId(users_id);
        if (!result) {
            return "用户ID不存在，请检查";
        }
        Long currentUserId = infoService.getCurrentLoginUserId();
        for (Object user_id : users_id) {
            boolean save = attendanceSpecialPersonnelMapper.insertAttendanceSpecialPersonnel(maps, (Integer) user_id, currentUserId);
            if (!save) {
                return "保存失败";
            }
        }
        return "保存成功";
    }

    @Override
    @ResponseBody
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateAttendanceSpecialPersonnel(HashMap<String, Object> maps) {
        JSONArray users_id = (JSONArray) maps.get("user_id");
        Boolean result = this.checkUserId(users_id);
        if (!result) {
            return "用户ID不存在，请检查";
        }
        Long currentUserId = infoService.getCurrentLoginUserId();
        boolean update = attendanceSpecialPersonnelMapper.updateAttendanceSpecialPersonnel(maps, currentUserId);
        if (update) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }
}