package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.Rules;
import com.orhon.smartcampus.modules.moral.mapper.BasicMapper;
import com.orhon.smartcampus.modules.moral.mapper.RulesMapper;
import com.orhon.smartcampus.modules.moral.service.IRulesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目细则表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class RulesServiceImpl extends BaseServiceImpl<RulesMapper, Rules>implements IRulesService {
    @Autowired
    private RulesMapper rulesMapper;

    @Override
    public List<HashMap<String, Object>> getRule(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = rulesMapper.getRule(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getRule(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return rulesMapper.getRulepage(maps,dto);
    }
}
