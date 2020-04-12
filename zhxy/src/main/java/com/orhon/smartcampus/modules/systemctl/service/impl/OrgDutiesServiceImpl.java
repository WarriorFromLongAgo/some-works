package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.OrgDuties;
import com.orhon.smartcampus.modules.systemctl.mapper.OrgDutiesMapper;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDutiesService;
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
public class OrgDutiesServiceImpl extends BaseServiceImpl<OrgDutiesMapper, OrgDuties>implements IOrgDutiesService {

}
