package com.orhon.smartcampus.modules.course.service.impl;

import com.orhon.smartcampus.modules.course.entity.ArrangeRecord;
import com.orhon.smartcampus.modules.course.mapper.ArrangeRecordMapper;
import com.orhon.smartcampus.modules.course.service.IArrangeRecordService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 排课记录表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ArrangeRecordServiceImpl extends BaseServiceImpl<ArrangeRecordMapper, ArrangeRecord>implements IArrangeRecordService {

}
