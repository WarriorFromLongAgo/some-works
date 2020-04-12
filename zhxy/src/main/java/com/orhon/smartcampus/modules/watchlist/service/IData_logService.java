package com.orhon.smartcampus.modules.watchlist.service;

import com.orhon.smartcampus.modules.watchlist.entity.Data_log;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 数据添加 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IData_logService extends BaseService<Data_log> {

    List<HashMap<String, Object>> getDataLog(HashMap<String, Object> maps, PageDto dto);

    List getDataLog(HashMap<String, Object> maps);

    R inserts(HashMap<String, Object> maps);

    R updates(HashMap<String, Object> maps);
}
