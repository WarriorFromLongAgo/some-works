package com.orhon.smartcampus.modules.base.service;

import com.orhon.smartcampus.modules.base.entity.Subjects;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;

/**
 * <p>
 * 学科 服务类
 * </p>
 *
 * @author Orhon
 */
public interface ISubjectsService extends BaseService<Subjects> {

    R subjectToGrade(HashMap<String, Object> maps);
    R subjectToPeriod(HashMap<String, Object> maps);
}
