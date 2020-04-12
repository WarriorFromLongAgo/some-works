package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;

/**
 * 参会人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 16:28:47
 */
@Mapper
public interface ZgMeetPeopleDao extends BaseMapper<ZgMeetPeopleEntity> {

	void save(ZgMeetPeopleEntity zgMeetPeople);

	List<ZgMeetPeopleEntity> findPeo(@Param("meetId")String meetId,@Param("userId") Long userId);

    List<Map<String,Object>> findJoinMeetList(Long userId);
    
    /**
	 * 已读更改状态
	 * @param meetId
	 */
	@Update("UPDATE zg_meet_people SET read_type=1 WHERE meet_id=#{meetId} and user_id=#{userId}")
	void updateReadType(@Param("meetId")String meetId,@Param("userId")Integer userId);
}
