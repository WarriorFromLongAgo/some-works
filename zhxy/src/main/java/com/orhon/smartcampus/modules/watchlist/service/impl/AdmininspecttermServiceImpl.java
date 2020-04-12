package com.orhon.smartcampus.modules.watchlist.service.impl;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.watchlist.entity.Admininspectterm;
import com.orhon.smartcampus.modules.watchlist.mapper.AdmininspecttermMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.AdministrativedutyMapper;
import com.orhon.smartcampus.modules.watchlist.service.IAdmininspecttermService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政值班检查项 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AdmininspecttermServiceImpl extends BaseServiceImpl<AdmininspecttermMapper, Admininspectterm>implements IAdmininspecttermService {
    @Autowired
    private InfoService infoService;

    @Autowired
    private AdmininspecttermMapper admininspecttermMapper;

    @Override
    public List<HashMap<String, Object>> getTerm(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = admininspecttermMapper.getTerm(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getTerm(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return admininspecttermMapper.getTermpage(maps,dto);
    }
}
