package com.orhon.smartcampus.modules.security.service.impl;

import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.security.entity.Devices;
import com.orhon.smartcampus.modules.security.mapper.DevicesMapper;
import com.orhon.smartcampus.modules.security.service.IDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class DevicesServiceImpl extends BaseServiceImpl<DevicesMapper, Devices> implements IDevicesService {

    @Autowired
    private InfoService infoService;
    @Autowired
    DevicesMapper devicesMapper;

    @Override
    @ResponseBody
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertDevices(HashMap<String, Object> maps) {
        Integer school_id = infoService.getCurrentUserSchoolID();
        Long user_id = infoService.getCurrentLoginUserId();
        Boolean save = devicesMapper.insertDevices(school_id, user_id, maps);
        if (save) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String updateDevices(HashMap<String, Object> maps) {
        Long user_id = infoService.getCurrentLoginUserId();
        Boolean update = devicesMapper.updateDevices(user_id, maps);
        if (update) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }
}
