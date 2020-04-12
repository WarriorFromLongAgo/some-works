package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialDate;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceSpecialPersonnel;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceSpecialPersonnelService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 特殊情况不需要打卡数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceSpecialPersonnelRestController extends ApiController {

    @Autowired
    private IAttendanceSpecialPersonnelService service;

    /**
     * 添加特殊考勤人员
     */

    @PostMapping("/specialPersonnels")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = service.insertAttendanceSpecialPersonnel(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除特殊考勤人员
     */

    @DeleteMapping("/specialPersonnels")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        service.removeById(maps);
        //Todo 需要加软删除
        return R.ok();
    }

    /**
     * 修改特殊考勤人员
     */

    @PutMapping("/specialPersonnels")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = service.updateAttendanceSpecialPersonnel(maps);
        return R.ok().put("msg", update);
    }

    /**
     * 查询特殊考勤人员
     */

    @GetMapping("/specialPersonnels")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceSpecialPersonnel attendanceSpecialPersonnel, PageDto dto) {
        IPage<AttendanceSpecialPersonnel> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceSpecialPersonnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceSpecialPersonnel);
        if (paging) {
            IPage<AttendanceSpecialPersonnel> pagelist = service.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceSpecialPersonnel> pagelists = service.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条考勤组时间
     */

    @GetMapping("/specialPersonnels/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceSpecialPersonnel attendanceSpecialPersonnel = service.getById(id);
        return R.ok().put("data", attendanceSpecialPersonnel);
    }
}
