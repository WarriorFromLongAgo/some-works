package com.orhonit.ole.enforce.controller.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.ps.ArticleDTO;
import com.orhonit.ole.enforce.dto.ps.CheckPersonDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.service.article.ArticleService;
import com.orhonit.ole.enforce.utils.PageList;

/**
 * 公示系统
 * 制度公开相关控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/ps/article")
public class PsArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 文章类型
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/typeList")
	public Result<List<ArticleDTO>> typeList() {
		List<ArticleDTO> typeList = this.articleService.getTypeList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, typeList);
	}
	
	/**
	 * 根据文章类型查询左边部门树
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/deptList")
	public Result<List<ArticleDTO>> deptList(@RequestParam(value="type",required = false) String type) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		List<ArticleDTO> deptList = this.articleService.getDeptList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, deptList);
	}
	
	/**
	 * 文章列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/list")
	public Result<PageList<ArticleDTO>> list(
			@RequestParam(value="type",required = false) String type,
			@RequestParam(value="area", required = false) String area,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		ArticleDTO articleDTO =new ArticleDTO();
		if(currentPage != 0 & pageSize != 0){
			articleDTO.setCurrentPage(currentPage);
			articleDTO.setPageSize(pageSize);
			if(type != "" & type != null){
				articleDTO.setType(type);
			}
			if(area != "" & area != null){
				articleDTO.setArea(area);
			}
		}else{
			if(type != "" & type != null){
				articleDTO.setType(type);
			}
			if(area != "" & area != null){
				articleDTO.setArea(area);
			}
			articleDTO.setCurrentPage(1);
			articleDTO.setPageSize(20);
		}
		PageList<ArticleDTO> list = this.articleService.list(articleDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	
	
	/**
	 * 文章详情
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getArticle")
	public Result<ArticleDTO> getArticle(
			@RequestParam(value="id",defaultValue="",required=false) String id){
		if(id == "" && id == null){
			return ResultUtil.toResponse(ResultCode.ERROR);
		}
		
		Map<String, Object> params = new HashMap<>();
		int ids = Integer.parseInt(id);
		params.put("id", ids);
		
		ArticleDTO articleDTO = this.articleService.getArticle(params);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, articleDTO);
		
	}
	
}
