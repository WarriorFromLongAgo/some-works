package com.orhonit.ole.sys.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.orhonit.ole.sys.dto.ArticleEntity;

/**
 * PC法制公开相关
 * 
 * @author wz
 *
 */

public interface ArticleService {
	
	/**
	 * 保存json数据
	 * @param article
	 */
	void save(ArticleEntity article);
	
	/**
	 * 获取文章
	 * @param id
	 * @return
	 */
	ArticleEntity findOne(String id);
	
	/**
	 * 更新文章
	 * @param article
	 */
	void updateArticle(ArticleEntity article);
	
	/**
	 * 获取文章列表列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	Page<ArticleEntity> getArticleList(Map<String, Object> params, Integer start, Integer length);
	
	/**
	 * 删除文章配置
	 * @param id
	 * @return
	 */
	void delArticle(String id);
}
