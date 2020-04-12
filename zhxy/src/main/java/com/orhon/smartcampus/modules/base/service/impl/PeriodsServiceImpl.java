package com.orhon.smartcampus.modules.base.service.impl;

import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.modules.base.mapper.GradesMapper;
import com.orhon.smartcampus.modules.base.mapper.PeriodsMapper;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 学段信息 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class PeriodsServiceImpl extends BaseServiceImpl<PeriodsMapper, Periods>implements IPeriodsService {
    @Autowired
    private PeriodsMapper mapper;
    @Override
    public HashMap<Long, Object> getPeriodList() {
        List<Periods> periodList = mapper.getPeriodList();
        HashMap<Long, Object> map = new HashMap<Long, Object>();
        for (Periods periods : periodList) {
            if(periods.getId()!=null && periods.getPeriod_name()!=null) {
                map.put(Long.valueOf(periods.getId().toString()),periods);
            }
        }
        return map;
    }
}
