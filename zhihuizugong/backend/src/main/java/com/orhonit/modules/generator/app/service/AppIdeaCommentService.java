package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentEntity;
import com.orhonit.modules.generator.app.vo.AppIdeaCommentVO;

/**
  * 我为组工出点子 评论
 * @author YaoSC
 *
 */
public interface AppIdeaCommentService extends IService<AppIdeaCommentVO>{
	
	/**
	  * 评论列表
	 * @param params
	 * @return
	 */
	PageUtils commentPage(Map<String, Object> params);
	
	/**
	  * 单条评论和他的评论回复
	 * @param params
	 * @return
	 */
	AppIdeaCommentVO newComAndReply(Map<String, Object> params);
	/**
	 * 评论添加
	 * @param AppIdeaCommentVO
	 */
	void insertideaComment(AppIdeaCommentEntity entity);

}
