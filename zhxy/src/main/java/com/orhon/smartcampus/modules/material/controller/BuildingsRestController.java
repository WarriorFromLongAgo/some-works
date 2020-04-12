package com.orhon.smartcampus.modules.material.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.material.entity.Buildings;
import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.modules.material.service.IBuildingsService;
import com.orhon.smartcampus.modules.material.service.ICampusService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 楼宇表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/material/buildings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BuildingsRestController extends ApiController {
    @Autowired
    private IBuildingsService service;
    @Autowired
    private InfoService infoService;

    /**
     * 条件加分页查询集合
     * @param buiidings
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Buildings buiidings, PageDto dto) {
        IPage<Buildings> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Buildings> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(buiidings);
        IPage<Buildings> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }

    @GetMapping(value="/getNoPageList")
    @ResponseBody
    public R getNoPageList(Buildings buiidings, PageDto dto) {
        QueryWrapper<Buildings> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(buiidings);
        List<Buildings> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     * @param buiidings
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Buildings byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     * @param Holidays
     * @param dto
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
     * @param buiidings
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Buildings buiidings = JSONObject.parseObject(JSONObject.toJSONString(maps), Buildings.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        boolean save = service.save(buiidings);
        if (save) {
            return R.ok().put("data", buiidings);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param buiidings
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Buildings buiidings = JSONObject.parseObject(JSONObject.toJSONString(maps), Buildings.class);
        service.updateById(buiidings);
        return R.ok().put("data", buiidings);
    }
}
