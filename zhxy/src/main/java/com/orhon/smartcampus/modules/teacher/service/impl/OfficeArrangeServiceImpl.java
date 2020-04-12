package com.orhon.smartcampus.modules.teacher.service.impl;

import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.mapper.OfficeArrangeMapper;
import com.orhon.smartcampus.modules.teacher.mapper.TInformationMapper;
import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeService;
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
public class OfficeArrangeServiceImpl extends BaseServiceImpl<OfficeArrangeMapper, OfficeArrange>implements IOfficeArrangeService {

    @Autowired
    private OfficeArrangeMapper mapper;
    @Override
    public List<HashMap<String,Object>> getList() {
        List<HashMap<String,Object>> ret = this.baseMapper.getInfos();
        return ret;
    }
    public List<HashMap<String, Object>> getOfficeArrange(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> officeArrange = mapper.getOfficeArrange(maps);
        return officeArrange;
    }
    @Override
    public List<HashMap<String, Object>> getOfficeArrange(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return mapper.getOfficeArrangepage(maps,dto);
    }
}
