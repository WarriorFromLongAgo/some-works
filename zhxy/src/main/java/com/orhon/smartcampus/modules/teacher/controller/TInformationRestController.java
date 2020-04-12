package com.orhon.smartcampus.modules.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/teacher/information", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TInformationRestController extends ApiController {
    @Autowired
    private TIInformationService service;
    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private InfoService infoService;
    /**
     * 条件加分页查询集合
     * @param information
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
        Map teacher_name = JSON.parseObject((String)maps.get("teacher_name"));
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<TInformation> list = service.getTeacherInfomation(maps,dto,teacher_name);
        return R.ok().put("data", list).put("count", service.getTeacherInfomation(maps,teacher_name).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }
    //******教职工信息start*****//
        //获取在职人员列表
        @GetMapping(value="/getWorkList")
        @ResponseBody
        public R getWorkList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
            Map teacher_name = JSON.parseObject((String)maps.get("teacher_name"));
            Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
            maps.put("school_id", school_id);
            maps.put("workstatus", "on-the-job");
            List<TInformation> list = service.getTeacherInfomation(maps,dto,teacher_name);
            return R.ok().put("data", list).put("count", service.getTeacherInfomation(maps,teacher_name).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
        }


        //获取退休人员列表
        @GetMapping(value="/getNoWorkList")
        @ResponseBody
        public R getNoWorkList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
            Map teacher_name = JSON.parseObject((String)maps.get("teacher_name"));
            Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
            maps.put("school_id", school_id);
            maps.put("workstatus", "retired");
            List<TInformation> list = service.getTeacherInfomation(maps,dto,teacher_name);
            return R.ok().put("data", list).put("count", service.getTeacherInfomation(maps,teacher_name).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
        }

        //获取其他教职工列表
        @GetMapping(value="/getOtherList")
        @ResponseBody
        public R getOtherList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
            Map teacher_name = JSON.parseObject((String)maps.get("teacher_name"));
            Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
            maps.put("school_id", school_id);
            maps.put("workstatus", 640);
            List<TInformation> list = service.getTeacherInfomation(maps,dto,teacher_name);
            return R.ok().put("data", list).put("count", service.getTeacherInfomation(maps,teacher_name).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
        }

        //获取我的消息(未完)
        @GetMapping(value="/getMyInformation")
        @ResponseBody
        public R getMyInformation() {
            HashMap<String,Object> ret =  infoService.getCurrentUser();
            return R.ok().put("data", ret);
        }
    //******教职工信息end*****//



    //******教职工信息统计start*****//
        //教职工总体统计
        @GetMapping(value="/getStatistics")
        @ResponseBody
        public R getStatistics() {
            Object data = this.service.getStatistics();
            return R.ok().put("data",data);
        }
        //在职教职工统计
        @GetMapping(value="/getWorkStatistics")
        @ResponseBody
        public R getWorkStatistics() {
            Object data = this.service.getWorkStatistics();
            return R.ok().put("data",data);
        }
        //退休教职工统计
        @GetMapping(value="/getNoWorkStatistics")
        @ResponseBody
        public R getNoWorkStatistics() {
            Object data = this.service.getNoWorkStatistics();
            return R.ok().put("data",data);
        }
    //******教职工信息统计end*****//
    /**
     * id查询一条数据
     * @param information
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        TInformation byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     * @param information
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
     * @param student
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        TInformation information = JSONObject.parseObject(JSONObject.toJSONString(maps), TInformation.class);
        information.setSchool_id(school_id);
        information.setUnitname(school_id);
        boolean save = service.save(information);
        if (save) {
			return R.ok().put("data",information);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param information
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        TInformation information = JSONObject.parseObject(JSONObject.toJSONString(maps), TInformation.class);
        service.updateById(information);
        return R.ok().put("data",information);
    }
}
