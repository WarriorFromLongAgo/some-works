package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.MeetFileEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 会议附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-17 16:37:36
 */
@Mapper
public interface MeetFileDao extends BaseMapper<MeetFileEntity> {

	//
	@Select("select * from tb_meet_file where meet_id = #{meetId}")
	List<MeetFileEntity> getById(@Param("meetId") String meetId);

	void insertFile(MeetFileEntity Entity);

	@Delete("DELETE FROM `tb_meet_file` WHERE meet_id = #{meetId}")
	int deleteBymeetId(String meetId);

	void updateByMeetFile(MeetFileEntity meetFile);

}
