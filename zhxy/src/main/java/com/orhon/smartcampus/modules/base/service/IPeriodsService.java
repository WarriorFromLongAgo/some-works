package com.orhon.smartcampus.modules.base.service;

import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 学段信息 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IPeriodsService extends BaseService<Periods> {

    HashMap<Long, Object> getPeriodList();

}
