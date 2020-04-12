package com.orhon.smartcampus.modules.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeService;
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
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/teacher/officeArrange", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfficeArrangeRestController extends ApiController {
    @Autowired
    private IOfficeArrangeService service;
    @Autowired
    private InfoService infoService;


    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "department_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "buildings_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "unit_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "floor_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "name")
    })
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = service.getOfficeArrange(maps,dto);
        return R.ok().put("data", list).put("count", service.getOfficeArrange(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
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
        OfficeArrange byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    @GetMapping(value="/getDepartmentRoom/{id}")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "department_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "buildings_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "unit_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "floor_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "name")
    })
    @ResponseBody
    public R getDepartmentRoom(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        maps.put("department_id", id);
        List<HashMap<String, Object>> list = service.getOfficeArrange(maps,dto);
        return R.ok().put("data", list);
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
        OfficeArrange officeArrange = JSONObject.parseObject(JSONObject.toJSONString(maps), OfficeArrange.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        officeArrange.setSchool_id(school_id);
        boolean save = service.save(officeArrange);
        if (save) {
            return R.ok().put("data", officeArrange);
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
        OfficeArrange officeArrange = JSONObject.parseObject(JSONObject.toJSONString(maps), OfficeArrange.class);
        service.updateById(officeArrange);
        return R.ok().put("data", officeArrange);
    }
}
