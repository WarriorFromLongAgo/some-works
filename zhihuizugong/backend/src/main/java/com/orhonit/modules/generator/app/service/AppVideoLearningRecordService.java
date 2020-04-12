package com.orhonit.modules.generator.app.service;


import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppVideoLearningRecordEntity;
import com.orhonit.modules.generator.app.vo.AppLectureHallVO;

/**
  * 组工讲堂  学习记录
 * @author YaoSC
 *
 */
public interface AppVideoLearningRecordService extends IService<AppVideoLearningRecordEntity>{
	
	/**
	 * 查出所有学习记录
	 * @return
	 */
	//Integer js(Integer userId,Integer courseId);
	
	/**
	 * TODO 暂时不用此方法
	 * 组工讲堂 学习视频
	 * @param userId
	 * @param courseId
	 * @param rememberTime
	 */
	void insertRecored(Integer userId,Integer courseId,Integer rememberTime,Date updateTime,
			Date createTime,Double getIntegral,String integral,String studyStatus);
	
	/**
	 * 组工讲堂 学习视频
	 * @param e
	 * @param recordId
	 * @param flag
	 * @param userId
	 * @param courseId
	 * @param rememberTime
	 * @param updateTime
	 * @param createTime
	 * @param getIntegral
	 * @param identifier
	 * @param studyStatus
	 */
	void inserRecoredUpdateStudyNum(AppVideoLearningRecordEntity e,Integer userId,Integer courseId,
			Integer rememberTime,Date updateTime,Date createTime,Double getIntegral);
    /**
     * 组工书苑   学习书籍
     * @param e
     * @param recordId
     * @param flag
     * @param userId
     * @param courseId
     * @param rememberTime
     * @param updateTime
     * @param createTime
     * @param getIntegral
     * @param identifier
     * @param studyStatus
     */
    void insertBookStore(AppVideoLearningRecordEntity e,boolean flag,Integer userId,Integer courseId,
			Date updateTime,Date createTime,Integer getIntegral,String identifier,String studyStatus);
	
	//AppLectureHallVO selectRanKing(Integer userId);
	
	  /**
	   * 组工讲堂  用户积分&时长排行榜
	   * @param params
	   * @return
	   */
	 PageUtils queryPage(Map<String, Object> params);
	 
	 
	 //组工书苑  用户积分排行
	 PageUtils phlist(Map<String,Object>params);
}
