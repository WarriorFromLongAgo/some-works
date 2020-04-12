package com.orhon.smartcampus.modules.attendance.controller;

import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.attendance.service.IAttendanceDataService;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤数据表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceDataRestController extends ApiController {

    @Autowired
    private IAttendanceDataService attendanceDataService;


    /**
     * 推送考勤数据
     */

    @PostMapping("/datas")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String result = attendanceDataService.insertAttendanceData(maps);
        return R.ok().put("data", result);
    }

    /**
     * 今日考勤率
     *
     * @return 今日出勤率
     */
    @GetMapping("/attendancetoday")
    public R attendancetoday() {
        HashMap result = attendanceDataService.attendancetoday();
        return R.ok().put("data", result);
    }

    /**
     * 最近7日考勤人数
     *
     * @return
     */
    @GetMapping("/attendancenearsevenday")
    public R attendancenearsevenday() {
        ArrayList result = attendanceDataService.attendancenearsevenday();
        return R.ok().put("msg", result);
    }


    /**
     * 考勤数据统计
     *
     * @param maps
     * @return
     */
    @GetMapping("/attendancedatastatistics")
    public R attendancedatastatistics(@RequestBody HashMap<String, Object> maps) {
        List result = attendanceDataService.attendancedatastatistics(maps);
        return R.ok().put("msg", result);
    }
}
