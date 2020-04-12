package com.orhon.smartcampus.modules.moral.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.moral.entity.GroupClass;
import com.orhon.smartcampus.modules.moral.entity.MonthWeek;
import com.orhon.smartcampus.modules.moral.service.IGroupClassService;
import com.orhon.smartcampus.modules.moral.service.IMonthWeekService;
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
@RequestMapping(value = "/moral/monthWeek", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MonthWeekRestController extends ApiController {
    @Autowired
    private IMonthWeekService service;

    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(MonthWeek monthWeek, PageDto dto) {
        IPage<MonthWeek> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<MonthWeek> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(monthWeek);
        IPage<MonthWeek> pagelist = service.page(page, queryWrapper);
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
        MonthWeek byId = service.getById(id);
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
        MonthWeek monthWeek = JSONObject.parseObject(JSONObject.toJSONString(maps), MonthWeek.class);
        boolean save = service.save(monthWeek);
        if (save) {
            return R.ok().put("data", monthWeek);
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
        MonthWeek monthWeek = JSONObject.parseObject(JSONObject.toJSONString(maps), MonthWeek.class);
        service.updateById(monthWeek);
        return R.ok().put("data", monthWeek);
    }
}
