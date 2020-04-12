package com.orhon.smartcampus.modules.base.service.impl;

import com.orhon.smartcampus.modules.base.entity.SchoolTypes;
import com.orhon.smartcampus.modules.base.mapper.SchoolTypesMapper;
import com.orhon.smartcampus.modules.base.service.ISchoolTypesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学校类型 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class SchoolTypesServiceImpl extends BaseServiceImpl<SchoolTypesMapper, SchoolTypes>implements ISchoolTypesService {

}
