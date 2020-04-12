package com.orhon.smartcampus.modules.systemctl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.modules.base.entity.Subjects;
import com.orhon.smartcampus.modules.base.service.IGradesService;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.modules.base.service.ISubjectsService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.systemctl.entity.Schoolsettings;
import com.orhon.smartcampus.modules.systemctl.service.ISchoolsettingsService;
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
 * 学校设置 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/systemctl/schoolsettings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchoolsettingsRestController extends ApiController {
    @Autowired
    private ISchoolsettingsService service;
    @Autowired
    private InfoService infoService;
    @Autowired
    private IGradesService gradesService;

    @Autowired
    private ISubjectsService subjectsService;

    @Autowired
    private IPeriodsService periodsService;

    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Schoolsettings setings, PageDto dto) {
        IPage<Schoolsettings> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Schoolsettings> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(setings);
        IPage<Schoolsettings> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }


    @RequestMapping(value = "/get/{id}",method= RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        Schoolsettings byId = service.getById(id);
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
        Schoolsettings setings = JSONObject.parseObject(JSONObject.toJSONString(maps), Schoolsettings.class);
        boolean save = service.save(setings);
        if (save) {
            return R.ok().put("data", setings);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }


    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Schoolsettings setings = JSONObject.parseObject(JSONObject.toJSONString(maps), Schoolsettings.class);
        service.updateById(setings);
        return R.ok().put("data", setings);
    }

    /**
     * 我校年级列表
     * @return
     */
    @GetMapping("/get/myschool/grades")
    @ResponseBody
    public R getMyschoolGrades() {
        HashMap <String, Object > schoolSetting = infoService.getCurrentSchoolSetting();
        JSONArray grades =  JSONObject.parseArray((String) schoolSetting.get("grades"));
        QueryWrapper<Grades> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",grades);
        List<Grades> list = gradesService.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * 我校学科列表
     * @return
     */
    @GetMapping("/get/myschool/subjects")
    @ResponseBody
    public R getMyschoolSubjects() {
        HashMap <String, Object > schoolSetting = infoService.getCurrentSchoolSetting();
        JSONArray subjects =  JSONObject.parseArray((String) schoolSetting.get("subjects"));
        QueryWrapper<Subjects> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",subjects);
        List<Subjects> list = subjectsService.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * 我校学科列表
     * @return
     */
    @GetMapping("/get/myschool/periods")
    @ResponseBody
    public R getMyschoolPeriods() {
        HashMap <String, Object > schoolSetting = infoService.getCurrentSchoolSetting();
        JSONArray  periods=  JSONObject.parseArray((String) schoolSetting.get("periods"));
        QueryWrapper<Periods> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",periods);
        List<Periods> list = periodsService.list(queryWrapper);
        return R.ok().put("data", list);
    }


}
