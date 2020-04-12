package com.orhon.smartcampus.modules.material.service.impl;

import com.orhon.smartcampus.modules.material.entity.Floor;
import com.orhon.smartcampus.modules.material.mapper.FloorMapper;
import com.orhon.smartcampus.modules.material.service.IFloorService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 楼层 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class FloorServiceImpl extends BaseServiceImpl<FloorMapper, Floor>implements IFloorService {

}
