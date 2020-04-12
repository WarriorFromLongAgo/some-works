package com.orhonit.modules.generator.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.entity.AppVideoLearningRecordEntity;
import com.orhonit.modules.generator.app.service.AppVideoLearningRecordService;
import com.orhonit.modules.generator.dao.OrganizationWorkshopDao;
import com.orhonit.modules.generator.entity.OrganizationWorkshopEntity;
import com.orhonit.modules.generator.entity.SoulTypeEntity;
import com.orhonit.modules.generator.service.LearnalessonBookstoreService;
import com.orhonit.modules.generator.service.OrganizationWorkshopService;
import com.orhonit.modules.generator.service.SoulTypeService;
import com.orhonit.modules.sys.controller.AbstractController;

/**
 * APP端 组工讲堂 组工书苑
 * 
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("app/learnalesson")
public class AppLearnalessonController extends AbstractController{
	
	@Autowired
	SoulTypeService soulTypeService;
	@Autowired
	OrganizationWorkshopService orgService;
	@Autowired
	AppVideoLearningRecordService recordService;
	@Autowired
	OrganizationWorkshopDao workshopDao;
	@Autowired
	LearnalessonBookstoreService bookeStoreService;



	/**
	 * 组工讲堂 列表
	 * 
	 * @param params
	 * @return
	 */
	
	  @GetMapping("/organizationworkshop/workshopList") 
	  public R workshopList(@RequestParam Map<String,Object>params) { 
		  PageUtils page = orgService.AppqueryPage(params);
	  
	      return R.ok().put("page", page); 
	  }
	  
	    /**
	    * 组工讲堂 详情
	     * @param courseId
	     * @return
	     */
	    @GetMapping("/organizationworkshop/info/{courseId}")
	    public R organizationworkshopInfo(@PathVariable("courseId") Integer courseId) {
	    	OrganizationWorkshopEntity t=	orgService.selectWorkShop(courseId);
	    	return R.ok().put("OrganizationWorkshopEntity", t);
	    }
	  
	  /**
	   * 查出所有分类
	   * @return
	   */
	  @GetMapping("/type/soultype/list")
	  public List<SoulTypeEntity> typelist() {
		  return soulTypeService.soulTypeList();
	  }
	  
	  

	  /**
	   * 组工讲堂  学习视频
	   * @param userId
	   * @param courseId
	   * @param e
	   * @param rememberTime
	   * @param getIntegral
	   * @param flag
	   * @param recordId
	   * @return
	   */
	  @PostMapping("/organizationworkshop/fraction/save")
	  public R fractionsave(@RequestParam("courseId")Integer courseId,AppVideoLearningRecordEntity e,
			  @RequestParam("rememberTime")Integer rememberTime,@RequestParam("getIntegral")Double getIntegral) {
		  Integer userId = getUserId().intValue();
		  recordService.inserRecoredUpdateStudyNum(e,userId, courseId, rememberTime, new Date(), new Date(), 
				  getIntegral);
		  //recordService.insertRecored(userId, courseId, rememberTime, new Date(), new Date(), getIntegral, identifier, studyStatus);
		return R.ok();
	  }
	  
	  
	  
	  /**
	   * 组工讲堂 统计排行列表
	   * @param params
	   * @return
	   */
	  @GetMapping("/organizationworkshop/personalranking")
	  public R personlRanking(@RequestParam Map<String,Object>params) {
		  PageUtils page = recordService.queryPage(params);
		  return R.ok().put("page", page);
	  }
	  
	  
	  
	  /**
	   * 组工书苑   提交看书
	   * @param userId
	   * @param courseId
	   * @param e
	   * @param rememberTime
	   * @param getIntegral
	   * @param flag
	   * @param recordId
	   * @return
	   */
	  @RequestMapping("/bookstore/save")
	  public R bookstoresave(@RequestParam("courseId")Integer courseId,AppVideoLearningRecordEntity e,
			  @RequestParam("getIntegral")Integer getIntegral, @RequestParam("flag")boolean flag) {
		 Integer userId = getUserId().intValue();
		  String identifier= String.valueOf(CommonParameters.ProjectModeules.LEARNALESSON_BOOKESTORE);
		  String studyStatus=CommonParameters.studystatus.STUDY_STATUS_YES;
		  recordService.insertBookStore(e,  flag, userId, courseId, new Date(),new Date(), getIntegral, identifier, studyStatus);
		  return  R.ok();
	  }
	  
	  /**
	      * 组工书苑 用户积分排行榜  
	   * @param params
	   * @return
	   */
	  @RequestMapping("/bookestore/phlist")
	  public R phlist(@RequestParam Map<String,Object>params) {
		  PageUtils page = recordService.phlist(params);
		  return R.ok().put("page", page);
	  }
	  
	  /**
		 * 组工书苑 列表
		 * 
		 * @param params
		 * @return
		 */
		@RequestMapping("/bookestore/list")
		public R bookestorelist(@RequestParam Map<String, Object> params) {
			PageUtils page = bookeStoreService.queryPage(params);
			return R.ok().put("page", page);
		}
		
		/**
		 * 书苑详情
		 * @param bookstoreId
		 * @return
		 */
		@GetMapping("/bookestore/info/{bookstoreId}")
		public R bookestoreinfo(@PathVariable("bookstoreId") Integer bookstoreId) {
			return R.ok().put("entity", bookeStoreService.selectById(bookstoreId));
		}
	  
}
