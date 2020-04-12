package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.modules.systemctl.mapper.OrgDepartmentsMapper;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDepartmentsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.teacher.mapper.OfficeArrangeUserMapper;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class OrgDepartmentsServiceImpl extends BaseServiceImpl<OrgDepartmentsMapper, OrgDepartments>implements IOrgDepartmentsService {
    @Autowired
    private OrgDepartmentsMapper mapper;
    public List<HashMap<String, Object>> getDepartmentTeacher(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> officeArrange = mapper.getDepartmentTeacher(maps);
        return officeArrange;
    }
    @Override
    public List<HashMap<String, Object>> getDepartmentTeacher(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return mapper.getDepartmentTeacherpage(maps,dto);
    }
    @Override
    public R departmentToGrade(HashMap<String, Object> maps) {
        Integer department_id = (Integer)maps.get("department_id");
        JSONArray grade_ids = (JSONArray)maps.get("grade_id");
        List<Object> list = new ArrayList<Object>();
        if(department_id!=null&&grade_ids!=null) {
            for (Object grade_id : grade_ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("department_id", department_id.toString());
                map.put("grade_id", grade_id.toString());
                list.add(map);
            }
            mapper.departmentToGrade(list);
            return R.ok();
        }
        return R.error("参数错误！");
    }
    @Override
    public R departmentToSubject(HashMap<String, Object> maps) {
        Integer department_id = (Integer)maps.get("department_id");
        JSONArray subject_ids = (JSONArray)maps.get("subject_id");
        List<Object> list = new ArrayList<Object>();
        if(department_id!=null&&subject_ids!=null) {
            for (Object subject_id : subject_ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("department_id", department_id.toString());
                map.put("subject_id", subject_id.toString());
                list.add(map);
            }
            mapper.departmentToSubject(list);
            return R.ok();
        }
        return R.error("参数错误！");
    }
    @Override
    public R departmentToDuties(HashMap<String, Object> maps) {
        Integer department_id = (Integer)maps.get("department_id");
        JSONArray duty_ids = (JSONArray)maps.get("duty_id");
        List<Object> list = new ArrayList<Object>();
        if(department_id!=null&&duty_ids!=null) {
            for (Object duty_id : duty_ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("department_id", department_id.toString());
                map.put("duty_id", duty_id.toString());
                list.add(map);
            }
            mapper.departmentToDuties(list);
            return R.ok();
        }
        return R.error("参数错误！");
    }
}
