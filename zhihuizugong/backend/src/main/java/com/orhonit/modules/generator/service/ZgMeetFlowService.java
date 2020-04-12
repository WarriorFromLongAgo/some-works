package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgMeetFlowEntity;

import java.util.Map;

/**
 * 会议流程
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-17 10:32:02
 */
public interface ZgMeetFlowService extends IService<ZgMeetFlowEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgMeetFlowEntity zgMeetFlow);
}

