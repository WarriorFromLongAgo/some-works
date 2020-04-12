package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.student.entity.Learninfo;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.mapper.LearninfoMapper;
import com.orhon.smartcampus.modules.student.mapper.SInformationMapper;
import com.orhon.smartcampus.modules.student.service.ILearninfoService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学生学籍基本信息表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class LearninfoServiceImpl extends BaseServiceImpl<LearninfoMapper, Learninfo>implements ILearninfoService {

	@Autowired
	private LearninfoMapper mapper;

	

}
