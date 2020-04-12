package com.orhon.smartcampus.modules.document.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.document.entity.Circulation;
import com.orhon.smartcampus.modules.document.entity.Examine;
import com.orhon.smartcampus.modules.document.entity.Teacher;
import com.orhon.smartcampus.modules.document.mapper.CirculationMapper;
import com.orhon.smartcampus.modules.document.mapper.ExamineMapper;
import com.orhon.smartcampus.modules.document.mapper.TeacherMapper;
import com.orhon.smartcampus.modules.document.service.ICirculationService;
import com.orhon.smartcampus.modules.document.service.IExamineService;
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
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ExamineServiceImpl extends BaseServiceImpl<ExamineMapper, Examine>implements IExamineService {
    @Autowired
    private ExamineMapper examineMapper;
    @Autowired
    private InfoService infoService;
    @Autowired
    private IExamineService service;
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = examineMapper.getDocument(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return examineMapper.getDocumentpage(maps,dto);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R examine(HashMap<String, Object> maps) {
        Integer id = (Integer) maps.get("id");
        Integer document_id = (Integer) maps.get("document_id");
        Integer status = (Integer) maps.get("status");
        Boolean save = examineMapper.updates(id,status);
        if(save){
            if(status==3){
                Integer examine_user = (Integer) maps.get("examine_user");
                Integer parent_id = id;
                examineMapper.inserts(document_id,examine_user,parent_id);
            }
            //审核成功并下发
            if(status==1){
                Integer department_id = (Integer) maps.get("department_id");
                JSONArray user_ids = (JSONArray) maps.get("user_ids");
                JSON content = (JSON) maps.get("content");
                Integer from_user_id = (Integer)infoService.getCurrentUser().get("id");
                for (Object user_id: user_ids) {
                    teacherMapper.inserts(department_id,user_id,from_user_id,document_id,content);
                }
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }
}
