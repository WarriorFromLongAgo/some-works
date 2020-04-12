package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.Operations;
import com.orhon.smartcampus.modules.systemctl.mapper.OperationsMapper;
import com.orhon.smartcampus.modules.systemctl.service.IOperationsService;
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
public class OperationsServiceImpl extends BaseServiceImpl<OperationsMapper, Operations>implements IOperationsService {

}
