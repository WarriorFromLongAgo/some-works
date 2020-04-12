package com.orhon.smartcampus.modules.moral.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.modules.moral.entity.Month;
import com.orhon.smartcampus.modules.moral.mapper.GroupClassMapper;
import com.orhon.smartcampus.modules.moral.mapper.GroupMapper;
import com.orhon.smartcampus.modules.moral.mapper.MonthMapper;
import com.orhon.smartcampus.modules.moral.mapper.MonthWeekMapper;
import com.orhon.smartcampus.modules.moral.service.IGroupService;
import com.orhon.smartcampus.modules.moral.service.IMonthService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class MonthServiceImpl extends BaseServiceImpl<MonthMapper, Month>implements IMonthService {
    @Autowired
    private MonthMapper monthMapper;
    @Autowired
    private MonthWeekMapper monthWeekMapper;
    @Autowired
    private IMonthService service;
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        Month month = JSONObject.parseObject(JSONObject.toJSONString(maps), Month.class);
        boolean save = service.save(month);
        if (save) {
            Integer month_id = month.getId();
            JSONArray week_ids  = (JSONArray) maps.get("week_id");
            for (Object week_id : week_ids) {
                monthMapper.inserts(month_id,(Integer)week_id);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public R updates(HashMap<String, Object> maps) {
        Month month = JSONObject.parseObject(JSONObject.toJSONString(maps), Month.class);
        boolean save = service.updateById(month);
        if (save) {
            Integer month_id = month.getId();
            monthMapper.deletes(month_id);
            JSONArray week_ids  = (JSONArray) maps.get("week_id");
            for (Object week_id : week_ids) {
                monthMapper.inserts(month_id,(Integer)week_id);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }
}
