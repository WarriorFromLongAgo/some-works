package com.orhon.smartcampus.modules.moral.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.Basic;
import com.orhon.smartcampus.modules.moral.entity.DataStudent;
import com.orhon.smartcampus.modules.moral.service.IBasicService;
import com.orhon.smartcampus.modules.moral.service.IDataStudentService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
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
@RequestMapping(value = "/moral/basic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BasicRestController extends ApiController {
    @Autowired
    private IBasicService service;
    @Autowired
    private InfoService infoService;
    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "semester_name")
    })
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        String semester_id = (String)maps.get("semester_id");
        if(semester_id ==null){
            HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
            maps.put("semester_id", Semester.get("id"));
        }else{
            maps.put("semester_id", semester_id);
        }
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = service.getBase(maps,dto);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        maps.put("id",id);
        Basic byId = service.getById(id);
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
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        Basic basic = JSONObject.parseObject(JSONObject.toJSONString(maps), Basic.class);
        basic.setSchool_id(school_id);
        boolean save = service.save(basic);
        if (save) {
            return R.ok().put("data", basic);
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
        Basic basic = JSONObject.parseObject(JSONObject.toJSONString(maps), Basic.class);
        service.updateById(basic);
        return R.ok().put("data", basic);
    }
}
