package com.orhonit.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.orhonit.modules.sys.dao.StudyAssessDao;

import com.orhonit.modules.sys.entity.StudyAssessEntity;

import com.orhonit.modules.sys.service.StudyAssessService;



@Service
public class StudyAssessServiceImpl extends ServiceImpl<StudyAssessDao, StudyAssessEntity> implements StudyAssessService {

	@Autowired
	private StudyAssessDao assessDao;


}
