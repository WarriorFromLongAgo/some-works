package com.orhon.smartcampus.modules.watchlist.service.impl;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.watchlist.entity.Dutyleader;
import com.orhon.smartcampus.modules.watchlist.mapper.AdministrativedutyMapper;
import com.orhon.smartcampus.modules.watchlist.mapper.DutyleaderMapper;
import com.orhon.smartcampus.modules.watchlist.service.IDutyleaderService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 值班领导、环节干部 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class DutyleaderServiceImpl extends BaseServiceImpl<DutyleaderMapper, Dutyleader>implements IDutyleaderService {
    @Autowired
    private InfoService infoService;

    @Autowired
    private DutyleaderMapper dutyleaderMapper;

    @Override
    public List<HashMap<String, Object>> getLeader(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = dutyleaderMapper.getLeader(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getLeader(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return dutyleaderMapper.getLeaderpage(maps,dto);
    }
}
