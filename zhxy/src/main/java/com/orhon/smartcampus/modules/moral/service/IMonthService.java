package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.Month;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IMonthService extends BaseService<Month> {
    R inserts(HashMap<String, Object> maps);
    R updates(HashMap<String, Object> maps);
}
