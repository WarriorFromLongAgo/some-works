package com.orhonit.ole.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.EventType;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.event.AdminEvent;
import com.orhonit.ole.sys.dao.ArticlesDao;
import com.orhonit.ole.sys.model.Articles;
import com.orhonit.ole.sys.model.Articles.Status;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

	@Autowired
	private ArticlesDao articlesDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存文章")
	@RequiresPermissions("articles:add")
	public Articles saveArticles(@RequestBody Articles articles) {
		articlesDao.save(articles);
		sendMsg(articles);

		return articles;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取文章")
	@RequiresPermissions("articles:query")
	public Articles get(@PathVariable Long id) {
		return articlesDao.getById(id);
	}

	@Autowired
	private ApplicationContext applicationContext;

	private void sendMsg(Articles articles) {
		if (articles.getStatus() == Status.PUBLISH) {// 发公告通知，过滤自己
			applicationContext.publishEvent(new AdminEvent(articles, EventType.NEW_NOTICE));
		}
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改文章")
	@RequiresPermissions("articles:add")
	public Articles updateArticles(@RequestBody Articles articles) {
		articlesDao.update(articles);
		sendMsg(articles);

		return articles;
	}

	@GetMapping
	@ApiOperation(value = "文章列表")
	@RequiresPermissions("articles:query")
	public TableResponse<Articles> listArticles(TableRequest request) {
		return TableRequestHandler.<Articles> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return articlesDao.count(request.getParams());
			}
		}).listHandler(new ListHandler<Articles>() {

			@Override
			public List<Articles> list(TableRequest request) {
				return articlesDao.list(request.getParams(), request.getStart(), request.getLength());
			}
		}).build().handle(request);
	}

	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除文章")
	@RequiresPermissions(value = { "articles:del" })
	public void delete(@PathVariable Long id) {
		articlesDao.delete(id);
	}
}
