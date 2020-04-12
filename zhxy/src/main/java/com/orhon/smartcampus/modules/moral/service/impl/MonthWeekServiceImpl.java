package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.MonthWeek;
import com.orhon.smartcampus.modules.moral.mapper.MonthWeekMapper;
import com.orhon.smartcampus.modules.moral.service.IMonthWeekService;
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
public class MonthWeekServiceImpl extends BaseServiceImpl<MonthWeekMapper, MonthWeek>implements IMonthWeekService {

}
