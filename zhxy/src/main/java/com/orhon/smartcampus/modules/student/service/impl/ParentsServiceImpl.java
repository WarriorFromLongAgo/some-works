package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.student.entity.Parents;
import com.orhon.smartcampus.modules.student.mapper.ParentsMapper;
import com.orhon.smartcampus.modules.student.service.IParentsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学生家长关系表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ParentsServiceImpl extends BaseServiceImpl<ParentsMapper, Parents>implements IParentsService {

}
