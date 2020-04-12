package com.orhon.smartcampus.modules.systemctl.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.material.entity.Buildings;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDepartmentsService;
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
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/systemctl/orgDepartments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrgDepartmentsRestController extends ApiController {
    @Autowired
    private IOrgDepartmentsService service;

    @GetMapping("/get/list")
    @ResponseBody
    public R getList(OrgDepartments departments, PageDto dto) {
        IPage<OrgDepartments> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<OrgDepartments> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(departments);
        IPage<OrgDepartments> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }

    @GetMapping(value="/getNoPageList")
    @ResponseBody
    public R getNoPageList(OrgDepartments departments, PageDto dto) {
        QueryWrapper<OrgDepartments> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(departments);
        List<OrgDepartments> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }
    /**部门下人员信息
     *
     */
    //办公室下教师信息
    @GetMapping(value="/getDepartmentTeacher/{id}")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "teacher_name")
    })
    @ResponseBody
    public R getDepartmentTeacher(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        maps.put("department_id", id);
        List<HashMap<String, Object>> list = service.getDepartmentTeacher(maps,dto);
        return R.ok().put("data", list);
    }



    /**
     *
     */
    @RequestMapping(value = "/get/{id}",method= RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        OrgDepartments byId = service.getById(id);
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
        OrgDepartments departments = JSONObject.parseObject(JSONObject.toJSONString(maps), OrgDepartments.class);
        boolean save = service.save(departments);
        if (save) {
            return R.ok().put("data", departments);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }


    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        OrgDepartments departments = JSONObject.parseObject(JSONObject.toJSONString(maps), OrgDepartments.class);
        service.updateById(departments);
        return R.ok().put("data", departments);
    }
}
