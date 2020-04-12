package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.Basic;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;

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
public interface IBasicService extends BaseService<Basic> {
    List<HashMap<String, Object>> getBase(HashMap<String, Object> maps);
    List<HashMap<String, Object>> getBase(HashMap<String, Object> maps, PageDto dto);

}
