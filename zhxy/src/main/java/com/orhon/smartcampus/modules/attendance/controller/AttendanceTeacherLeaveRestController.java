package com.orhon.smartcampus.modules.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceTeacherLeave;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceTeacherLeaveService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 教师请假数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceTeacherLeaveRestController extends ApiController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private IAttendanceTeacherLeaveService attendanceTeacherLeaveService;

    /**
     * 添加教师请假
     *
     * @param maps
     * @return
     */
    @PostMapping("/teacherLeaves")
    @ResponseBody
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = attendanceTeacherLeaveService.insertAttendanceTeacherLeaveService(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除教师请假
     *
     * @param maps
     * @return
     */
    @DeleteMapping("/teacherLeaves")
    @ResponseBody
    public R delete(@RequestBody HashMap<String, Object> maps) {
        attendanceTeacherLeaveService.removeById(maps);
        return R.ok();
    }

    /**
     * 修改教师请假
     *
     * @param maps
     * @return
     */
    @PutMapping("/teacherLeaves")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = attendanceTeacherLeaveService.updateAttendanceTeacherLeaveService(maps);
        return R.ok().put("msg", update);
    }

    /**
     * 查询教师请假
     */

    @GetMapping("/teacherLeaves")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, AttendanceTeacherLeave attendanceTeacherLeave, PageDto dto) {
        IPage<AttendanceTeacherLeave> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<AttendanceTeacherLeave> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(attendanceTeacherLeave);
        if (paging) {
            IPage<AttendanceTeacherLeave> pagelist = attendanceTeacherLeaveService.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<AttendanceTeacherLeave> pagelists = attendanceTeacherLeaveService.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条教师请假
     */

    @GetMapping("/teacherLeaves/{id}")
    public R single(@PathVariable("id") Long id) {
        AttendanceTeacherLeave attendanceTeacherLeave = attendanceTeacherLeaveService.getById(id);
        return R.ok().put("data", attendanceTeacherLeave);
    }
}
