package com.orhon.smartcampus.modules.material.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.material.entity.Buildings;
import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.modules.material.entity.Unit;
import com.orhon.smartcampus.modules.material.service.ICampusService;
import com.orhon.smartcampus.modules.material.service.IUnitService;
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
 * 单元表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/material/unit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UnitRestController extends ApiController {
    @Autowired
    private IUnitService service;

    /**
     * 条件加分页查询集合
     * @param unit
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Unit unit, PageDto dto) {
        IPage<Unit> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(unit);
        IPage<Unit> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }

    @GetMapping(value="/getNoPageList")
    @ResponseBody
    public R getNoPageList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        String buildings_id = (String)maps.get("buildings_id");
        QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
        if(buildings_id!= null){
            queryWrapper.eq("buildings_id",buildings_id);
        }
        List<Unit> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     * @param unit
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Unit byId = service.getById(id);
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
     * @param unit
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Unit unit = JSONObject.parseObject(JSONObject.toJSONString(maps), Unit.class);
        boolean save = service.save(unit);
        if (save) {
            return R.ok().put("data", unit);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param unit
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Unit unit = JSONObject.parseObject(JSONObject.toJSONString(maps), Unit.class);
        service.updateById(unit);
        return R.ok().put("data", unit);
    }
}
