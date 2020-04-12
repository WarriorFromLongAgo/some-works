package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.dao.WorkPlanUploadDao;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.service.WorkPlanUploadService;
@Service
public class WorkPlanUploadServiceImpl extends ServiceImpl<WorkPlanUploadDao, ZgPlanFileEntity> implements WorkPlanUploadService{

	@Autowired
	private WorkPlanUploadDao workPlanUploadDao;
}
