package com.orhonit.modules.generator.dao;

import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgMeetRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会议通知和记录总结中间表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 17:17:20
 */
@Mapper
public interface ZgMeetRecordDao extends BaseMapper<ZgMeetRecordEntity> {

	void save(ZgMeetRecordEntity zgMeetRecord);

    List<ZgMeetPeopleEntity> findPeoList(Long userId);

    List<ZgMeetRecordEntity> findReListCount(@Param("params") Map<String, Object> params);

    List<ZgMeetRecordEntity> findReList(@Param("params") Map<String, Object> params);
}
