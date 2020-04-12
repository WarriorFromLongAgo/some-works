package com.orhonit.modules.generator.app.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppFsoulFileDao;
import com.orhonit.modules.generator.app.entity.AppFsoulFileEntity;
import com.orhonit.modules.generator.app.service.AppFsoulFileService;


/**
 * 文件上传
 * @author YaoSC
 *
 */
@Service("AppFsoulFileService")
public class AppFsoulFileServiceImpl extends ServiceImpl<AppFsoulFileDao,AppFsoulFileEntity>implements AppFsoulFileService{

	@Override
	public void insertFsoulFileEntity(Integer fileType) {
		/*
		 * AppFsoulFileEntity entity = new AppFsoulFileEntity(); switch(fileType){ case
		 * CommonParameters.AppEverBodyShareL.file_picture: entity break; case
		 * CommonParameters.AppEverBodyShareL.file_video: //...; break; ... case
		 * default: //...; break; }
		 */
		
	}

}
