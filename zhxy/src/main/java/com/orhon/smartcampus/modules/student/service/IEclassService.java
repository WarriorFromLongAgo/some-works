package com.orhon.smartcampus.modules.student.service;

import com.orhon.smartcampus.modules.student.entity.Eclass;

import java.util.HashMap;
import java.util.List;

import com.orhon.smartcampus.framework.service.BaseService;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IEclassService extends BaseService<Eclass> {

	List<Eclass> getSchoolByEclass(HashMap<String, Object> maps);

}
