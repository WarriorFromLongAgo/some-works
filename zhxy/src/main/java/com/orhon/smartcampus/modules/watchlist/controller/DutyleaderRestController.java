package com.orhon.smartcampus.modules.watchlist.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.entity.Dutyleader;
import com.orhon.smartcampus.modules.watchlist.service.IAdministrativedutyService;
import com.orhon.smartcampus.modules.watchlist.service.IDutyleaderService;
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
 * 值班领导、环节干部 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/watchlist/dutyleader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DutyleaderRestController extends ApiController {
    @Autowired
    private IDutyleaderService service;
    @Autowired
    private InfoService infoService;
    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        String type = (String)maps.get("type");
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        maps.put("type", type);
        List<HashMap<String, Object>> list = service.getLeader(maps,dto);
        return R.ok().put("data", list).put("count", service.getLeader(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }
    @GetMapping(value="/getNoPageList")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "teacher_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "department_name")
    })
    @ResponseBody
    public R getNoPageList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
        String type = (String)maps.get("type");
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        maps.put("type", type);
        List<HashMap<String, Object>> list = service.getLeader(maps,dto);
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
        Dutyleader byId = service.getById(id);
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
        Dutyleader dutyleader = JSONObject.parseObject(JSONObject.toJSONString(maps), Dutyleader.class);
        dutyleader.setSchool_id(school_id);
        boolean save = service.save(dutyleader);
        if (save) {
            return R.ok().put("data", dutyleader);
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
        Dutyleader dutyleader = JSONObject.parseObject(JSONObject.toJSONString(maps), Dutyleader.class);
        service.updateById(dutyleader);
        return R.ok().put("data", dutyleader);
    }
}
