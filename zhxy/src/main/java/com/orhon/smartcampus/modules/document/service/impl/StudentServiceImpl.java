package com.orhon.smartcampus.modules.document.service.impl;

import com.orhon.smartcampus.modules.document.entity.Student;
import com.orhon.smartcampus.modules.document.mapper.StudentMapper;
import com.orhon.smartcampus.modules.document.service.IStudentService;
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
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student>implements IStudentService {

}
