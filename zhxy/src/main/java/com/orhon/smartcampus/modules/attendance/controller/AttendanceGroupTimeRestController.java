package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroup;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupTime;
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
 * 考勤组时间数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceGroupTimeRestController extends ApiController {

    @Autowired
    private IAttendanceGroupTimeService service;

    /**
     * 添加考勤组时间
     */

    @PostMapping("/attendanceGroupTimes")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = service.insertAttendanceGroupTime(maps);
        return R.ok().put("msg", save);
    }


    /**
     * 删除考勤组时间
     */

    @DeleteMapping("/attendanceGroupTimes")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }


    /**
     * 修改考勤组时间
     */

    @PutMapping("/attendanceGroupTimes")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = service.updateAttendanceGroupTime(maps);
        return R.ok().put("msg", update);
    }


    /**
     * 查询考勤组时间
     */

    @GetMapping("/attendanceGroupTimes")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceGroupTime attendanceGroupTime, PageDto dto) {
        IPage<AttendanceGroupTime> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceGroupTime> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceGroupTime);
        if (paging) {
            IPage<AttendanceGroupTime> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceGroupTime> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间
     */

    @GetMapping("/attendanceGroupTimes/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceGroupTime attendanceGroupTime = service.getById(id);
        return R.ok().put("data", attendanceGroupTime);
    }
}
