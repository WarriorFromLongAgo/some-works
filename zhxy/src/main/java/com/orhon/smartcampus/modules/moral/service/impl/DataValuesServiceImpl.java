package com.orhon.smartcampus.modules.moral.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.*;
import com.orhon.smartcampus.modules.moral.mapper.DataStudentMapper;
import com.orhon.smartcampus.modules.moral.mapper.DataValuesMapper;
import com.orhon.smartcampus.modules.moral.mapper.MonthWeekMapper;
import com.orhon.smartcampus.modules.moral.service.IDataValuesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.moral.service.IMonthWeekService;
import com.orhon.smartcampus.modules.moral.service.IWeekService;
import com.orhon.smartcampus.modules.student.entity.EclassRecords;
import com.orhon.smartcampus.modules.student.entity.Eclass_grade_view;
import com.orhon.smartcampus.modules.student.mapper.EclassRecordsMapper;
import com.orhon.smartcampus.modules.student.service.IEclassRecordsService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class DataValuesServiceImpl extends BaseServiceImpl<DataValuesMapper, DataValues>implements IDataValuesService {
    @Autowired
    private IDataValuesService service;
    @Autowired
    private IEclassRecordsService eclassRecordsService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private EclassRecordsMapper eclassRecordsMapper;
    @Autowired
    private DataValuesMapper dataValuesMapper;
    @Autowired
    private MonthWeekMapper monthWeekMapper;
    @Autowired
    private IMonthWeekService monthWeekService;
    @Autowired
    private IWeekService weekService;

    @Autowired
    private DataStudentMapper dataStudentMapper;

    private Integer gv(Object a){
        if(ObjectUtils.allNotNull(a)){
            return (Integer) a;
        }
        return 0;
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public R studentStatics(HashMap<String, Object> maps) {
        Integer class_id  = (Integer) maps.get("class_id");
        Integer month_id  = (Integer) maps.get("month_id");
        Integer grade_id  = (Integer) maps.get("grade_id");
        Integer week_ids  = (Integer) maps.get("week_id");
        //如果班级存在，获取当前班级下的学生信息
        if(class_id !=null){
            QueryWrapper<EclassRecords> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("original_eclass",class_id).eq("status","normal");
            List<EclassRecords> list = eclassRecordsService.list(queryWrapper);
            //List<Map<String,Object>> list = e(queryWrapper);
            ArrayList<JSONObject> ret = new ArrayList<JSONObject>();
            for (Object obj: list) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
                Integer user_id = jsonObject.getIntValue("user_id");
                Integer eclass_id = jsonObject.getIntValue("original_eclass");
                JSONObject semester = JSON.parseObject(JSON.toJSONString(infoService.getCurruentUsresSemster()));
                Integer semester_id = semester.getIntValue("id");
                JSONObject school = JSON.parseObject(JSON.toJSONString(infoService.getCurrentSchoolSetting()));
                Integer school_id = school.getIntValue("school_id");
                if(month_id != null && month_id ==0){
                    //按学期统计
                    //年级层德育统计
                    jsonObject.put("grade" ,dataValuesMapper.selects_grade(user_id,semester_id));
                    jsonObject.put("school" ,dataValuesMapper.selects_school(user_id,semester_id));
                }else if( month_id != null && month_id != 0){
                    //按月份统计
                    //根据月份获取开始和结束时间。
                    QueryWrapper<MonthWeek> monthqueryWrapper = new QueryWrapper<>();
                    monthqueryWrapper.eq("month_id",month_id);
                    List<MonthWeek> monthWeeks = monthWeekService.list(monthqueryWrapper);
                    ArrayList<JSONObject> rets = new ArrayList<JSONObject>();
                    for (MonthWeek monthweek:monthWeeks) {
                        JSONObject monthweekjsonObject = JSON.parseObject(JSON.toJSONString(monthweek));
                        Integer week_id = monthweekjsonObject.getIntValue("week_id");
                        monthweekjsonObject.put("start_time" ,monthWeekMapper.start_time(week_id));
                        monthweekjsonObject.put("end_time" ,monthWeekMapper.end_time(week_id));
                        rets.add(monthweekjsonObject);
                    }
                    Integer grade_score = 0;
                    Integer school_score = 0;
                    for (Object res: rets) {
                        JSONObject resjsonObject = JSON.parseObject(JSON.toJSONString(res));
                        String start_times = resjsonObject.getString("start_time");
                        String end_times = resjsonObject.getString("end_time");
                        grade_score = grade_score + this.gv(dataValuesMapper.selects_grade_month(user_id,start_times,end_times));
                        school_score = school_score + this.gv(dataValuesMapper.selects_school_month(user_id,start_times,end_times));
                    }
                    jsonObject.put("grade" ,grade_score);
                    jsonObject.put("school" ,school_score);
                }else if(week_ids != null){
                    String start_times = monthWeekMapper.start_time(week_ids);
                    String end_times = monthWeekMapper.end_time(week_ids);
                    Integer grade_score = this.gv(dataValuesMapper.selects_grade_month(user_id,start_times,end_times));
                    Integer school_score = this.gv(dataValuesMapper.selects_school_month(user_id,start_times,end_times));
                    jsonObject.put("grade" ,grade_score);
                    jsonObject.put("school" ,school_score);
                }
                //学生姓名
                jsonObject.put("student_name" ,dataValuesMapper.student_name(user_id));
                //班级信息
                jsonObject.put("class_name" ,dataValuesMapper.class_name(eclass_id));
                //基础分
                jsonObject.put("moral_base" ,dataValuesMapper.moral_base(semester_id,school_id));
                ret.add(jsonObject);
            }
            return R.ok().put("data",ret);
        }
//        if(grade_id != null){
//            QueryWrapper<Eclass_grade_view> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("grade_id",grade_id);
//            return R.ok().put("data",queryWrapper);
//
////            QueryWrapper<EclassRecords> queryWrapper_class = new QueryWrapper<>();
////            queryWrapper.in("original_eclass",class_id).eq("status","normal");
//        }

        return R.error("参数错误");
    }
    @Override
    public List<HashMap<String, Object>> getDateValue(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = dataValuesMapper.getDateValue(maps);
        return base;
    }
    @Override
    public Object getOneDateValue(Integer id) {
        Object obj = dataValuesMapper.getOneDateValue(id);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
        jsonObject.put("student",dataStudentMapper.getStudent(jsonObject.getIntValue("id")));
        return jsonObject;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        DataValues dataValues = JSONObject.parseObject(JSONObject.toJSONString(maps), DataValues.class);
        Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
        JSONObject semester = JSON.parseObject(JSON.toJSONString(infoService.getCurruentUsresSemster()));
        Integer semester_id = semester.getIntValue("id");
        dataValues.setSchool_id(school_id);
        dataValues.setSemester_id(semester_id);
        boolean save = service.save(dataValues);
        if (save) {
            Integer moral_column_id = dataValues.getId();
            JSONArray students = (JSONArray) maps.get("student");
            for (Object student : students) {
                dataStudentMapper.inserts(moral_column_id, (Integer) student);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public R updates(HashMap<String, Object> maps) {
        DataValues dataValues = JSONObject.parseObject(JSONObject.toJSONString(maps), DataValues.class);
        boolean save = service.updateById(dataValues);
        if (save) {
            Integer id = dataValues.getId();
            dataStudentMapper.deletes(id);
            Integer moral_column_id = dataValues.getId();
            JSONArray students = (JSONArray) maps.get("student");
            for (Object student : students) {
                dataStudentMapper.inserts(moral_column_id, (Integer) student);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }

    @Override
    public List<HashMap<String, Object>> getDateValue(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return dataValuesMapper.getDateValuepage(maps,dto);
    }
}
