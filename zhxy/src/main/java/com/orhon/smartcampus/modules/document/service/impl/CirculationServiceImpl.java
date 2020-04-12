package com.orhon.smartcampus.modules.document.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.document.entity.Circulation;
import com.orhon.smartcampus.modules.document.mapper.CirculationMapper;
import com.orhon.smartcampus.modules.document.service.ICirculationService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.modules.moral.mapper.GroupClassMapper;
import com.orhon.smartcampus.modules.moral.mapper.GroupMapper;
import com.orhon.smartcampus.modules.moral.service.IGroupService;
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
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class CirculationServiceImpl extends BaseServiceImpl<CirculationMapper, Circulation>implements ICirculationService {
    @Autowired
    private CirculationMapper circulationMapper;
    @Autowired
    private ICirculationService service;
    @Autowired
    private InfoService infoService;
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        Circulation circulation = JSONObject.parseObject(JSONObject.toJSONString(maps), Circulation.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        Integer user_id = (Integer)infoService.getCurrentUser().get("id");
        circulation.setSchool_id(school_id);
        circulation.setCreated_by(user_id);
        circulation.setSemester_id((Integer)infoService.getCurruentUsresSemster().get("id"));
        boolean save = service.save(circulation);
        if (save) {
            Integer document_id = circulation.getId();
            JSONArray files = (JSONArray) maps.get("files");
            if(files != null ){
                for (Object content : files) {
                    circulationMapper.inserts(document_id, (JsonObject) content);
                }
            }
            //审核人
            Integer examine_user = (Integer) maps.get("examine_user");
            circulationMapper.insertExamineUser(document_id, (Integer) examine_user);
            return R.ok();
        }
        return R.error("参数错误！");
    }

    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = circulationMapper.getDocument(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return circulationMapper.getDocumentpage(maps,dto);
    }


}
