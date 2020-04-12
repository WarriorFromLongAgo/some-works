package com.orhon.smartcampus.modules.watchlist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 * 行政值班数据项 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/datas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DatasRestController extends ApiController {

}
