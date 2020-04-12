package com.orhon.smartcampus.modules.systemctl.service;

import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
@Transactional
public interface IOrgDepartmentsService extends BaseService<OrgDepartments> {

    List<HashMap<String, Object>> getDepartmentTeacher(HashMap<String, Object> maps, PageDto dto);
    List<HashMap<String, Object>> getDepartmentTeacher(HashMap<String, Object> maps);
//    List<HashMap<String, Object>> departmentToGrade(List<HashMap> list);
//    List<HashMap<String, Object>> departmentToSubject(List<HashMap> list);
//    List<HashMap<String, Object>> departmentToDuties(List<HashMap> list);

    R departmentToGrade(HashMap<String, Object> maps);
    R departmentToSubject(HashMap<String, Object> maps);
    R departmentToDuties(HashMap<String, Object> maps);

}
