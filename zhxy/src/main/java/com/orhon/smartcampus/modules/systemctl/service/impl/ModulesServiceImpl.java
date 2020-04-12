package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.Modules;
import com.orhon.smartcampus.modules.systemctl.mapper.ModulesMapper;
import com.orhon.smartcampus.modules.systemctl.service.IModulesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ModulesServiceImpl extends BaseServiceImpl<ModulesMapper, Modules>implements IModulesService {

	@Autowired
	private ModulesMapper mapper;
	
	@Override
	public List<Modules> getOurschool(Long school_id) {
		// TODO Auto-generated method stub
		return mapper.getOurschool(school_id);
	}

}
