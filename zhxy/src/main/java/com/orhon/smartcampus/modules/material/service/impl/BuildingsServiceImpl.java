package com.orhon.smartcampus.modules.material.service.impl;

import com.orhon.smartcampus.modules.material.entity.Buildings;
import com.orhon.smartcampus.modules.material.mapper.BuildingsMapper;
import com.orhon.smartcampus.modules.material.service.IBuildingsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 楼宇表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class BuildingsServiceImpl extends BaseServiceImpl<BuildingsMapper, Buildings>implements IBuildingsService {

}
