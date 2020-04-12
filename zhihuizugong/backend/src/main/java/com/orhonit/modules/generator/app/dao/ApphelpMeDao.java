package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.ApphelpMeEntity;
import com.orhonit.modules.generator.app.vo.AppHelpMeVO;

/**
 * 发帖信息 
 * Title: ApphelpMeDao.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月13日
 */
@Mapper
public interface ApphelpMeDao extends BaseMapper<AppHelpMeVO>{

	
	@Delete("DELETE FROM please_help_me WHERE help_me_id=#{helpMeId}")
	void deleteId(Integer helpMeId);
	
	//修改
	void updateHelpMe(ApphelpMeEntity entity);
	
	//列表&详情
	List<AppHelpMeVO>selectListAndUserName(
			@Param("helpMeId")int helpMeId,@Param("helpTitle")String helpTitle,
			@Param("beginLimit")int beginLimit,
			@Param("limit")int limit);
	
	@Select("select help_thumbs_up FROM please_help_me where help_me_id=#{helpMeId}")
	int countZan(@Param("helpMeId")Integer helpMeId);
	@Update("update please_help_me set help_thumbs_up =#{helpThumbsUp} WHERE help_me_id=#{helpMeId}")
	void updateZqn(@Param("helpThumbsUp")int helpThumbsUp,@Param("helpMeId")int helpMeId);
	
	
	/**
	 * 发布一条帖子
	 * @param entity
	 */
	@Insert("INSERT INTO please_help_me\r\n" + 
			"(\r\n" + 
			"help_title,help_content,help_create_time,help_update_time,help_user_id,help_picture,lower_id,lower_name\r\n" + 
			")VALUES(\r\n" + 
			"#{helpTitle},#{helpContent},#{helpCreateTime},#{helpUpdateTime},#{helpUserId},#{helpPicture},(SELECT lower_id FROM sys_user WHERE user_id=#{helpUserId}),(SELECT lower_name FROM sys_user WHERE user_id=#{helpUserId})\r\n" + 
			")")
	void insertHelpMe(ApphelpMeEntity entity);
	
	//统计帖子的点赞数量      help_type=1  帖子点赞
	@Select("SELECT count(*) FROM pleas_help_dianzan WHERE help_id=#{helpId} and help_type=1")
	int selectCountZan(@Param("helpId")int helpId);
	
}
