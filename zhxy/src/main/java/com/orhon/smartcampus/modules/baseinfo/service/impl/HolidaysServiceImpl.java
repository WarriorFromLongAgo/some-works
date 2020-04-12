package com.orhon.smartcampus.modules.baseinfo.service.impl;

import com.orhon.smartcampus.modules.baseinfo.entity.Holidays;
import com.orhon.smartcampus.modules.baseinfo.mapper.HolidaysMapper;
import com.orhon.smartcampus.modules.baseinfo.service.IHolidaysService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 节假日管理 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class HolidaysServiceImpl extends BaseServiceImpl<HolidaysMapper, Holidays>implements IHolidaysService {

}
