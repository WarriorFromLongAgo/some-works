package com.orhon.smartcampus.modules.teacher.service;

import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
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
public interface IOfficeArrangeService extends BaseService<OfficeArrange> {
//获取列表
    public List<HashMap<String,Object>> getList();

    List<HashMap<String, Object>> getOfficeArrange(HashMap<String, Object> maps);
    List<HashMap<String, Object>> getOfficeArrange(HashMap<String, Object> maps, PageDto dto);
}
