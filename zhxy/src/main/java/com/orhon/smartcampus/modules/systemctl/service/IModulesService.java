package com.orhon.smartcampus.modules.systemctl.service;

import com.orhon.smartcampus.modules.systemctl.entity.Modules;

import java.util.List;

import com.orhon.smartcampus.framework.service.BaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IModulesService extends BaseService<Modules> {

	List<Modules> getOurschool(Long school_id);

}
