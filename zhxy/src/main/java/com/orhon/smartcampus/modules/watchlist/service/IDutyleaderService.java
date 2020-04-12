package com.orhon.smartcampus.modules.watchlist.service;

import com.orhon.smartcampus.modules.watchlist.entity.Dutyleader;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 值班领导、环节干部 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IDutyleaderService extends BaseService<Dutyleader> {
    List<HashMap<String, Object>> getLeader(HashMap<String, Object> maps, PageDto dto);

    List getLeader(HashMap<String, Object> maps);
}
