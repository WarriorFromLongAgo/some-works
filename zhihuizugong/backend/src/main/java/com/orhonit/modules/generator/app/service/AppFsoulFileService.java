package com.orhonit.modules.generator.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.app.entity.AppFsoulFileEntity;

/**
  *  组工之魂 文件上传
 * @author YaoSC
 *
 */
public interface AppFsoulFileService extends IService<AppFsoulFileEntity>{
	
	
	void insertFsoulFileEntity(Integer fileType);

}
