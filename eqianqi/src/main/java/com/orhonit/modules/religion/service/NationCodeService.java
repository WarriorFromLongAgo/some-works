package com.orhonit.modules.religion.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.religion.entity.NationCode;


public interface NationCodeService extends IService<NationCode>{

	/**
	 * 查询所有民族代码
	 * @return
	 */
	public List<NationCode> selectNationCodeAll();
	
}
