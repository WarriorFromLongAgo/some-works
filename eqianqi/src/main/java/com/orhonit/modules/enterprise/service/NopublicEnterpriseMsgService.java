package com.orhonit.modules.enterprise.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.enterprise.entity.NopublicEnterprise;
import com.orhonit.modules.enterprise.entity.NopublicEnterpriseMsg;
import com.orhonit.modules.enterprise.model.NopublicEnterpriseMsgModel;


public interface NopublicEnterpriseMsgService  extends IService<NopublicEnterpriseMsg> {
	
	/**
	 * 通过用户id或工商联id和类型查询发布信息
	 * @param id
	 * @param type
	 * @return
	 */
	public Map<String, Object> selectNopublicEnterpriseByUserId(Long userId, String type, int pageNum, int pageSize);

}
