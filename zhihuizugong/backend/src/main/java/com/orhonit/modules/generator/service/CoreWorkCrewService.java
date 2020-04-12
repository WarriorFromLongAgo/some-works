package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreWorkCrewEntity;

import java.util.List;
import java.util.Map;

/**
 * 工作队全队人员
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-10 10:11:20
 */
public interface CoreWorkCrewService extends IService<CoreWorkCrewEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreWorkCrewEntity> getByServeId(String serveId);

    void deleteByServeId(String serveId);
}

