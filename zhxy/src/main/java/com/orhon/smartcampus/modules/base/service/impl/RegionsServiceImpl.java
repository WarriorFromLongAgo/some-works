package com.orhon.smartcampus.modules.base.service.impl;

import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.modules.base.mapper.RegionsMapper;
import com.orhon.smartcampus.modules.base.service.IRegionsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 地区信息 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class RegionsServiceImpl extends BaseServiceImpl<RegionsMapper, Regions>implements IRegionsService {

	@Autowired
	private RegionsMapper mapper;
	
	@Override
	public HashMap<String, Object> getRegionsList() {
		 List<Regions> regionsList = mapper.getRegionsList();
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  for (Regions regions : regionsList) {
			if(regions.getId()!=null && regions.getRegion_name()!=null) {
				map.put(regions.getId().toString(),regions);
			}
		}
		return map;
	}
}
