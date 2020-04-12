package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.entity.ScheduleJobLogEntity;
@Mapper
public interface ScheduleJobLogDao {

	ScheduleJobLogEntity queryObject(Long jobId);

	List<ScheduleJobLogEntity> queryList(@Param("params")Map<String, Object> params,@Param("start")Integer start,@Param("length")Integer length);

	int queryTotal(@Param("params")Map<String, Object> params);

	void save(ScheduleJobLogEntity log);

}
