package com.orhonit.ole.report.service.power.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.power.PowerDao;
import com.orhonit.ole.report.dto.power.PowerAndRespDTO;
import com.orhonit.ole.report.service.power.PowerAndRespService;

@Service
public class PowerAndRespServiceImpl implements PowerAndRespService{
	
	@Autowired
	private PowerDao powerDao;
	
	@Override
	public List<PowerAndRespDTO> getCount() {
		
		return powerDao.getCount();
	}

	@Override
	public List<PowerAndRespDTO> getClassFica() {
		
		return powerDao.getClassFica();
	}

	@Override
	public List<PowerAndRespDTO> getDepartCount(String areaId) {
		
		return powerDao.getDepartCount(areaId);
	}

	@Override
	public List<PowerAndRespDTO> getDayInspection(String areaId) {
		
		return powerDao.getDayInspection(areaId);
	}

}
