package com.orhon.smartcampus.modules.attendance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/statistics_month", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceStatisticsMonthRestController extends ApiController {

}
