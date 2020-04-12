package com.orhon.smartcampus.modules.student.controller;

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
@RequestMapping(value = "/eclass_grade_view", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Eclass_grade_viewRestController extends ApiController {

}
