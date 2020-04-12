package com.orhonit.modules.generator.service;

import com.orhonit.modules.generator.entity.ZgPortrayalValueEntity;
import com.orhonit.modules.generator.vo.ZgPortrayalValueVo;

import java.util.List;
import java.util.Map;

public interface ZgPortrayalValueService {

    Map<String, List<ZgPortrayalValueEntity>> findPortrayal(Long userId);

    Map<String, List<ZgPortrayalValueEntity>> findPortrayalDept(Integer lowerId);

    Map<String, List<ZgPortrayalValueVo>> findPortrayalSelfThirteen(Long userId);
}
