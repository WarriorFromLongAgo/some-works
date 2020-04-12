package com.orhonit.modules.generator.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppOsAnswerRecordDao;
import com.orhonit.modules.generator.app.dao.AppOsErrorQuestionDao;
import com.orhonit.modules.generator.app.dao.AppOsExamQuestionDao;
import com.orhonit.modules.generator.app.dao.AppOsExamScopeDao;
import com.orhonit.modules.generator.app.entity.AppOsAnswerRecordEntity;
import com.orhonit.modules.generator.app.entity.AppOsErrorQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamScopeEntity;
import com.orhonit.modules.generator.dao.OsExamDao;
import com.orhonit.modules.generator.dao.OsQuestionDao;
import com.orhonit.modules.generator.dao.OsQuestionLibraryDao;
import com.orhonit.modules.generator.entity.OsExamEntity;
import com.orhonit.modules.generator.entity.OsQuestionEntity;
import com.orhonit.modules.generator.entity.OsQuestionLibraryEntity;
import com.orhonit.modules.generator.service.OsQuestionLibraryService;
import com.orhonit.modules.generator.vo.QuestionLibraryVo;


@Service("osQuestionLibraryService")
public class OsQuestionLibraryServiceImpl extends ServiceImpl<OsQuestionLibraryDao, OsQuestionLibraryEntity> implements OsQuestionLibraryService {

	@Autowired
	OsQuestionLibraryService service;
	@Autowired
	OsQuestionLibraryDao dao;
	@Autowired
	OsExamDao osExamDao;
	@Autowired
	AppOsExamQuestionDao appOsExamQuestionDao;
	@Autowired
	AppOsExamScopeDao appOsExamScopeDao;
	@Autowired
	OsQuestionDao osQuestionDao;
	@Autowired
	AppOsErrorQuestionDao appOsErrorQuestionDao;
	@Autowired
	AppOsAnswerRecordDao appOsAnswerRecordDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if(!params.containsKey("libraryTitle")) {
    		 Page<OsQuestionLibraryEntity> page = this.selectPage(
    	                new Query<OsQuestionLibraryEntity>(params).getPage(),
    	                new EntityWrapper<OsQuestionLibraryEntity>()
    	                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
    	                .orderDesc(Arrays.asList(new String[] {"library_create_time"}))
    		 );
    		 page.setTotal(this.selectCount(new EntityWrapper<OsQuestionLibraryEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
    		 return new PageUtils(page);
    	}else {
    		String libraryTitle = params.get("libraryTitle").toString();
    		System.out.println("libraryTitle="+libraryTitle);
            Page<OsQuestionLibraryEntity> page = this.selectPage(
                    new Query<OsQuestionLibraryEntity>(params).getPage(),
                    new EntityWrapper<OsQuestionLibraryEntity>()
                    .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
                    .and()
                    .like(StringUtils.isNotBlank(libraryTitle), "library_title", libraryTitle)
                    .orderDesc(Arrays.asList(new String[] {"library_create_time"}))
            );
            page.setTotal(this.selectCount(new EntityWrapper<OsQuestionLibraryEntity>()
            		.eq("is_del", CommonParameters.isDel.IS_DEL_NO)
            		.and()
            		.like(StringUtils.isNotBlank(libraryTitle), "library_title", libraryTitle)));
            return new PageUtils(page);
    	}
    }

	@Override
	public void removeByIds(Long[] Ids) {
		this.deleteBatchIds(Arrays.asList(Ids));
	}

	@Override
	public void save(OsQuestionLibraryEntity config) {
		String uuid = UUID.randomUUID().toString().replaceAll("-","");  
		config.setLibraryId(uuid);
		config.setIsDel(CommonParameters.isDel.IS_DEL_NO);
		dao.inserOsQuestionLibrary(config);
		//this.insert(config);
		
	}
	
	 @Override 
	 public void updateOs(OsQuestionLibraryEntity config) {
		 dao.updateOsQuestionlibrary(config);
	 }

	@Override
	public void deleteAllOsQuestions(String libraryId) {
		OsQuestionLibraryEntity entity = new OsQuestionLibraryEntity();
		entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
		dao.update(entity, new EntityWrapper<OsQuestionLibraryEntity>().eq("library_id", libraryId));
		//题库ID查询所有题
		List<OsQuestionEntity>questionList=osQuestionDao.selectList(new EntityWrapper<OsQuestionEntity>().eq("library_id", libraryId)
				.and().eq("is_del", CommonParameters.isDel.IS_DEL_NO));
		if(questionList.size()>0) {
			for(OsQuestionEntity ques:questionList) {
				AppOsErrorQuestionEntity errorquestion = new AppOsErrorQuestionEntity();
				errorquestion.setIsDel(CommonParameters.isDel.IS_DEL_YES);
				appOsErrorQuestionDao.update(errorquestion, new EntityWrapper<AppOsErrorQuestionEntity>().eq("question_no", ques.getQuestionNo()));
			}
			OsQuestionEntity question = new OsQuestionEntity();
			question.setIsDel(CommonParameters.isDel.IS_DEL_YES);
			//逻辑删除题
			osQuestionDao.update(question, new EntityWrapper<OsQuestionEntity>().eq("library_id", libraryId));
		}
		List<OsExamEntity>exma=osExamDao.selectList(new EntityWrapper<OsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO).and().eq("library_id", libraryId));
		if(exma.size()>0) {
			for(OsExamEntity Exam:exma) {
				List<AppOsExamQuestionEntity>examQuestionList=appOsExamQuestionDao.selectList(new EntityWrapper<AppOsExamQuestionEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)
						.and()
						.eq("paper_no", Exam.getExamId()));
				if(examQuestionList.size()>0) {
					for(AppOsExamQuestionEntity ExamQuestionEntity:examQuestionList) {
						AppOsExamScopeEntity osExamScope = new AppOsExamScopeEntity();
						osExamScope.setIsDel(CommonParameters.isDel.IS_DEL_YES);
						appOsExamScopeDao.update(osExamScope, new EntityWrapper<AppOsExamScopeEntity>().eq("paper_no", ExamQuestionEntity.getPaperNo()));
					}
					//逻辑删试题表
					AppOsExamQuestionEntity t = new AppOsExamQuestionEntity();
					t.setIsDel(CommonParameters.isDel.IS_DEL_YES);
					appOsExamQuestionDao.update(t, new EntityWrapper<AppOsExamQuestionEntity>().eq("paper_no", Exam.getExamId()));
				}
				//逻辑删记录表
				AppOsAnswerRecordEntity anser = new AppOsAnswerRecordEntity();
				anser.setIsDel(CommonParameters.isDel.IS_DEL_YES);
				appOsAnswerRecordDao.update(anser, new EntityWrapper<AppOsAnswerRecordEntity>().eq("exam_no", Exam.getExamId()));
			}
			//逻辑删试卷表
			OsExamEntity osexam = new OsExamEntity();
			osexam.setIsDel(CommonParameters.isDel.IS_DEL_YES);
			osExamDao.update(osexam, new EntityWrapper<OsExamEntity>().eq("library_id", libraryId));
		}
		//dao.deleteAllOsQuestion(id);
		
	}

	@Override
	public QuestionLibraryVo getLibraryQuestionCount(String libraryId) {
		QuestionLibraryVo questionLibraryVo = new QuestionLibraryVo();
		questionLibraryVo.setSingle(dao.selectSingleCount(libraryId));
		questionLibraryVo.setMulti(dao.selectMultiCount(libraryId));
		questionLibraryVo.setJudge(dao.selectJudgeCount(libraryId));
		questionLibraryVo.setTotle(dao.selectQuestionTotle(libraryId));
		return questionLibraryVo;
	}

}