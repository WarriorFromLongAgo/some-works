package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.entity.Basic;
import com.orhon.smartcampus.modules.moral.mapper.BasicMapper;
import com.orhon.smartcampus.modules.moral.service.IBasicService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class BasicServiceImpl extends BaseServiceImpl<BasicMapper, Basic>implements IBasicService {
    @Autowired
    private InfoService infoService;

    @Autowired
    private BasicMapper basicMapper;

    @Override
    public List<HashMap<String, Object>> getBase(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = basicMapper.getBase(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getBase(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return basicMapper.getBasepage(maps,dto);
    }
}
