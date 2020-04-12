package com.orhonit.modules.generator.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.OsQuestionDao;
import com.orhonit.modules.generator.entity.OsQuestionEntity;
import com.orhonit.modules.generator.service.OsQuestionService;


@Service("osQuestionService")
public class OsQuestionServiceImpl extends ServiceImpl<OsQuestionDao, OsQuestionEntity> implements OsQuestionService {
	
	@Autowired
	OsQuestionService service;
	@Autowired
	OsQuestionDao dao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if(!params.containsKey("libraryId")) {
    		 Page<OsQuestionEntity> page = this.selectPage(
    	        		new Query<OsQuestionEntity>(params).getPage(),
    	                new EntityWrapper<OsQuestionEntity>()
    	                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
    	                .orderDesc(Arrays.asList(new String[] {"create_time"}))
    	        );
    	        page.setTotal(this.selectCount(new EntityWrapper<OsQuestionEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
    	        return new PageUtils(page);
    	}else {
    		 String libraryId = params.get("libraryId").toString();
    		 Page<OsQuestionEntity> page = this.selectPage(
 	        		new Query<OsQuestionEntity>(params).getPage(),
 	                new EntityWrapper<OsQuestionEntity>()
 	               .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
 	               .and()
 	               .eq(StringUtils.isNotBlank(libraryId), "library_id", libraryId)
 	               .orderDesc(Arrays.asList(new String[] {"create_time"}))
 	        );
 	        page.setTotal(this.selectCount(new EntityWrapper<OsQuestionEntity>()
 	        		.eq("is_del", CommonParameters.isDel.IS_DEL_NO)
 	        		.and()
 	        		.like(StringUtils.isNotBlank(libraryId), "library_id", libraryId)));
 	        return new PageUtils(page);
    	}
    	
    }

    @Override
	public void removeByIds(Long[] Ids) {
		this.deleteBatchIds(Arrays.asList(Ids));
	}

	@Override
	public void save(OsQuestionEntity config) {
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		config.setCreateTime(new Date());
		config.setUpdateTime(new Date());
		config.setQuestionNo(uuid);
		this.insert(config);
		
	}
	
	

	/**
	 * 查询题库下的例题
	 * @param LibraryId
	 * @return
	 */
	public List<OsQuestionEntity> selectByLibraryId(String LibraryId){
		return service.selectByLibraryId(LibraryId);
	}

	@Override
	public void deleteOsQuestion(String questionNo) {
		OsQuestionEntity entity = new OsQuestionEntity();
		entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
		entity.setUpdateTime(new Date());
		dao.update(entity, new EntityWrapper<OsQuestionEntity>().eq("question_no", questionNo));
		//dao.deleteOsQuetion(questionNo);
	}
}