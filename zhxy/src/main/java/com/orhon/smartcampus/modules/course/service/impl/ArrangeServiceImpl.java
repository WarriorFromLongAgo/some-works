package com.orhon.smartcampus.modules.course.service.impl;

import com.orhon.smartcampus.modules.course.entity.Arrange;
import com.orhon.smartcampus.modules.course.mapper.ArrangeMapper;
import com.orhon.smartcampus.modules.course.service.IArrangeService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 排课 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ArrangeServiceImpl extends BaseServiceImpl<ArrangeMapper, Arrange>implements IArrangeService {

}
