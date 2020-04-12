package com.orhonit.modules.generator.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgMeetFileEntity;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
/**
 * 会议通知附件
 * @author baiam
 *
 */
public interface ZgMeetFileService extends IService<ZgMeetFileEntity> {

	void saveFile(ZgMeetFileEntity zgMeetFileEntity);

	PageUtils findFile(Map<String, Object> params);

}
