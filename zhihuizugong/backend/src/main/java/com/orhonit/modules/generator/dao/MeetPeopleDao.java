package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.MeetPeopleEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 参加人员表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-12 15:00:48
 */
@Mapper
public interface MeetPeopleDao extends BaseMapper<MeetPeopleEntity> {

    @Select("select \n" + 
    		"a.people_id,\n" + 
    		"a.meet_id,\n" + 
    		"a.user_id,\n" + 
    		"ser.user_true_name AS userName,\n" + 
    		"a.people_need_meet,\n" + 
    		"a.people_is_read,\n" + 
    		"a.people_is_signin,\n" + 
    		"a.people_join,\n" + 
    		"a.dept_id,\n" + 
    		"ser.mobile AS mobilePhone,\n" + 
    		"a.station_id,\n" + 
    		"a.people_lve_msg\n" + 
    		"from tb_meet_people a\n" + 
    		"left join sys_user ser on a.user_id = ser.user_id\n" + 
    		"where a.meet_id=#{meetId}")
    List<MeetPeopleEntity> getById(@Param("meetId") String meetId);

    @Delete("delete from tb_meet_people where meet_id = #{meetId}")
    int deleteByMeetId(String meetId);
}
