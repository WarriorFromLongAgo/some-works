package com.orhon.smartcampus.modules.material.service.impl;

import com.orhon.smartcampus.modules.material.entity.Unit;
import com.orhon.smartcampus.modules.material.mapper.UnitMapper;
import com.orhon.smartcampus.modules.material.service.IUnitService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 单元表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class UnitServiceImpl extends BaseServiceImpl<UnitMapper, Unit>implements IUnitService {

}
