package com.orhonit.modules.generator.service;



import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;

/**
 * 参会人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 16:28:47
 */
public interface ZgMeetPeopleService extends IService<ZgMeetPeopleEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void save(ZgMeetPeopleEntity zgMeetPeople);
	
	List<ZgMeetPeopleEntity> findPeo(String meetId,Long userId);

    List<Map<String,Object>> findJoinMeetList(Long userId);
    
    void updateReadType(String meetId,Integer userId);  //已读 更改状态
}

