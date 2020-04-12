package com.orhonit.modules.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.sys.dao.StudyUserDao;
import com.orhonit.modules.sys.entity.StudyUserEntity;
import com.orhonit.modules.sys.service.StudyUserService;


@Service
public class StudyUserServiceImpl extends ServiceImpl<StudyUserDao, StudyUserEntity> implements StudyUserService {
	
	@Autowired
	private StudyUserDao studyUserDao;
	
   
}
