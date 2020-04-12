package com.orhon.smartcampus.modules.attendance.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceNoNeedAttendancePersonnel;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceNoNeedAttendancePersonnelService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 无需打卡人员 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceNoNeedAttendancePersonnelRestController extends ApiController {

    @Autowired
    private IAttendanceNoNeedAttendancePersonnelService service;

    /**
     * 添加无需考勤人员
     */


    @PostMapping("/attendanceNoNeedAttendancePersonnels")
    public R add(@RequestBody HashMap<String, Object> maps) {
        AttendanceNoNeedAttendancePersonnel attendanceNoNeedAttendancePersonnel = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceNoNeedAttendancePersonnel.class);
        boolean save = service.save(attendanceNoNeedAttendancePersonnel);
        if (save) {
            return R.ok().put("data", attendanceNoNeedAttendancePersonnel);
        }
        return R.error().put("msg", service.getValidationData().toString());
        //Todo 需要获取用户数据，添加数据库
    }

    /**
     * 删除无需考勤人员
     */

    @DeleteMapping("/attendanceNoNeedAttendancePersonnels")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 去考勤时间中查询时候有子集，需要加软删除
        return R.ok();
    }


    /**
     * 修改无需考勤人员
     */

    @PutMapping("/attendanceNoNeedAttendancePersonnels")
    public R update(@RequestBody HashMap<String, Object> maps) {
        AttendanceNoNeedAttendancePersonnel attendanceNoNeedAttendancePersonnel = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceNoNeedAttendancePersonnel.class);
        //Todo 获取用户ID填充 update_by
        service.updateById(attendanceNoNeedAttendancePersonnel);
        return R.ok();
    }

    /**
     * 查询无需考勤人员
     */

    @GetMapping("/attendanceNoNeedAttendancePersonnels")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceNoNeedAttendancePersonnel attendanceNoNeedAttendancePersonnel, PageDto dto) {
        IPage<AttendanceNoNeedAttendancePersonnel> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceNoNeedAttendancePersonnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceNoNeedAttendancePersonnel);
        if (paging) {
            IPage<AttendanceNoNeedAttendancePersonnel> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceNoNeedAttendancePersonnel> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }
}
