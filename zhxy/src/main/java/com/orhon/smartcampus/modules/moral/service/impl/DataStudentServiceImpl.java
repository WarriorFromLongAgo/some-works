package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.DataStudent;
import com.orhon.smartcampus.modules.moral.mapper.DataStudentMapper;
import com.orhon.smartcampus.modules.moral.service.IDataStudentService;
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
public class DataStudentServiceImpl extends BaseServiceImpl<DataStudentMapper, DataStudent>implements IDataStudentService {

}
