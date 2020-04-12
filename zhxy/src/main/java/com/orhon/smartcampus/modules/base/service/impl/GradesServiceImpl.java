package com.orhon.smartcampus.modules.base.service.impl;

import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.modules.base.mapper.GradesMapper;
import com.orhon.smartcampus.modules.base.mapper.RegionsMapper;
import com.orhon.smartcampus.modules.base.service.IGradesService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 年级 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class GradesServiceImpl extends BaseServiceImpl<GradesMapper, Grades>implements IGradesService {

}
