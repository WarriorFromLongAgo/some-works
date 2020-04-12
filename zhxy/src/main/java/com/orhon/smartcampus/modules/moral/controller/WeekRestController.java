package com.orhon.smartcampus.modules.moral.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.moral.entity.GroupClass;
import com.orhon.smartcampus.modules.moral.entity.Week;
import com.orhon.smartcampus.modules.moral.service.IGroupClassService;
import com.orhon.smartcampus.modules.moral.service.IWeekService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/moral/week", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WeekRestController extends ApiController {
    @Autowired
    private IWeekService service;

    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Week week, PageDto dto) {
        IPage<Week> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Week> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(week);
        IPage<Week> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
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
        Week byId = service.getById(id);
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
        Week week = JSONObject.parseObject(JSONObject.toJSONString(maps), Week.class);
        boolean save = service.save(week);
        if (save) {
            return R.ok().put("data", week);
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
        Week week = JSONObject.parseObject(JSONObject.toJSONString(maps), Week.class);
        service.updateById(week);
        return R.ok().put("data", week);
    }
}
