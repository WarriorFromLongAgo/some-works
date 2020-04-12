package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OsQuestionLibraryEntity;
import com.orhonit.modules.generator.vo.QuestionLibraryVo;

import java.util.Map;


/**
 * 题库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 10:58:55
 */
public interface OsQuestionLibraryService extends IService<OsQuestionLibraryEntity> {
   
    PageUtils queryPage(Map<String, Object> params);
    //删除
    void removeByIds(Long[] Ids) ;
    //保存
    public void save(OsQuestionLibraryEntity config);
    //修改
	void updateOs(OsQuestionLibraryEntity config);
	//删除题库同时删除底下所有试题
	void deleteAllOsQuestions(String id);

	
	public QuestionLibraryVo getLibraryQuestionCount(String libraryId);
}

