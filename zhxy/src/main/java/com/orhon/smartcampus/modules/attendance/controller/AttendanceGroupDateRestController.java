package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupDate;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupDateService;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceGroupTimeService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤时间段数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceGroupDateRestController extends ApiController {

    @Autowired
    private IAttendanceGroupDateService service;

    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;

    /**
     * 添加考勤组时间段
     */

    @PostMapping("/attendanceGroupDates")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String result = service.insertAttendanceGroupDate(maps);
        return R.ok().put("msg", result);
    }

    /**
     * 删除考勤组时间段
     */

    @DeleteMapping("/attendanceGroupDates")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }

    /**
     * 修改考勤组时间段
     */

    @PutMapping("/attendanceGroupDates")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String result = service.updateAttendanceGroupDate(maps);
        return R.ok().put("msg", result);
    }


    /**
     * 查询考勤组时间段
     */

    @GetMapping("/attendanceGroupDates")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceGroupDate attendanceGroupDate, PageDto dto) {
        IPage<AttendanceGroupDate> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceGroupDate> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceGroupDate);
        if (paging) {
            IPage<AttendanceGroupDate> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceGroupDate> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间段
     */


    @GetMapping("/attendanceGroupDates/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceGroupDate attendanceGroupDate = service.getById(id);
        return R.ok().put("data", attendanceGroupDate);
    }

}
