package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OverseeRecordsEntity;

import java.util.List;
import java.util.Map;

/**
 * 领导督办记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-09 11:14:32
 */
public interface OverseeRecordsService extends IService<OverseeRecordsEntity> {

    PageUtils queryPage(Map<String, Object> params);
//    领导批示列表
	List<OverseeRecordsEntity> instructionsList(String overseeId);
//	完成进度列表
	List<OverseeRecordsEntity> scheduleList(String overseeId);
//	领导点评列表
	List<OverseeRecordsEntity> reviewList(String overseeId);
}

