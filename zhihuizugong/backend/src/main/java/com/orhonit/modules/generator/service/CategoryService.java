package com.orhonit.modules.generator.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.entity.CategoryEntity;
import com.orhonit.modules.generator.vo.NewsModelTreeVo;

/**
 * 栏目类别表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-12 14:32:36
 */
public interface CategoryService extends IService<CategoryEntity> {

	void deleteCategory(Integer catId);

	//查询栏目列表
	List<NewsModelTreeVo> getCategoryList();

}

