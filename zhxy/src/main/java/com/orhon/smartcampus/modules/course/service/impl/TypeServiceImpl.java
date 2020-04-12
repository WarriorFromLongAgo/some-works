package com.orhon.smartcampus.modules.course.service.impl;

import com.orhon.smartcampus.modules.course.entity.Type;
import com.orhon.smartcampus.modules.course.mapper.TypeMapper;
import com.orhon.smartcampus.modules.course.service.ITypeService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程级别表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class TypeServiceImpl extends BaseServiceImpl<TypeMapper, Type>implements ITypeService {

}
