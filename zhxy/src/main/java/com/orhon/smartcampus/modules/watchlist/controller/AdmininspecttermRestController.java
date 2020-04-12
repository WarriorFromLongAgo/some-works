package com.orhon.smartcampus.modules.watchlist.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.watchlist.entity.Admininspectterm;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.service.IAdmininspecttermService;
import com.orhon.smartcampus.modules.watchlist.service.IAdministrativedutyService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政值班检查项 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/watchlist/admininspectterm", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdmininspecttermRestController extends ApiController {
    @Autowired
    private IAdmininspecttermService service;
    @Autowired
    private InfoService infoService;
    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        String administrativeduty_id = (String)maps.get("administrativeduty_id");
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        maps.put("administrativeduty_id", administrativeduty_id);
        List<HashMap<String, Object>> list = service.getTerm(maps,dto);
        return R.ok().put("data", list).put("count", service.getTerm(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }

    /**
     * id查询一条数据
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        maps.put("id",id);
        Admininspectterm byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     * @param Users
     * @param bao
     * @return
     */
    @DeleteMapping(value="/delById/{id}")
    @ResponseBody
    public R delById(@PathVariable("id") Integer id) {
        service.removeById(id);
        return R.ok().put("msg", "删除成功");
    }

    /**
     * 新增一条记录
     * @param Users
     * @param bao
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        Admininspectterm admininspectterm = JSONObject.parseObject(JSONObject.toJSONString(maps), Admininspectterm.class);
        admininspectterm.setSchool_id(school_id);
        boolean save = service.save(admininspectterm);
        if (save) {
            return R.ok().put("data", admininspectterm);
        }
        return R.error().put("msg" , service.getValidationData().toString());
    }

    /**
     * id修改一条记录
     * @param Users
     * @param bao
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Admininspectterm admininspectterm = JSONObject.parseObject(JSONObject.toJSONString(maps), Admininspectterm.class);
        service.updateById(admininspectterm);
        return R.ok().put("data", admininspectterm);
    }
}
