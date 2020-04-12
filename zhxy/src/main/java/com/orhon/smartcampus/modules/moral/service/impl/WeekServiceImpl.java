package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.Week;
import com.orhon.smartcampus.modules.moral.mapper.WeekMapper;
import com.orhon.smartcampus.modules.moral.service.IWeekService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class WeekServiceImpl extends BaseServiceImpl<WeekMapper, Week>implements IWeekService {

}
