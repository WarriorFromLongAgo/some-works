package com.orhon.smartcampus.modules.watchlist.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.Basic;
import com.orhon.smartcampus.modules.moral.entity.DataStudent;
import com.orhon.smartcampus.modules.moral.service.IBasicService;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.mapper.AdmininspecttermMapper;
import com.orhon.smartcampus.modules.watchlist.service.IAdministrativedutyService;
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
 * 德育量化项目表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/watchlist/administrativeduty", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdministrativedutyRestController extends ApiController {
    @Autowired
    private IAdministrativedutyService service;
    @Autowired
    private InfoService infoService;
    @Autowired
    private AdmininspecttermMapper admininspecttermMapper;
    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = service.getDuty(maps,dto);
        return R.ok().put("data", list).put("count", service.getDuty(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }
    @GetMapping(value="/getNoPageList")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "administrativeduty_name")
    })
    @ResponseBody
    public R getNoPageList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = service.getDuty(maps,dto);
        return R.ok().put("data", list);
    }

    @GetMapping(value="/duty/getNoPageItemList")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "administrativeduty_name")
    })
    @ResponseBody
    public R getNoPageItemList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = service.getDuty(maps,dto);
        ArrayList<Object> ret = new ArrayList<>();
        for (Object obj:list) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
            jsonObject.put("duty",admininspecttermMapper.getItems(jsonObject.getIntValue("id")));
            ret.add(jsonObject);
        }
        return R.ok().put("data", ret);
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
        Administrativeduty byId = service.getById(id);
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
        Administrativeduty administrativeduty = JSONObject.parseObject(JSONObject.toJSONString(maps), Administrativeduty.class);
        administrativeduty.setSchool_id(school_id);
        boolean save = service.save(administrativeduty);
        if (save) {
            return R.ok().put("data", administrativeduty);
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
        Administrativeduty administrativeduty = JSONObject.parseObject(JSONObject.toJSONString(maps), Administrativeduty.class);
        service.updateById(administrativeduty);
        return R.ok().put("data", administrativeduty);
    }
}
