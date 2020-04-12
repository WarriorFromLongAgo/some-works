package com.orhon.smartcampus.modules.security.service;

import com.orhon.smartcampus.modules.security.entity.Devices;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IDevicesService extends BaseService<Devices> {

    String insertDevices(HashMap<String, Object> maps);

    String updateDevices(HashMap<String, Object> maps);
}
