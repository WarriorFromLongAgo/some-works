package com.orhon.smartcampus.modules.material.service.impl;

import com.orhon.smartcampus.modules.material.entity.Room;
import com.orhon.smartcampus.modules.material.mapper.RoomMapper;
import com.orhon.smartcampus.modules.material.service.IRoomService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 房间 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room>implements IRoomService {

}
