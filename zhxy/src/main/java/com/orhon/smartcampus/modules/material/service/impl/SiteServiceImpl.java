package com.orhon.smartcampus.modules.material.service.impl;

import com.orhon.smartcampus.modules.material.entity.Site;
import com.orhon.smartcampus.modules.material.mapper.SiteMapper;
import com.orhon.smartcampus.modules.material.service.ISiteService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 场地 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class SiteServiceImpl extends BaseServiceImpl<SiteMapper, Site>implements ISiteService {

}
