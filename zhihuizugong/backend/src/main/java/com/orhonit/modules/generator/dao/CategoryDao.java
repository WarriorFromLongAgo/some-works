package com.orhonit.modules.generator.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CategoryEntity;
import com.orhonit.modules.generator.vo.NewsModelTreeVo;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * 栏目类别表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-12 14:32:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

	//栏目列表
	List<CategoryEntity> getCategoryList(@Param("beginLimit") int beginLimit,@Param("limit") int limit);

	//逻辑删除栏目
	@Update("update tb_category set delete_flag = 1 where  cat_id = #{catId}")
	void deleteCategory(@Param("catId")Integer catId);

	List<NewsModelTreeVo> getNewsModelTree();
	
}
