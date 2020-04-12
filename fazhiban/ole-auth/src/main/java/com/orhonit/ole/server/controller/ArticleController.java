package com.orhonit.ole.server.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.dto.ArticleEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.ArticleService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * PC法制公开相关
 * 
 * @author wz
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	ArticleService articleService;

	/**
	 * 将前台提交的json内容保存到数据库中
	 * @return
	 */
	@PostMapping("/save")
	public Result<Object> saveArticle(@Valid ArticleEntity article) {
		this.articleService.save(article);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	/**
	 * 修改文章
	 * @param article
	 */
	@PostMapping("/update")
	public Result<Object> updateArticle(@Valid ArticleEntity article) {
		if ( article == null || StringUtils.isEmpty(article.getId())) {
			return ResultUtil.toResponse(ResultCode.FIELD_ERROR);
		}
		ArticleEntity articleEntity = this.articleService.findOne(article.getId());
		articleEntity.setContent(article.getContent());
		articleEntity.setTitle(article.getTitle());
		articleEntity.setAuthor(article.getAuthor());
		articleEntity.setType(article.getType());
		articleEntity.setIsPs(article.getIsPs());
		articleEntity.setArea(article.getArea());
		User user = UserUtil.getCurrentUser();
		articleEntity.setUpdateBy(user.getUsername());
		articleEntity.setUpdateDate(new Date());
		articleEntity.setUpdateName(user.getUsername());
		articleService.updateArticle(articleEntity);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	/**
	 * 获取文章列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<ArticleEntity> listArticle(TableRequest request) {
		
		Page<ArticleEntity> page = this.articleService.getArticleList(request.getParams(), request.getStart(), request.getLength());
		
		return TableRequestHandler.<ArticleEntity> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return Long.valueOf(page.getTotalElements()).intValue();
			}
		}).listHandler(new ListHandler<ArticleEntity>() {

			@Override
			public List<ArticleEntity> list(TableRequest request) {
				return page.getContent().stream().map(
						e -> {
							ArticleEntity articleDTO = new ArticleEntity();
							BeanUtils.copyProperties(e, articleDTO);
							return articleDTO;
						}
				).collect(Collectors.toList());
			}
		}).build().handle(request);
	}
	
	/**
	 * 获取文章内容
	 * @param id
	 * @return
	 */
	@GetMapping("/getArticle/{id}")
	@ResponseBody
	public ArticleEntity getArticle(@PathVariable String id) {
		ArticleEntity articleEntity = this.articleService.findOne(id);
		return articleEntity;
	}
	
	/**
	 * 删除文章配置
	 * @param request
	 * @return
	 */
	@LogAnnotation
	@DeleteMapping("/delArticle/{id}")
	@ApiOperation(value = "删除文章")
	public void delArticle(@PathVariable String id) {
		this.articleService.delArticle(id);
	}
}
