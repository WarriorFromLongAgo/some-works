package com.orhonit.modules.generator.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppVideoLearningRecordDao;
import com.orhonit.modules.generator.app.entity.AppVideoLearningRecordEntity;
import com.orhonit.modules.generator.app.service.AppVideoLearningRecordService;
import com.orhonit.modules.generator.app.vo.AppLectureHallVO;
import com.orhonit.modules.generator.dao.OrganizationWorkshopDao;


/**
  * 组工讲堂  学习记录
 * @author YaoSC
 *
 */
@Service("AppVideoLearningRecordService")
public class AppVideoLearningRecordServiceImpl  extends ServiceImpl<AppVideoLearningRecordDao,AppVideoLearningRecordEntity>
                                              implements AppVideoLearningRecordService{
	@Autowired
	AppVideoLearningRecordDao RcordDao;
	@Autowired
	OrganizationWorkshopDao workShopDao;
	@Autowired
	AppVideoLearningRecordService service;
	
    
	
	/**
	 * 学习视频
	 */
	@Override
	public void insertRecored(Integer userId, Integer courseId, Integer rememberTime,Date updateTime,
			Date createTime,Double getIntegral,String integral,String studyStatus) {
		
		RcordDao.insertRecord(userId, courseId, rememberTime,updateTime,createTime,getIntegral,integral,studyStatus);
	}


    /**
     * 查出所有学习记录
     * @return 
     */
	/*
	 * @Override public Integer js(Integer userId,Integer courseId) { return
	 * RcordDao.js(userId,courseId); }
	 */

    
	/**
	 * 
	  * 学习记录
	  * 讲堂学习人数+1
	 */
	@Override
	public void inserRecoredUpdateStudyNum(AppVideoLearningRecordEntity e,Integer userId,
			Integer courseId, Integer rememberTime, Date updateTime,
			Date createTime, Double getIntegral) {
		String identifier= String.valueOf(CommonParameters.ProjectModeules.LEARNALESSON_ORGANIZATIONWORKSHOP);
		String studyStatus=CommonParameters.studystatus.STUDY_STATUS_YES;
		Integer count = RcordDao.js(identifier,userId,courseId);
		if(count==0) {
			RcordDao.insertRecord(userId, courseId, rememberTime,updateTime,createTime,getIntegral,
					identifier,studyStatus);
			workShopDao.upDateStudyNum(courseId);
		}else{
			 e.setRememberTime(rememberTime);
			 e.setUpdateTime(new Date());
			 RcordDao.updateRememberTime(rememberTime, courseId,userId);
		}
	}


	/*
	 * @Override public AppLectureHallVO selectRanKing(Integer userId) {
	 * AppLectureHallVO vo =workShopDao.selectLecture(userId);//用户的总积分&&总看视频长 double
	 * readoutRate=vo.getSp()/workShopDao.videoNumber();//看完视频比例 String read =
	 * String.valueOf(readoutRate); DecimalFormat decimal = new
	 * DecimalFormat("0.0%"); decimal.setRoundingMode(RoundingMode.HALF_UP); String
	 * str= decimal.format(read); vo.setReadoutRate(str); return vo; }
	 */

    
	/**
	 * 组工讲堂  排行榜 
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		boolean flag = true;
		int currPage = 1;
    	int limit = 10;      //每条页面数量
    	Integer index = 1;   //排名
    	if(params.get("page") != null){
            currPage = Integer.parseInt((String)params.get("page"));
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt((String)params.get("limit"));
        }
        Page<AppLectureHallVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        //String y =  String.valueOf(params.get("userId"));
        Integer userId = 0;
        //Integer userId = Integer.parseInt(y);
       // Integer allvideo=workShopDao.videoNumber();//看完视频比例
        List<AppLectureHallVO> vo =workShopDao.selectLecture(flag,userId,beginLimit,limit);//查询所有用户的学习
        for(AppLectureHallVO i : vo) {
        	//i.getSp()
        	//i.setIndex(index+(limit*(int)page.getPages()-1));
        	i.setIndex(index+beginLimit);
        	index++;
        }
        page.setTotal(vo.size());
        page.setRecords(vo);
		return new PageUtils(page);
	}

    /**
         * 组工书苑 提交学习
     */
	@Override
	public void insertBookStore(AppVideoLearningRecordEntity e, boolean flag, Integer userId,
			Integer courseId, Date updateTime, Date createTime, Integer getIntegral,
			String identifier, String studyStatus) {
		Integer count = RcordDao.js(identifier,userId,courseId);
		if(count==0) {
			RcordDao.insertBookeStore(userId, courseId, updateTime, createTime, getIntegral,
					String.valueOf(CommonParameters.ProjectModeules.LEARNALESSON_BOOKESTORE),
					 CommonParameters.studystatus.STUDY_STATUS_YES);
		}//else { /*if(flag &&  recordId>0){
			 //e.setUpdateTime(new Date());
			 //service.update(e,new EntityWrapper<AppVideoLearningRecordEntity>().eq("record_id", recordId));*/
		//}
	}


	@Override
	public PageUtils phlist(Map<String, Object> params) {
		boolean flag = true;
		int currPage = 1;
    	int limit = 10;      //每条页面数量
    	Integer index = 1;   //排名
    	if(params.get("page") != null){
            currPage = Integer.parseInt((String)params.get("page"));
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt((String)params.get("limit"));
        }
        Page<AppLectureHallVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        //String y =  String.valueOf(params.get("userId"));
        Integer userId = 0;
        List<AppLectureHallVO> vo =workShopDao.selectBookeStore(flag, userId, beginLimit, limit);
        for(AppLectureHallVO i:vo) {
        	//i.setIndex(index+(limit*(int)page.getPages()-1));
        	i.setIndex(index+beginLimit);
        	index++;
        }
        page.setTotal(vo.size());
        page.setRecords(vo);
		return new PageUtils(page);
	}
}
