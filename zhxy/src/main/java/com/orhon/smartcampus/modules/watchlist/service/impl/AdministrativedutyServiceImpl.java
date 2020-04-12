package com.orhon.smartcampus.modules.watchlist.service.impl;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.moral.mapper.BasicMapper;
import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
import com.orhon.smartcampus.modules.watchlist.mapper.AdministrativedutyMapper;
import com.orhon.smartcampus.modules.watchlist.service.IAdministrativedutyService;
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
public class AdministrativedutyServiceImpl extends BaseServiceImpl<AdministrativedutyMapper, Administrativeduty>implements IAdministrativedutyService {
    @Autowired
    private InfoService infoService;

    @Autowired
    private AdministrativedutyMapper administrativedutyMapper;

    @Override
    public List<HashMap<String, Object>> getDuty(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = administrativedutyMapper.getDuty(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getDuty(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return administrativedutyMapper.getDutypage(maps,dto);
    }
}
