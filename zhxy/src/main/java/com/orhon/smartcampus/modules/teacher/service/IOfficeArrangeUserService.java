package com.orhon.smartcampus.modules.teacher.service;

import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IOfficeArrangeUserService extends BaseService<OfficeArrangeUser> {

    List<HashMap<String, Object>> getOfficeArrangeTeacher(HashMap<String, Object> maps, PageDto dto);
    List<HashMap<String, Object>> getOfficeArrangeTeacher(HashMap<String, Object> maps);
    R inserts(HashMap<String, Object> maps);

}
