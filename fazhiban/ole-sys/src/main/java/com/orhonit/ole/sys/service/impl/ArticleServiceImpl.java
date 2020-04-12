package com.orhonit.ole.sys.service.impl;

import java.util.Date;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.dto.ArticleEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.ArticleRepository;
import com.orhonit.ole.sys.service.ArticleService;
import com.orhonit.ole.sys.utils.UserUtil;


/**
 * PC法制公开相关
 * 
 * @author wz
 *
 */

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public void save(ArticleEntity article) {
		User user = UserUtil.getCurrentUser();
		article.setCreateBy(user.getUsername());
		article.setCreateDate(new Date());
		article.setCreateName(user.getNickname());
		article.setUpdateBy(user.getUsername());
		article.setUpdateDate(new Date());
		article.setUpdateName(user.getNickname());
		
		this.articleRepository.save(article);
	}
	
	@Override
	public ArticleEntity findOne(String id) {
		return this.articleRepository.findOne(id);
	}
	
	@Override
	public void updateArticle(ArticleEntity article) {
		this.articleRepository.save(article);
	}
	
	@Override
	public Page<ArticleEntity> getArticleList(Map<String, Object> params, Integer start, Integer length) {
		
		PageRequest request = new PageRequest(start/length ,length);
		
		Specification<ArticleEntity> spec = new Specification<ArticleEntity>() {

			@SuppressWarnings("unused")
			@Override
			public Predicate toPredicate(Root<ArticleEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				Predicate result = null;
				
				if ( params.get("title") != null && !"".equals(params.get("title").toString())) {
					Path<String> title = root.get("title");   
					Predicate pname = cb.like(title, "%" + params.get("title") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				if ( params.get("area") != null && !"".equals(params.get("area").toString())) {
					Path<String> area = root.get("area");   
					Predicate pname = cb.like(area, "%" + params.get("area") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				if ( params.get("type") != null && !"".equals(params.get("type").toString())) {
					Path<String> type = root.get("type");   
					Predicate pname = cb.like(type, "%" + params.get("type") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				return result;
			}
			
		};
		
		return this.articleRepository.findAll(spec, request);
	}
	
	/**
	 * 删除文章配置
	 * @return
	 */
	@Override
	public void delArticle(String id) {
		this.articleRepository.delete(id);
	}

}
