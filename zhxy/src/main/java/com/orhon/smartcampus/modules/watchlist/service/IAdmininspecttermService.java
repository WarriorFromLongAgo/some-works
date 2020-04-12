package com.orhon.smartcampus.modules.watchlist.service;

import com.orhon.smartcampus.modules.watchlist.entity.Admininspectterm;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政值班检查项 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAdmininspecttermService extends BaseService<Admininspectterm> {

    List<HashMap<String, Object>> getTerm(HashMap<String, Object> maps, PageDto dto);

    List getTerm(HashMap<String, Object> maps);
}
