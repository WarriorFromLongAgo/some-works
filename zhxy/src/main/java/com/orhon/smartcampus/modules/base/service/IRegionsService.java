package com.orhon.smartcampus.modules.base.service;

import com.orhon.smartcampus.modules.base.entity.Regions;

import java.util.HashMap;

import com.orhon.smartcampus.framework.service.BaseService;

/**
 * <p>
 * 地区信息 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IRegionsService extends BaseService<Regions> {

	HashMap<String, Object>  getRegionsList();
}
