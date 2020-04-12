package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupTime;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialDate;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceSpecialDateService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 特殊需要上班日期数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceSpecialDateRestController extends ApiController {

    @Autowired
    private IAttendanceSpecialDateService service;

    /**
     * 添加特殊考勤时间
     */

    @PostMapping("/specialDates")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = service.insertAttendanceSpecialDate(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除特殊考勤时间
     */

    @DeleteMapping("/specialDates")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }

    /**
     * 修改特殊考勤时间
     */

    @PutMapping("/specialDates")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = service.updateAttendanceSpecialDate(maps);
        return R.ok().put("msg", update);
    }

    /**
     * 查询特殊考勤时间
     */

    @GetMapping("/specialDates")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceSpecialDate attendanceSpecialDate, PageDto dto) {
        IPage<AttendanceSpecialDate> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceSpecialDate> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceSpecialDate);
        if (paging) {
            IPage<AttendanceSpecialDate> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceSpecialDate> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间
     */

    @GetMapping("/specialDates/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceSpecialDate attendanceSpecialDate = service.getById(id);
        return R.ok().put("data", attendanceSpecialDate);
    }
}
