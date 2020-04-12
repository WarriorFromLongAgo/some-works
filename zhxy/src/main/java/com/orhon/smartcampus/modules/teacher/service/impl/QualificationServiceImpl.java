package com.orhon.smartcampus.modules.teacher.service.impl;

import com.orhon.smartcampus.modules.teacher.entity.Qualification;
import com.orhon.smartcampus.modules.teacher.mapper.QualificationMapper;
import com.orhon.smartcampus.modules.teacher.service.IQualificationService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class QualificationServiceImpl extends BaseServiceImpl<QualificationMapper, Qualification>implements IQualificationService {

}
