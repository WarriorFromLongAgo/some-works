package com.orhon.smartcampus.modules.attendance.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroup;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤组数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceGroupRestController extends ApiController {

    @Autowired
    private IAttendanceGroupService service;


    /**
     * 添加考勤组
     */

    @PostMapping("/attendanceGroups")
    public R add(@RequestBody HashMap<String, Object> maps) {
        //Todo 把传参数写入service
        AttendanceGroup attendanceGroup = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceGroup.class);
        boolean save = service.insertAttendanceGroup(attendanceGroup);
        if (save) {
            return R.ok().put("data", attendanceGroup);
        }
        return R.error().put("msg", service.getValidationData().toString());
    }


    /**
     * 删除考勤组
     */

    @DeleteMapping("/attendanceGroups")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        String result = service.deleteAttendanceGroup(maps);
        //Todo 需要加软删除
        return R.ok().put("msg", result);
    }


    /**
     * 修改考勤组
     */

    @PutMapping("/attendanceGroups")
    public R update(@RequestBody HashMap<String, Object> maps) {
        //Todo 把传参数写入service
        AttendanceGroup attendanceGroup = JSONObject.parseObject(JSONObject.toJSONString(maps), AttendanceGroup.class);
        service.updateAttendanceGroup(attendanceGroup);
        return R.ok();
    }


    /**
     * 查询考勤组全部
     */

    @GetMapping("/attendanceGroups")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceGroup attendanceGroup, PageDto dto) {
        IPage<AttendanceGroup> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceGroup);
        if (paging) {
            IPage<AttendanceGroup> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceGroup> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询考勤组单条
     */

    @GetMapping("/attendanceGroups/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceGroup attendanceGroup = service.getById(id);
        return R.ok().put("data", attendanceGroup);
    }
}
