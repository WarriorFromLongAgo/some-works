package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceWeek;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceWeekService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤组周设置数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceWeekRestController extends ApiController {

    @Autowired
    private IAttendanceWeekService service;

    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;


    /**
     * 添加考勤组考勤周
     */

    @PostMapping("/weeks")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = service.insertAttendanceGroupWeek(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除考勤组考勤周
     */

    @DeleteMapping("/weeks")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }

    /**
     * 修改考勤组考勤周
     */

    @PutMapping("/weeks")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String save = service.updateAttendanceGroupWeek(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 查询考勤组考勤周
     */

    @GetMapping("/weeks")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceWeek attendanceWeek, PageDto dto) {
        IPage<AttendanceWeek> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceWeek> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceWeek);
        if (paging) {
            IPage<AttendanceWeek> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceWeek> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间
     */

    @GetMapping("/weeks/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceWeek attendanceWeek = service.getById(id);
        return R.ok().put("data", attendanceWeek);
    }

    /**
     * <p>
     * 教师请假数据表 前端控制器
     * </p>
     *
     * @author Orhon
     */

    @RestController
    @RequestMapping(value = "/teacher_leave", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public static class AttendanceTeacherLeaveRestController extends ApiController {

    }
}
