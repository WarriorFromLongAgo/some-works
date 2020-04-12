package com.orhon.smartcampus.modules.watchlist.service;

import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAdministrativedutyService extends BaseService<Administrativeduty> {

    List<HashMap<String, Object>> getDuty(HashMap<String, Object> maps, PageDto dto);

    List getDuty(HashMap<String, Object> maps);

}
