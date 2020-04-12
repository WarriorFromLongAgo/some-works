package com.orhon.smartcampus.modules.student.service;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.student.entity.EclassRecords;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bao
 */
public interface IEclassRecordsService extends BaseService<EclassRecords> {

    String eclassRecordsSave(HashMap<String, Object> maps);

}
