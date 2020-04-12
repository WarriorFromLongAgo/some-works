package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.dto.ps.ArticleDTO;

@Mapper
public interface ArticleDao {
	
	/**
	 * 根据文章类型查询左边部门树
	 * @param params
	 * @return
	 */
	List<ArticleDTO> getDeptList(@Param("params") Map<String, Object> params);
	
	/**
	 * 文章类型
	 * @param 
	 * @return
	 */
	List<ArticleDTO> getTypeList();
	
	/**
	 * 文章列表总数
	 * 
	 * @param 
	 * @return
	 */
	public int getNum(ArticleDTO articleDTO);
	
	/**
	 * 文章列表
	 * 
	 * @param 
	 * @return
	 */
	List<ArticleDTO> list(ArticleDTO articleDTO);
	
	/**
	 * 文章详情
	 * 
	 * @param 
	 * @return
	 */
	ArticleDTO getArticle(@Param("params") Map<String, Object> params);
}
