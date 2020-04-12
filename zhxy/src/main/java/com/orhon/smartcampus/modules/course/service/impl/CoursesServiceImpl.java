package com.orhon.smartcampus.modules.course.service.impl;

import com.orhon.smartcampus.modules.course.entity.Courses;
import com.orhon.smartcampus.modules.course.mapper.CoursesMapper;
import com.orhon.smartcampus.modules.course.service.ICoursesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程的数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class CoursesServiceImpl extends BaseServiceImpl<CoursesMapper, Courses>implements ICoursesService {

}
