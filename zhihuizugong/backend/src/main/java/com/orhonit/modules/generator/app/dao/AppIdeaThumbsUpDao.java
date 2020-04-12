package com.orhonit.modules.generator.app.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppIdeaThumbsUpEntity;


/**
  * 为组工出点子 点赞
 * @author YaoSC
 *
 */
@Mapper
public interface AppIdeaThumbsUpDao extends BaseMapper<AppIdeaThumbsUpEntity>{
	
	/**
	  * 点赞-1
	 * @param thumbsupId
	 */
    @Delete("DELETE FROM idea_thumbs_up WHERE thumbs_up_id=#{thumbsupId}")
    void deleteZan(Integer thumbsupId);

}
