package com.orhonit.modules.generator.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.OsExamDao;
import com.orhonit.modules.generator.entity.OsExamEntity;
import com.orhonit.modules.generator.service.OsExamService;


@Service("osExamService")
public class OsExamServiceImpl extends ServiceImpl<OsExamDao, OsExamEntity> implements OsExamService {
	
	@Autowired
	OsExamDao dao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if(!params.containsKey("examTitle")) {
    		Page<OsExamEntity> page = this.selectPage(
            		new Query<OsExamEntity>(params).getPage(),
                    new EntityWrapper<OsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)
                    
            );
            page.setTotal(this.selectCount(new EntityWrapper<OsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
            return new PageUtils(page);
    	}else {
    		String examTitle = params.get("examTitle").toString();
    		 Page<OsExamEntity> page = this.selectPage(
    	        		new Query<OsExamEntity>(params).getPage(),
    	                new EntityWrapper<OsExamEntity>()
    	                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
    	                .and()
    	                .like(StringUtils.isNotBlank(examTitle), "exam_title", examTitle)
    	        );
    	        page.setTotal(this.selectCount(new EntityWrapper<OsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)
    	        		.and()
    	        		.like(StringUtils.isNotBlank(examTitle), "exam_title", examTitle)));
    	        return new PageUtils(page);
    	}
       
    }

	@Override
	public void insertOs(OsExamEntity osExam) {
		osExam.setCreateUserId(ShiroUtils.getUserEntity().getUserTrueName());
		osExam.setExamId(UUID.randomUUID().toString().replaceAll("-",""));
		osExam.setCreateTime(new Date());
		osExam.setUpdateTime(new Date());
		osExam.setIsDel(CommonParameters.isDel.IS_DEL_NO);
		dao.insertOsExamEntity(osExam);
		
	}

	@Override
	public void deleteOsExam(String examId) {
		OsExamEntity entity = new OsExamEntity();
		entity.setUpdateTime(new Date());
		entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
		dao.update(entity, new EntityWrapper<OsExamEntity>().eq("exam_id", examId));
		//dao.deleteOsExam(examId);
		
	}

}