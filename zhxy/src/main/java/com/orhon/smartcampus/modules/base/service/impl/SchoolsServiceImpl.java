package com.orhon.smartcampus.modules.base.service.impl;

import com.orhon.smartcampus.modules.base.entity.Schools;
import com.orhon.smartcampus.modules.base.mapper.SchoolsMapper;
import com.orhon.smartcampus.modules.base.service.ISchoolsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学校 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class SchoolsServiceImpl extends BaseServiceImpl<SchoolsMapper, Schools>implements ISchoolsService {

}
