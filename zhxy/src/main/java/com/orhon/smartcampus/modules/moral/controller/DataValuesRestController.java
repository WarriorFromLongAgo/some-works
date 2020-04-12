package com.orhon.smartcampus.modules.moral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.DataStudent;
import com.orhon.smartcampus.modules.moral.entity.DataValues;
import com.orhon.smartcampus.modules.moral.mapper.DataStudentMapper;
import com.orhon.smartcampus.modules.moral.service.IDataStudentService;
import com.orhon.smartcampus.modules.moral.service.IDataValuesService;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.ArrayList;
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
@RequestMapping(value = "/moral/dataValues", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataValuesRestController extends ApiController {
    @Autowired
    private IDataValuesService service;
    @Autowired
    private InfoService infoService;
    @Autowired
    private DataStudentMapper dataStudentMapper;

    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
        maps.put("semester_id", Semester.get("id"));
        List<HashMap<String, Object>> list = service.getDateValue(maps,dto);
        ArrayList<Object> ret = new ArrayList<>();
        for (Object lists:list) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(lists));
            jsonObject.put("student",dataStudentMapper.getStudent(jsonObject.getIntValue("id")));
            ret.add(jsonObject);
        }
        return R.ok().put("data", ret).put("count", service.getDateValue(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
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
        Object obj = service.getOneDateValue(id);
        return R.ok().put("data", obj);
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
        dataStudentMapper.deletes(id);
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
        return service.inserts(maps);
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
        return service.updates(maps);
    }
    /**
     * 学生德育统计
     * @param Users
     * @param bao
     * @return
     */
    @PostMapping("/studentStatics")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "student_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "class_name"),
    })
    @ResponseBody
    public R studentStatics(@RequestBody HashMap<String, Object> maps) {
        return service.studentStatics(maps);
    }
}
