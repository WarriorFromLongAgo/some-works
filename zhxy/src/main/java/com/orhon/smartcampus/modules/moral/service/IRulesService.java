package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.Rules;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目细则表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IRulesService extends BaseService<Rules> {
    List<HashMap<String, Object>> getRule(HashMap<String, Object> maps);
    List<HashMap<String, Object>> getRule(HashMap<String, Object> maps, PageDto dto);

}
