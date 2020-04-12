package com.orhon.smartcampus.modules.baseinfo.service.impl;

import com.orhon.smartcampus.modules.baseinfo.entity.Semester;
import com.orhon.smartcampus.modules.baseinfo.mapper.SemesterMapper;
import com.orhon.smartcampus.modules.baseinfo.service.ISemesterService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 学期表 服务实现类
 * </p>
 *
 * @author bao
 */
@Service
public class SemesterServiceImpl extends BaseServiceImpl<SemesterMapper, Semester>implements ISemesterService {

    @Override
    public HashMap getSemesterViaStartAndEnd(Date startAt, Date endAt) {
        return this.baseMapper.getSemesterViaStartAndEnd(startAt , endAt);
    }

}
