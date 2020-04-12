package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.mapper.ArrivesMapper;
import com.orhon.smartcampus.modules.student.mapper.SInformationMapper;
import com.orhon.smartcampus.modules.student.service.IArrivesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 届 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ArrivesServiceImpl extends BaseServiceImpl<ArrivesMapper, Arrives>implements IArrivesService {
    @Autowired
    private ArrivesMapper mapper;

    @Override
    public List<HashMap<String, Object>> PageListArrives(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        return mapper.PageListArrives(map);
    }
}
