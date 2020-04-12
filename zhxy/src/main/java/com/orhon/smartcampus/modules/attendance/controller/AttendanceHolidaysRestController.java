package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceHolidays;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceHolidaysService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 节假日管理 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceHolidaysRestController extends ApiController {


    @Autowired
    private IAttendanceHolidaysService attendanceHolidaysService;

    /**
     * 添加假期
     *
     * @param maps
     * @return
     */
    @PostMapping("/holidays")
    @ResponseBody
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = attendanceHolidaysService.insertAttendanceHolidaysRest(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 修改假期
     *
     * @param maps
     * @return
     */
    @PutMapping("/holidays")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = attendanceHolidaysService.updateAttendanceHolidaysRest(maps);
        return R.ok().put("msg", update);
    }

    /**
     * 删除假期
     *
     * @param maps
     * @return
     */
    @DeleteMapping("/holidays")
    @ResponseBody
    public R delete(@RequestBody HashMap<String, Object> maps) {
        Boolean delete = attendanceHolidaysService.removeById(maps);
        return R.ok();
    }

    /**
     * 查询假期
     *
     * @param paging
     * @param attendanceHolidays
     * @param dto
     * @return
     */
    @GetMapping("/holidays")
    @ResponseBody
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceHolidays attendanceHolidays, PageDto dto) {
        IPage<AttendanceHolidays> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceHolidays> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceHolidays);
        if (paging) {
            IPage<AttendanceHolidays> pagelist = attendanceHolidaysService.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceHolidays> pagelists = attendanceHolidaysService.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条假期
     *
     * @param id
     * @return
     */

    @GetMapping("/holidays/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceHolidays attendanceHolidays = attendanceHolidaysService.getById(id);
        return R.ok().put("data", attendanceHolidays);
    }
}
