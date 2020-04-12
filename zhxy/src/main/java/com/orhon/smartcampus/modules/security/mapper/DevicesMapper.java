package com.orhon.smartcampus.modules.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.security.entity.Devices;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.HashMap;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface DevicesMapper extends BaseMapper<Devices> {

    Boolean insertDevices(Integer school_id, Long user_id, HashMap<String, Object> maps);

    Boolean updateDevices(Long user_id, HashMap<String, Object> maps);
}
