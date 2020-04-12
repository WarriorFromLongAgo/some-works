package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.Item;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IItemService extends BaseService<Item> {
    List<HashMap<String, Object>> getItem(HashMap<String, Object> maps);
    List<HashMap<String, Object>> getItem(HashMap<String, Object> maps, PageDto dto);

}
