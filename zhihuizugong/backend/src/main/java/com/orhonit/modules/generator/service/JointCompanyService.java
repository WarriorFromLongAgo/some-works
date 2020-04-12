package com.orhonit.modules.generator.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.JointCompanyEntity;
import com.orhonit.modules.generator.vo.JointCompanyVO;
import com.orhonit.modules.generator.vo.UserAndOrgVO;

public interface JointCompanyService extends IService<JointCompanyEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	PageUtils jointqueryPage(Map<String,Object>params);
	
	/**
	  * 联席单位 违法违纪统计
	 * @param params
	 * @return
	 */
	//PageUtils getList(Map<String,Object>params);
	/**
	  * 查询个人信息以及所在单位名称
	 * @param userTrueName
	 * @param userNickname
	 * @return
	 */
	PageUtils userList(Map<String,Object>params);
	
	JointCompanyVO getOneJoint(Integer id);

}
