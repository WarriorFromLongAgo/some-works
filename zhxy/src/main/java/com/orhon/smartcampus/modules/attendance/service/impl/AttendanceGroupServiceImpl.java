package com.orhon.smartcampus.modules.attendance.service.impl;

import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroup;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceGroupMapper;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>
 * 考勤组数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceGroupServiceImpl extends BaseServiceImpl<AttendanceGroupMapper, AttendanceGroup> implements IAttendanceGroupService {

    @Autowired
    private AttendanceGroupMapper attendanceGroupMapper;
    @Autowired
    private InfoService infoService;


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ResponseBody
    public Boolean insertAttendanceGroup(AttendanceGroup attendanceGroup) {
        String title = attendanceGroup.getTitle();
        Long user_id = infoService.getCurrentLoginUserId();
        Integer school_id = infoService.getCurrentUserSchoolID();
        Boolean result = attendanceGroupMapper.insertAttendanceGroup(school_id, title, user_id);
        if (result) {
            return true;
        } else {
            return true;
        }
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String deleteAttendanceGroup(HashMap<String, Object> maps) {
        Integer attdendanceGroupid = (Integer) maps.get("id");
        String selectResult = attendanceGroupMapper.selectAttendanceGroupId(attdendanceGroupid);
        if (selectResult != null) {
            return "该考勤组已设置考勤时间，无法删除";
        }
        boolean deleteResult = attendanceGroupMapper.deleteAttendanceGroup(attdendanceGroupid);
        if (deleteResult) {
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ResponseBody
    public Boolean updateAttendanceGroup(AttendanceGroup attendanceGroup) {
        Integer id = attendanceGroup.getId();
        String title = attendanceGroup.getTitle();
        Long user_id = infoService.getCurrentLoginUserId();
        Boolean result = attendanceGroupMapper.updateAttendanceGroup(id, title, user_id);
        if (result) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 查询用户考勤组
     *
     * @param maps
     * @return
     */
    @Override
    public Integer AttendanceGroup(HashMap<String, Object> maps) {
        Integer attendanceGroup = attendanceGroupMapper.attendanceGroup(maps);
        return attendanceGroup;
    }
}
