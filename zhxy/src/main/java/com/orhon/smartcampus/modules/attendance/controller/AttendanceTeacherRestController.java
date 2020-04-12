package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacher;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceTeacherService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤教师数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceTeacherRestController extends ApiController {

    @Autowired
    private IAttendanceTeacherService service;

    /**
     * 添加考勤组教师
     */

    @PostMapping("/teachers")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = service.insertAttendanceTeacher(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除考勤组教师
     */

    @DeleteMapping("/teachers")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }

    /**
     * 修改考勤组教师
     */

    @PutMapping("/teachers")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String save = service.updateAttendanceTeacher(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 查询考勤组教师
     */

    @GetMapping("/teachers")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceTeacher attendanceTeacher, PageDto dto) {
        IPage<AttendanceTeacher> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceTeacher);
        if (paging) {
            IPage<AttendanceTeacher> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceTeacher> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间
     */

    @GetMapping("/teachers/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceTeacher attendanceTeacher = service.getById(id);
        return R.ok().put("data", attendanceTeacher);
    }
}
