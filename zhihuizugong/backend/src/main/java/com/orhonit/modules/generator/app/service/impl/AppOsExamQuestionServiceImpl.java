package com.orhonit.modules.generator.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppOsAnswerRecordDao;
import com.orhonit.modules.generator.app.dao.AppOsErrorQuestionDao;
import com.orhonit.modules.generator.app.dao.AppOsExamDao;
import com.orhonit.modules.generator.app.dao.AppOsExamQuestionDao;
import com.orhonit.modules.generator.app.dao.AppOsExamScopeDao;
import com.orhonit.modules.generator.app.dao.AppOsQuestionDao;
import com.orhonit.modules.generator.app.entity.AppOsAnswerRecordEntity;
import com.orhonit.modules.generator.app.entity.AppOsErrorQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamScopeEntity;
import com.orhonit.modules.generator.app.entity.AppOsQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppStudentAnswer;
import com.orhonit.modules.generator.app.entity.AppStudentExam;
import com.orhonit.modules.generator.app.entity.AppStudentExamScore;
import com.orhonit.modules.generator.app.service.AppOsErrorQuestionService;
import com.orhonit.modules.generator.app.service.AppOsExamQuestionService;
import com.orhonit.modules.generator.app.service.AppOsExamScopeService;
import com.orhonit.modules.generator.app.service.AppOsExamService;
import com.orhonit.modules.generator.app.vo.AppOsExamUserExaminationVO;

import lombok.extern.slf4j.Slf4j;


/**
 *  APP端考试题库试题表
 * @author YaoSC
 *
 */
@Service("AppOsExamQuestionService")
@Slf4j
public class AppOsExamQuestionServiceImpl extends ServiceImpl<AppOsExamQuestionDao,AppOsExamQuestionEntity>implements AppOsExamQuestionService{

	@Autowired
	AppOsExamService appExamService;
	@Autowired
	AppOsQuestionDao appOsQuestionDao;
	@Autowired
	AppOsExamQuestionService examQustionService;
	@Autowired
	AppOsExamQuestionDao apppOsExamQuestionDao;
	@Autowired
	AppOsAnswerRecordDao appOsAnSwerRecordDao;
	@Autowired
	AppOsExamDao appOsExamDao;
	@Autowired
	AppOsErrorQuestionService appOsErrorQuestionService;
	@Autowired
	AppOsExamScopeService  appOsExamScopeService;	
	@Autowired
	AppOsExamScopeDao appOsExamScopeDao;
	@Autowired
	AppOsErrorQuestionDao appOsErrorQuestionDao;

	
	
	/**
	 * 套卷出题
	 */
	@SuppressWarnings({ "null", "static-access" })
	@Override
	public AppOsExamUserExaminationVO CatenaQuestion(String examId,String userId) {
		//创建新的白卷
		AppOsExamUserExaminationVO osExam = new AppOsExamUserExaminationVO();
		// 设置卷面编号
		String paperNo = UUID.randomUUID().toString().replace("-", "");
		System.out.println("卷面号:"+examId);
		// 试卷编号应该是真实编号ExamId 中的id
		osExam.setPaperNo(examId);
		// 根据试卷编号找出符合条件的题
        AppOsExamEntity exam = appExamService.selectById(examId);
        List<AppOsQuestionEntity> questionList = null;
        if(CommonParameters.OsExamRandom.IS_RANDOM.equals(exam.getIsRandom())) {
        	//随机题
        	questionList=appOsQuestionDao.selectQuestionByNumWhereLibraryNO(exam.getLibraryId(), true, exam.getQuestionNumber());
        }else {
        	//顺序题
        	questionList=appOsQuestionDao.selectQuestionByNumWhereLibraryNO(exam.getLibraryId(), false, exam.getQuestionNumber());
        }
        // 保存空白试卷
        AppOsExamQuestionEntity entity = new AppOsExamQuestionEntity();
        int count=apppOsExamQuestionDao.selectCount(new EntityWrapper<AppOsExamQuestionEntity>().eq("paper_no", examId).and().eq("user_id", userId));
        if(count==0) {
        	for(AppOsQuestionEntity en : questionList) {
            		entity.setId(UUID.randomUUID().toString().replace("-", ""));
                	entity.setPaperNo(examId);
                	entity.setQuestionNo(en.getQuestionNo());
                	entity.setUserId(String.valueOf(userId));
                	entity.setQuestionAnswer(en.getAnswer());
                	entity.setType(CommonParameters.AppOsQuestionDomain.SeriesExercise);
                	entity.setCreateTime(new Date());
                	entity.setUpdateTime(new Date());
                	apppOsExamQuestionDao.insertOsExamQuestion(entity);
            }
        }
        osExam.setQuestions(questionList);
        return osExam;
	}

	
	/**
	 * 练习模式判分（套卷练习）
	 * 
	 * @param studentExam
	 *                学员答案
	 * @return 学员成绩信息
	 */
	@Override
	public AppStudentExamScore MarkExerciseExam(AppStudentExam studentExam) {
		Integer right = 0; //正确题
		Integer error = 0; //错误题
		Integer score = 0; //得分
		String userId = studentExam.getUserId();
		System.out.println("userId="+userId);
		System.out.println("studentExam="+studentExam.toString());
		for(AppStudentAnswer s :studentExam.getStudentAnswers()) {
			AppOsAnswerRecordEntity record = new AppOsAnswerRecordEntity();
			record.setId(UUID.randomUUID().toString().replace("-", ""));
			record.setUserId(userId);
			record.setUserNo(s.getNo());
			record.setUserSelect(s.getSelect());
			record.setExamNo(studentExam.getExamNo());
			record.setCreateTime(new Date());
			record.setQuestionNo(s.getId());
			record.setUpdateTime(new Date());
			appOsAnSwerRecordDao.inserAppOsAnswerRecord(record);
		}
		int sl=studentExam.getStudentAnswers().size();
		System.out.println("学员答案数量="+sl);
		apppOsExamQuestionDao.updatePaPerNo(studentExam.getExamNo(), sl);
		// 查找正确答案
		List<AppOsExamQuestionEntity> examQuestions = apppOsExamQuestionDao.selectByPaperNo(studentExam.getExamNo(), userId);
		System.out.println("所有答卷数量="+examQuestions.size());
		AppOsExamEntity exam = null;
		if(examQuestions.size()>0) {
			// 根据试卷编号找试卷信息
			String paperNo=examQuestions.get(0).getPaperNo();
			System.out.println("pagerNo:"+paperNo);
			exam=appOsExamDao.selectExamOne(paperNo);
		}
		for(AppStudentAnswer se : studentExam.getStudentAnswers()) {
			for(AppOsExamQuestionEntity eq : examQuestions) {
				if(se.getId().equals(eq.getQuestionNo())) {
					//题号一样
					if(se.getSelect().equals(eq.getQuestionAnswer())) {
						//答案一样
						right++;
						break;
					}else {
						//保存错题
						System.out.println("错题编号:"+eq.getQuestionNo());
						int errorCount=appOsErrorQuestionDao.selectCount(new EntityWrapper<AppOsErrorQuestionEntity>()
								.eq("question_no", eq.getQuestionNo())
								.and().eq("user_id", userId));
						if(errorCount>0) {
							AppOsErrorQuestionEntity entity = new AppOsErrorQuestionEntity();
							entity.setUpdateTime(new Date());
							entity.setErrorAnswer(se.getSelect());
							appOsErrorQuestionDao.update(entity, new EntityWrapper<AppOsErrorQuestionEntity>().eq("question_no", eq.getQuestionNo()).and().eq("user_id", userId));
						}else {
							appOsErrorQuestionDao.InsertOsErrorQuestion(new AppOsErrorQuestionEntity(UUID.randomUUID().toString().replace("-", ""),
									eq.getQuestionNo(), se.getSelect(), userId, new Date(),new Date()));
						}
					}
				}
			}
		}
		if(exam==null) {
			score = right * 1;
		}else {
			score = right * exam.getQuestionScope();
		}
		//错题数量
		error = examQuestions.size() - right;
		System.out.println("试卷编号:"+studentExam.getExamNo()+"用户:"+userId+"正确题数:"+right+"错误题:"+error+"得分:"+score);
		
		int examScopeCount=appOsExamScopeDao.selectCount(new EntityWrapper<AppOsExamScopeEntity>()
				.eq("paper_no", studentExam.getExamNo()).and().eq(true, "user_id", userId));
		if(examScopeCount>0) {
			AppOsExamScopeEntity entity = new AppOsExamScopeEntity();
			entity.setUpdateTime(new Date());
			entity.setPaperScore(score);
			entity.setUseTime(studentExam.getUseTime());
			appOsExamScopeDao.update(entity, new EntityWrapper<AppOsExamScopeEntity>().eq("paper_no", studentExam.getExamNo()).and().eq("user_id", userId));
		}else {
			//保存成绩
			AppOsExamScopeEntity osExam = new AppOsExamScopeEntity(UUID.randomUUID().toString().replace("-", ""), studentExam.getExamNo(),
					userId, score, right, error, new Date(), studentExam.getUseTime(),
					CommonParameters.OsScopeType.Exercise, new Date(),new Date(),CommonParameters.isDel.IS_DEL_NO);
			appOsExamScopeDao.insertExamScope(osExam);
		}
		//返回结果
		AppStudentExamScore examScore = new AppStudentExamScore();
		examScore.setUserId(userId);
		examScore.setPaperNo(studentExam.getExamNo());
		examScore.setRight(right);
		examScore.setError(error);
		examScore.setScore(score);
		return examScore;
	}

	
	/**
	 * 在线答题出题
	 * * @param num
	 * @return
	 */
	@Override
	public AppOsExamUserExaminationVO OnlineQuestion(String examId,String userId) {
		// 创建空白试卷
		AppOsExamUserExaminationVO osExam = new AppOsExamUserExaminationVO();
		// 试题列表
		List<AppOsQuestionEntity> questionList = null;
		// 试卷编号应该是真实编号ExamId 中的id
		osExam.setPaperNo(examId);
		//String userId =String.valueOf(ShiroUtils.getUserEntity().getUserId());
		// log.info("查找用户【{}】是否答过题【{}】", userId, examId);
		// 答题历史
		List<AppOsExamQuestionEntity> examHistory = apppOsExamQuestionDao.selectOnlyOnlineExam(examId, userId);
		if(examHistory.size()>0) {
			// 答过题
			// 找有没有成绩
			AppOsExamScopeEntity scopeHistory = appOsExamScopeDao.selectOnlyOnlieExamScope(examId, userId);
			if(scopeHistory !=null) {
				// 答过题了 不能出卷不能答题
				log.debug("已经答题过了!!!{},{},{}",examId, userId, scopeHistory);
				return null;
			}else {
				log.info("删了试卷答案,重新出卷");
				apppOsExamQuestionDao.deleteOnlyOnlineExamQuestion(examId, userId);
				// 根据试卷编号找出符合条件的题
				AppOsExamEntity Exam = appExamService.selectById(examId);
				if("1".equals(Exam.getIsRandom())) {
					questionList = appOsQuestionDao.selectQuestionByNumWhereLibraryNO(Exam.getLibraryId(),true,
							Exam.getQuestionNumber());
				}else {
					questionList = appOsQuestionDao.selectQuestionByNumWhereLibraryNO(Exam.getLibraryId(), false,
							Exam.getQuestionNumber());
				}
				// 保存试题答案
				AppOsExamQuestionEntity examQuestion = new AppOsExamQuestionEntity();
				for(AppOsQuestionEntity q : questionList) {
					examQuestion.setPaperNo(examId);
					examQuestion.setQuestionNo(q.getQuestionNo());
					examQuestion.setUserId(userId);
					examQuestion.setQuestionAnswer(q.getAnswer());
					examQuestion.setType(CommonParameters.AppOsQuestionDomain.OnlineExam);
					examQuestion.setCreateTime(new Date());
					apppOsExamQuestionDao.insertOsExamQuestion(examQuestion);
				}
				log.info(questionList.toString());
				//保存试题
				osExam.setQuestions(questionList);
				return osExam;
			}
		}else {
			log.info("第一次答题");
			// 根据试卷编号找出符合条件的题
			AppOsExamEntity	 Exam = appOsExamDao.selectById(examId);
			if("1".equals(Exam.getIsRandom())) {
				questionList = appOsQuestionDao.selectQuestionByNumWhereLibraryNO(Exam.getLibraryId(), true,
						Exam.getQuestionNumber());
			}else {
				questionList = appOsQuestionDao.selectQuestionByNumWhereLibraryNO(Exam.getLibraryId(), false,
						Exam.getQuestionNumber());
			}
			// 保存试题答案
			AppOsExamQuestionEntity examQuestion = new AppOsExamQuestionEntity();
			for (AppOsQuestionEntity q : questionList) {
				examQuestion.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				examQuestion.setPaperNo(examId);
				examQuestion.setQuestionNo(q.getQuestionNo());
				examQuestion.setUserId(userId);
				examQuestion.setQuestionAnswer(q.getAnswer());
				examQuestion.setType(CommonParameters.AppOsQuestionDomain.OnlineExam);
				examQuestion.setCreateTime(new Date());
				apppOsExamQuestionDao.insertOsExamQuestion(examQuestion);
			}
			log.info(questionList.toString());
			// 保存试题
			osExam.setQuestions(questionList);

			return osExam;
		}
	}
    
	/**
	  * 在线考试判分
	 * 
	 * @param studentExam
	  *            学员答案
	 * @return 学员成绩信息
	 */
	@Override
	public AppStudentExamScore MarkOnlineExam(AppStudentExam appstudentExam) {
		log.info("试卷编号【{}】", appstudentExam.getExamNo());
		String userId =String.valueOf(ShiroUtils.getUserEntity().getUserId());
		for(AppStudentAnswer s : appstudentExam.getStudentAnswers()) {
			AppOsAnswerRecordEntity record = new AppOsAnswerRecordEntity();
			record.setUserId(userId);
			record.setUserNo(s.getNo());
			record.setUserSelect(s.getSelect());
			record.setExamNo(appstudentExam.getExamNo());
			record.setCreateTime(new Date());
			record.setQuestionNo(s.getId());
			appOsAnSwerRecordDao.inserAppOsAnswerRecord(record);
		}
		// 根据试卷编号找试卷信息
		AppOsExamEntity exam = appOsExamDao.selectById(appstudentExam.getExamNo());
		Integer right = 0;
		Integer error = 0;
		Integer score = 0;
		// 根据卷面获取本卷答案
		List<AppOsExamQuestionEntity> examQuestions = apppOsExamQuestionDao.selectByPaperNo(appstudentExam.getExamNo(), userId);
		for(AppStudentAnswer se : appstudentExam.getStudentAnswers()) {
			for(AppOsExamQuestionEntity eq : examQuestions) {
				if(se.getId().equals(eq.getQuestionNo())) {
					// 题号一样
					if(se.getSelect().equals(eq.getQuestionAnswer())) {
						// 答案一样
						right++;
						break;
					}else {
						// 保存错题
						System.out.println("错题编号：" + eq.getQuestionNo());
						appOsErrorQuestionService
						.insert(new AppOsErrorQuestionEntity(UUID.randomUUID().toString().replace("-", ""),
								eq.getQuestionNo(), se.getSelect(), userId, new Date(),new Date()));
					}
				}
			}
		}
		// 试卷分值
		score = right * exam.getQuestionScope();
		// 错题数量
		error = examQuestions.size() - right;
		System.out.println("试卷编号：" + appstudentExam.getExamNo() + "用户：" + userId + "正确题数：" + right + "错误数：" + error);
		// 保存成绩
		AppOsExamScopeEntity osExam = new AppOsExamScopeEntity(UUID.randomUUID().toString().replace("-", ""), appstudentExam.getExamNo(),
				userId, score, right, error, new Date(), appstudentExam.getUseTime(), CommonParameters.OsScopeType.Formal,
				new Date(),new Date(),CommonParameters.isDel.IS_DEL_NO);
		appOsExamScopeService.insert(osExam);
		// 返回结果
		AppStudentExamScore examScore = new AppStudentExamScore();
		examScore.setUserId(userId);
		String userName = ShiroUtils.getUserEntity().getUsername();
		examScore.setName(userName);
		examScore.setPaperNo(appstudentExam.getExamNo());
		// OsQuestionLibrary library = osQuestionLibraryMapper.selectByPrimaryKey();
		// examScore.setExamName(library.getLibraryTitle());
		examScore.setRight(right);
		examScore.setError(error);
		examScore.setScore(score);
		return examScore;
	}
}
