package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.student.entity.Traffic;
import com.orhon.smartcampus.modules.student.mapper.TrafficMapper;
import com.orhon.smartcampus.modules.student.service.ITrafficService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学生交通信息表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class TrafficServiceImpl extends BaseServiceImpl<TrafficMapper, Traffic>implements ITrafficService {

}
