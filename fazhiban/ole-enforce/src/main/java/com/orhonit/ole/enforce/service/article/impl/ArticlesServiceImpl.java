package com.orhonit.ole.enforce.service.article.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.ArticleDao;
import com.orhonit.ole.enforce.dto.ps.ArticleDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.service.article.ArticleService;
import com.orhonit.ole.enforce.utils.PageList;

/**
 * 制度公开服务类
 * @author liuzhi
 */
@Service
public class ArticlesServiceImpl  implements ArticleService{
	
	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public List<ArticleDTO> getDeptList(Map<String, Object> paramMap) {
		return this.articleDao.getDeptList(paramMap);
	}
	
	@Override
	public List<ArticleDTO> getTypeList() {
		return this.articleDao.getTypeList();
	}
	
	@Override
	public PageList<ArticleDTO> list(ArticleDTO articleDTO) {
		PageList<ArticleDTO> page = new PageList<ArticleDTO>();
        int pageCount = articleDao.getNum(articleDTO);//得到总条数
        page = initPage(page, pageCount,articleDTO);
        List<ArticleDTO> message= articleDao.list(articleDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	
	private PageList<ArticleDTO> initPage(PageList<ArticleDTO> page, int pageCount,
			ArticleDTO articleDTO) {
        page.setTotalRecord(pageCount);
        page.setCurrentPage(articleDTO.getCurrentPage());
        page.setPageSize(articleDTO.getPageSize());
        articleDTO.setStartIndexEndIndex();
        return page;    
    } 
	
	@Override
	public ArticleDTO getArticle(Map<String, Object> paramMap) {
		return this.articleDao.getArticle(paramMap);
	}
}
