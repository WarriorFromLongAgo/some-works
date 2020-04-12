package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.student.entity.Eclass;
import com.orhon.smartcampus.modules.student.mapper.EclassMapper;
import com.orhon.smartcampus.modules.student.service.IEclassService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class EclassServiceImpl extends BaseServiceImpl<EclassMapper, Eclass>implements IEclassService {

	@Autowired
	private EclassMapper mapper;
	@Override
	public List<Eclass> getSchoolByEclass(HashMap<String, Object> maps) {
		// TODO Auto-generated method stub
		return mapper.getSchoolByEclass(maps);
	}

}
