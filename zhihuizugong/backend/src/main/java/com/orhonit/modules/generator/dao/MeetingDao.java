package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.MeetingEntity;
import com.orhonit.modules.generator.vo.MeetPeopleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 支部活动表
 * 
 * @author zjw
 * @email chobitsyjwz@163.com
 * @date 2019-02-14 14:56:22
 */
@Mapper
public interface MeetingDao extends BaseMapper<MeetingEntity> {

	@Select("SELECT * from tb_meeting WHERE meet_id = #{meetId}")
	MeetingEntity getById(@Param("meetId")String meetId);

	void setMeetUsers(@Param("id")String id, @Param("userDept")Integer userDept);

	void insertMeet(MeetingEntity meeting);

	List<MeetPeopleVo> getIsSigninAndStation(@Param("meetId")String meetId);

	void updateMeetStatus(@Param("meetStatus")int meetStatus,@Param("meetId")String meetId);

	int getmeetcounts(@Param("userId")Long userId);
	
	@Delete("DELETE FROM `tb_meeting` WHERE meet_id = #{meetId}")
	void deleteBymeetId(@Param("meetId")String meetId);
	
	void updateByMeeting(MeetingEntity meeting);
	
}
