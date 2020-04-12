package com.orhonit.modules.sys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.sys.dao.MyrequestDao;
import com.orhonit.modules.sys.dao.StudyAssessDao;
import com.orhonit.modules.sys.dao.StudyClassifyDao;
import com.orhonit.modules.sys.entity.MyrequestEntity;
import com.orhonit.modules.sys.entity.StudyAssessEntity;
import com.orhonit.modules.sys.entity.StudyClassifyEntity;
import com.orhonit.modules.sys.service.MyrequestService;
import com.orhonit.modules.sys.service.StudyAssessService;
import com.orhonit.modules.sys.service.StudyClassifyService;
import com.orhonit.modules.sys.vo.MyrequestEntityVo;


@Service
public class StudyClassifyServiceImpl extends ServiceImpl<StudyClassifyDao, StudyClassifyEntity> implements StudyClassifyService {
	
	@Autowired
	private StudyClassifyDao classifyDao;
	
   
}
