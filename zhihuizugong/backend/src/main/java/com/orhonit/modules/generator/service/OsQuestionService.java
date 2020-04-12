package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OsQuestionEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 问题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 09:34:14
 */
public interface OsQuestionService extends IService<OsQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    //删除
    void removeByIds(Long[] Ids) ;
    //保存
    public void save(OsQuestionEntity config);
    
	/*
	 * //插入 public void insertOsQuestion(OsQuestionEntity osuestionentity);
	 */
    
    /**
	 * 查询题库下的例题
	 * @param LibraryId
	 * @return
	 */
	List<OsQuestionEntity> selectByLibraryId(@Param("LibraryId")String LibraryId);
	
	void deleteOsQuestion(String questionNo);

}

