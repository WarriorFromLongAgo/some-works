package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.Item;
import com.orhon.smartcampus.modules.moral.mapper.BasicMapper;
import com.orhon.smartcampus.modules.moral.mapper.ItemMapper;
import com.orhon.smartcampus.modules.moral.service.IItemService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<ItemMapper, Item>implements IItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public List<HashMap<String, Object>> getItem(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = itemMapper.getItem(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getItem(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return itemMapper.getItempage(maps,dto);
    }
}
