package com.orhon.smartcampus.modules.moral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.Item;
import com.orhon.smartcampus.modules.moral.entity.Rules;
import com.orhon.smartcampus.modules.moral.service.IItemService;
import com.orhon.smartcampus.modules.moral.service.IRulesService;
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
 * 德育量化项目细则表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/moral/rules", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RulesRestController extends ApiController {
    @Autowired
    private IRulesService service;
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
    public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        Map moral_education_id = JSON.parseObject((String)maps.get("moral_education_id"));
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        maps.put("moral_education_id", moral_education_id);
        List<HashMap<String, Object>> list = service.getRule(maps,dto);
        return R.ok().put("data", list).put("count", service.getRule(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }

    /**
     * id查询一条数据
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Rules byId = service.getById(id);
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
        Rules rules = JSONObject.parseObject(JSONObject.toJSONString(maps), Rules.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        rules.setSchool_id(school_id);
        boolean save = service.save(rules);
        if (save) {
            return R.ok().put("data", rules);
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
        Rules rules = JSONObject.parseObject(JSONObject.toJSONString(maps), Rules.class);
        service.updateById(rules);
        return R.ok().put("data", rules);
    }
}
