package com.orhon.smartcampus.modules.baseinfo.service;

import com.orhon.smartcampus.modules.baseinfo.entity.Semester;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 学期表 服务类
 * </p>
 *
 * @author bao
 */
public interface ISemesterService extends BaseService<Semester> {

    //获取两个时间段之间的学期
    HashMap getSemesterViaStartAndEnd(Date startAt , Date endAt);

}
