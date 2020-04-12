package com.orhon.smartcampus.modules.teacher.service;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface TIInformationService extends BaseService<TInformation> {

    HashMap<String , Object> getStatistics();

    HashMap<String , Object> getWorkStatistics();

    HashMap<String , Object> getNoWorkStatistics();

    HashMap selectTeacherInfoById(Long teacherId);
    List<TInformation> getTeacherInfomation(HashMap<String, Object> maps,  Map teacher_name);
    List<TInformation> getTeacherInfomation(HashMap<String, Object> maps, PageDto dto, Map teacher_name);

	List<HashMap<String,Object>> getEclassIds(String user_id);

	HashMap<String, Object> getSexCount(String string);

	List<HashMap<String, Object>> getNationCount(String string);


    String getTeacherName(String user_id);

    List<HashMap<String, Object>> PageListTeacher(HashMap<String, Object> map);
    HashMap<String, Object> teacherDetails(Integer id);
}
