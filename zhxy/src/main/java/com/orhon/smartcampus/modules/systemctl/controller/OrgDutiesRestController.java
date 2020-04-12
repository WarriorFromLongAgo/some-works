package com.orhon.smartcampus.modules.systemctl.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDuties;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDutiesService;
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
@RequestMapping(value = "/systemctl/orgDuties", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrgDutiesRestController extends ApiController {
    @Autowired
    private IOrgDutiesService service;

    @GetMapping("/get/list")
    @ResponseBody
    public R getList(OrgDuties duties, PageDto dto) {
        IPage<OrgDuties> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<OrgDuties> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(duties);
        IPage<OrgDuties> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }


    @RequestMapping(value = "/get/{id}",method= RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        OrgDuties byId = service.getById(id);
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
        OrgDuties duties = JSONObject.parseObject(JSONObject.toJSONString(maps), OrgDuties.class);
        boolean save = service.save(duties);
        if (save) {
            return R.ok().put("data", duties);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }


    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        OrgDuties duties = JSONObject.parseObject(JSONObject.toJSONString(maps), OrgDuties.class);
        service.updateById(duties);
        return R.ok().put("data", duties);
    }
}
