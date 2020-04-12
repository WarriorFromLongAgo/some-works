package com.orhon.smartcampus.modules.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.modules.base.entity.Subjects;
import com.orhon.smartcampus.modules.base.mapper.SubjectsMapper;
import com.orhon.smartcampus.modules.base.service.ISubjectsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学科 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class SubjectsServiceImpl extends BaseServiceImpl<SubjectsMapper, Subjects>implements ISubjectsService {
    @Autowired
    private SubjectsMapper mapper;
    @Override
    public R subjectToGrade(HashMap<String, Object> maps) {
        Integer subject_id = (Integer)maps.get("subject_id");
        JSONArray grade_ids = (JSONArray)maps.get("grade_id");
        List<Object> list = new ArrayList<Object>();
        if(subject_id!=null&&grade_ids!=null) {
            for (Object grade_id : grade_ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("subject_id", subject_id.toString());
                map.put("grade_id", grade_id.toString());
                list.add(map);
            }
            mapper.subjectToGrade(list);
            return R.ok();
        }
        return R.error("参数错误！");
    }
    @Override
    public R subjectToPeriod(HashMap<String, Object> maps) {
        Integer subject_id = (Integer)maps.get("subject_id");
        JSONArray period_ids = (JSONArray)maps.get("period_id");
        List<Object> list = new ArrayList<Object>();
        if(subject_id!=null&&period_ids!=null) {
            for (Object period_id : period_ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("subject_id", subject_id.toString());
                map.put("period_id", period_id.toString());
                list.add(map);
            }
            mapper.subjectToPeriod(list);
            return R.ok();
        }
        return R.error("参数错误！");
    }
}
