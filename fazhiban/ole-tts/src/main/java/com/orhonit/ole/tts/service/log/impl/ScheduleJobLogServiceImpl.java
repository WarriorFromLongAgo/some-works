package com.orhonit.ole.tts.service.log.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.ScheduleJobLogDao;
import com.orhonit.ole.tts.entity.ScheduleJobLogEntity;
import com.orhonit.ole.tts.service.log.ScheduleJobLogService;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return scheduleJobLogDao.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> params,Integer start, Integer length) {
		return scheduleJobLogDao.queryList(params, start, length);
	}

	@Override
	public int queryTotal(Map<String, Object> params) {
		return scheduleJobLogDao.queryTotal(params);
	}

	@Override
	public void save(ScheduleJobLogEntity log) {
		scheduleJobLogDao.save(log);
	}

}
