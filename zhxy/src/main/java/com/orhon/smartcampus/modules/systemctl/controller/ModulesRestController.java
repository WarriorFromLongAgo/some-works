package com.orhon.smartcampus.modules.systemctl.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.systemctl.entity.Modules;
import com.orhon.smartcampus.modules.systemctl.service.IModulesService;
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
@RequestMapping(value = "/systemctl/modules", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ModulesRestController extends ApiController  {
    @Autowired
    private IModulesService service;

    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Modules modules, PageDto dto) {
        IPage<Modules> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Modules> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(modules);
        IPage<Modules> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }


    @RequestMapping(value = "/get/{id}",method=RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        Modules byId = service.getById(id);
        return R.ok().put("data", byId);
    }


    @DeleteMapping("/del/{id}")
    public R delById(@PathVariable("id") Long id) {
        service.removeById(id);
        return R.ok();
    }


    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Modules modules = JSONObject.parseObject(JSONObject.toJSONString(maps), Modules.class);
        boolean save = service.save(modules);
        if (save) {
            return R.ok().put("data", modules);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }


    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Modules modules = JSONObject.parseObject(JSONObject.toJSONString(maps), Modules.class);
        service.updateById(modules);
        return R.ok().put("data", modules);
    }

    // TODO: 11/7/2019 获取无分页模块列表
    @GetMapping("/get/nopaginglist")
    @ResponseBody
    public R getNopaginglist(Modules modules, PageDto dto) {

        return R.ok().put("data", "");
    }

    // TODO: 11/7/2019 获取当前学校的模块列表、无分页
//    @GetMapping("/get/ourschool")
//    public R getOurschool(Long school_id) {
//    	List<Modules> list = service.getOurschool(school_id);
//        return R.ok().put("data",list);
//    }

    // TODO: 11/7/2019 获取当前用户的模块列表、无分页
    @GetMapping("/get/my")
    @ResponseBody
    public R getMy(Modules modules, PageDto dto) {

        return R.ok().put("data", "");
    }

    // TODO: 11/7/2019 获取指定用户的权限
    @GetMapping(value="/get/foruser/{id}")
    @ResponseBody
    public R getForuser(Modules modules,PageDto dto,@PathVariable("id") Integer id) {
        // 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
        return R.ok().put("data", "");
    }

    // TODO: 11/7/2019 获取指定部门、职务的权限
    @GetMapping(value="/get/fordepartment/{departmentId}/{dutyId}")
    @ResponseBody
    public R getFordepartment(Modules modules,PageDto dto,@PathVariable("departmentId") Integer departmentId,@PathVariable("dutyId") Integer dutyId) {
        // 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
        return R.ok().put("data", "");
    }

    // TODO: 11/7/2019 获取模块的菜单和组件
    @GetMapping(value="/get/menus/byModuleId/{moduleID}")
    @ResponseBody
    public R getMenusByModuleId(Modules modules,PageDto dto,@PathVariable("departmentId") Integer departmentId,@PathVariable("moduleID") Integer moduleID) {
        // 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
        return R.ok().put("data", "");
    }



}
