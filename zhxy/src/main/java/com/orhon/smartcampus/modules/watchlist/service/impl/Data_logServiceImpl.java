package com.orhon.smartcampus.modules.watchlist.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.entity.Data_log;
import com.orhon.smartcampus.modules.watchlist.mapper.AdministrativedutyMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.Data_logMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.Data_log_cadreMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.DatasMapper;
import com.orhon.smartcampus.modules.watchlist.service.IData_logService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 数据添加 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class Data_logServiceImpl extends BaseServiceImpl<Data_logMapper, Data_log>implements IData_logService {
    @Autowired
    private InfoService infoService;

    @Autowired
    private Data_logMapper data_logMapper;
    @Autowired
    private Data_log_cadreMapper data_log_cadreMapper;
    @Autowired
    private DatasMapper datasMapper;
    @Autowired
    private IData_logService data_logService;

    @Override
    public List<HashMap<String, Object>> getDataLog(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = data_logMapper.getDataLog(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getDataLog(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return data_logMapper.getDataLogpage(maps,dto);
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        Data_log data_log = JSONObject.parseObject(JSONObject.toJSONString(maps), Data_log.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        data_log.setSchool_id(school_id);
        boolean save = data_logService.save(data_log);
        if(save){
            JSONArray cadres = (JSONArray) maps.get("cadre");
            Integer leader = (Integer) maps.get("leader");
            String time = (String) maps.get("time");
            HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
            Integer semester_id = (Integer) Semester.get("id");
            Integer data_log_id = data_log.getId();
            for (Object cadre:cadres) {
                data_log_cadreMapper.inserts(data_log_id,cadre);
            }
            JSONArray datas = (JSONArray) maps.get("data");
            for (Object data:datas) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
                Integer administrativeduty_id = jsonObject.getIntValue("administrativeduty_id");
                Integer admininspectterm_id = jsonObject.getIntValue("admininspectterm_id");
                String explains = jsonObject.getString("explains");
                datasMapper.inserts(administrativeduty_id,admininspectterm_id,leader,school_id,time,data_log_id,semester_id,explains);
            }
            return R.ok("添加成功！");
        }
        return R.error("参数错误！");
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R updates(HashMap<String, Object> maps) {
        Data_log data_log = JSONObject.parseObject(JSONObject.toJSONString(maps), Data_log.class);
        boolean save = data_logService.updateById(data_log);
        if(save){
            JSONArray cadres = (JSONArray) maps.get("cadre");
            Integer leader = (Integer) maps.get("leader");
            String time = (String) maps.get("time");
            Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
            HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
            Integer semester_id = (Integer) Semester.get("id");
            Integer data_log_id = data_log.getId();
            data_log_cadreMapper.deletes(data_log_id);
            for (Object cadre:cadres) {
                data_log_cadreMapper.inserts(data_log_id,cadre);
            }
            JSONArray datas = (JSONArray) maps.get("data");
            datasMapper.deletes(data_log_id);
            for (Object data:datas) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
                Integer administrativeduty_id = jsonObject.getIntValue("administrativeduty_id");
                Integer admininspectterm_id = jsonObject.getIntValue("admininspectterm_id");
                String explains = jsonObject.getString("explains");
                datasMapper.inserts(administrativeduty_id,admininspectterm_id,leader,school_id,time,data_log_id,semester_id,explains);
            }
            return R.ok("添加成功！");
        }
        return R.error("参数错误！");
    }
}
