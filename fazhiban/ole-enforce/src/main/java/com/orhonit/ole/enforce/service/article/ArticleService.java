package com.orhonit.ole.enforce.service.article;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.ps.ArticleDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.utils.PageList;

/**
 * 制度公开服务类
 * @author liuzhi
 *
 */
public interface ArticleService {
	/**
	 * 根据文章类型查询左边部门树
	 * @param 
	 * @return
	 */
	List<ArticleDTO> getDeptList(Map<String, Object> paramMap);
	
	/**
	 * 文章类型
	 * @param 
	 * @return
	 */
	List<ArticleDTO> getTypeList();
	
	/**
	 * 文章列表
	 * @param  
	 *  
	 * @return
	 */
	PageList<ArticleDTO> list(ArticleDTO articleDTO);
	
	/**
	 * 文章列表
	 * @param  
	 *  
	 * @return
	 */
	ArticleDTO getArticle(Map<String, Object> paramMap);
	
}
