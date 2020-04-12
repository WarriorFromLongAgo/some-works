package com.orhonit.modules.generator.service;



import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;

/**
 * 会议通知
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 15:05:24
 */
public interface ZgMeetInformService extends IService<ZgMeetInformEntity> {

    PageUtils findAll(Map<String, Object> params);

	void save(ZgMeetInformEntity zgMeetInform);

	List<Map<String, Object>> findAllUser(Long userId);

	List<Map<String, Object>> findMeetList();
	
    void del(String id);
    
    //未读列表
    Map unreadList(Map<String,Object>params);
    
}

