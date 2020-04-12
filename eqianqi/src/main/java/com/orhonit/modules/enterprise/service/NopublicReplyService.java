package com.orhonit.modules.enterprise.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.enterprise.entity.NopublicReply;

public interface NopublicReplyService extends IService<NopublicReply>{
	
	/**
	 *通过发布记录id查询批复信息
	 * @param id
	 * @return
	 */
	public NopublicReply selectNopublicReplyById(Long id);

}
