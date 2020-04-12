package com.orhon.smartcampus.modules.material.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.modules.material.entity.Floor;
import com.orhon.smartcampus.modules.material.entity.Room;
import com.orhon.smartcampus.modules.material.service.ICampusService;
import com.orhon.smartcampus.modules.material.service.IRoomService;
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
 * 房间 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/material/room", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoomRestController extends ApiController {
    @Autowired
    private IRoomService service;

    /**
     * 条件加分页查询集合
     * @param room
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Room room, PageDto dto) {
        IPage<Room> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(room);
        IPage<Room> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }

    @GetMapping(value="/getNoPageList")
    @ResponseBody
    public R getNoPageList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        String floor_id = (String)maps.get("floor_id");
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if(floor_id!= null){
            queryWrapper.eq("floor_id",floor_id);
        }
        List<Room> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     * @param room
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Room byId = service.getById(id);
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
     * @param room
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Room room = JSONObject.parseObject(JSONObject.toJSONString(maps), Room.class);
        boolean save = service.save(room);
        if (save) {
            return R.ok().put("data", room);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param room
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Room room = JSONObject.parseObject(JSONObject.toJSONString(maps), Room.class);
        service.updateById(room);
        return R.ok().put("data", room);
    }
}
