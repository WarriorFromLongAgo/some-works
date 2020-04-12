package com.orhon.smartcampus.modules.watchlist.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.entity.Data_log;
import com.orhon.smartcampus.modules.watchlist.mapper.AdministrativedutyMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.Data_logMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.Data_log_cadreMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.DatasMapper;
import com.orhon.smartcampus.modules.watchlist.service.IAdministrativedutyService;
import com.orhon.smartcampus.modules.watchlist.service.IData_logService;
import com.orhon.smartcampus.modules.watchlist.service.IData_log_cadreService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 数据添加 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/watchlist/data_log", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Data_logRestController extends ApiController {
    @Autowired
    private IData_logService service;
    @Autowired
    private IAdministrativedutyService administrativedutyService;
    @Autowired
    private Data_log_cadreMapper data_log_cadreMapper;
    @Autowired
    private AdministrativedutyMapper administrativedutyMapper;

    @Autowired
    private DatasMapper datasMapper;
    @Autowired
    private Data_logMapper data_logMapper;

    @Autowired
    private InfoService infoService;

    private Object gv(Object a){
        if(ObjectUtils.allNotNull(a)){
            return (Object) a;
        }
        return 0;
    }
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
        List<HashMap<String, Object>> list = service.getDataLog(maps,dto);
        ArrayList<JSONObject> ret = new ArrayList<JSONObject>();
        for (Object lists:list) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(lists));
            Integer data_log_id = jsonObject.getIntValue("id");
            jsonObject.put("crdre" ,data_log_cadreMapper.getDataLogCadre(data_log_id));
            ret.add(jsonObject);
        }
        return R.ok().put("data", ret).put("count", service.getDataLog(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
    }
    /**
     * id查询一条数据
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "leader_teacher"),
            @JsonForamtCmd(cmd = "raw" , okey = "teacher_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "admininspectterm_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "administrativeduty_name")
    })
    @ResponseBody
    public R getById(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        Object byId = data_logMapper.getById(id);
        ArrayList<JSONObject> ret = new ArrayList<JSONObject>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(byId));
        Integer data_log_id = id;
        //环节干部
        jsonObject.put("crdre" ,data_log_cadreMapper.getDataLogCadre(data_log_id));
        //值班项
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        maps.put("school_id", school_id);
        List<HashMap<String, Object>> list = administrativedutyService.getDuty(maps,dto);
        ArrayList<JSONObject> term = new ArrayList<JSONObject>();
        for (Object lists:list) {
            JSONObject jsonObjects = JSON.parseObject(JSON.toJSONString(lists));
            Integer duty_id = jsonObjects.getIntValue("id");
            List<HashMap<String, Object>> duty_term = administrativedutyMapper.getDutyTerm(duty_id);
            ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
            for (Object data:duty_term) {
                JSONObject jsonObjectdata = JSON.parseObject(JSON.toJSONString(data));
                Integer term_id = jsonObjectdata.getIntValue("id");
                jsonObjectdata.put("watchlist_datas",this.gv(datasMapper.getData(duty_id,term_id,data_log_id)));
                datas.add(jsonObjectdata);
            }
            jsonObjects.put("term" ,datas);
            term.add(jsonObjects);
        }
        jsonObject.put("duty" ,term);
        ret.add(jsonObject);
        return R.ok().put("data", ret);
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
     * 修改一条记录
     * @param Users
     * @param bao
     * @return
     */
    @PutMapping(value="/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        return service.updates(maps);
    }
    /**
     * 删除一条记录
     * @param Users
     * @param bao
     * @return
     */
    @DeleteMapping(value="/delById/{id}")
    @ResponseBody
    public R delById(@PathVariable("id") Integer id) {
        service.removeById(id);
        data_log_cadreMapper.deletes(id);
        datasMapper.deletes(id);
        return R.ok().put("msg", "删除成功");
    }
}
