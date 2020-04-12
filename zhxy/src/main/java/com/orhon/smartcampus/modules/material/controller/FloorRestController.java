package com.orhon.smartcampus.modules.material.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.modules.material.entity.Floor;
import com.orhon.smartcampus.modules.material.entity.Unit;
import com.orhon.smartcampus.modules.material.service.ICampusService;
import com.orhon.smartcampus.modules.material.service.IFloorService;
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
 * 楼层 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/material/floor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FloorRestController extends ApiController {
    @Autowired
    private IFloorService service;

    /**
     * 条件加分页查询集合
     * @param floor
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Floor floor, PageDto dto) {
        IPage<Floor> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Floor> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(floor);
        IPage<Floor> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }
    @GetMapping(value="/getNoPageList")
    @ResponseBody
    public R getNoPageList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        String unit_id = (String)maps.get("unit_id");
        QueryWrapper<Floor> queryWrapper = new QueryWrapper<>();
        if(unit_id!= null){
            queryWrapper.eq("unit_id",unit_id);
        }
        List<Floor> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     * @param floor
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Floor byId = service.getById(id);
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
     * @param floor
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Floor floor = JSONObject.parseObject(JSONObject.toJSONString(maps), Floor.class);
        boolean save = service.save(floor);
        if (save) {
            return R.ok().put("data", floor);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param floor
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Floor floor = JSONObject.parseObject(JSONObject.toJSONString(maps), Floor.class);
        service.updateById(floor);
        return R.ok().put("data", floor);
    }
}
