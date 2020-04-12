package com.orhonit.modules.generator.service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgMeetRecordEntity;

/**
 * 会议通知和记录总结中间表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 17:17:20
 */
public interface ZgMeetRecordService extends IService<ZgMeetRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void save(ZgMeetRecordEntity zgMeetRecord);

    Map<String, List<ZgMeetRecordEntity>> findInfo(Map<String, Object> params);
}

