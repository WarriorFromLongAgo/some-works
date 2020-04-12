package com.orhonit.modules.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.annotation.Login;
import com.orhonit.modules.app.service.AppMeetingService;
import com.orhonit.modules.app.vo.AppOneMeetVo;

@RestController
@RequestMapping("/app/meeting")
public class AppMeetingController{
	
	@Autowired
	private AppMeetingService appMeetingService;
	
    /**
     * 支部活动列表
     */
	@Login
    @RequestMapping("meetinglist")
    //@RequiresPermissions("sys:meeting:list")
    public R meetinglist(@RequestParam Map<String, Object> params,@RequestAttribute("userId") Long userId){
    	if(userId!=null){
	        PageUtils page = appMeetingService.meetingList(params,userId);
	
	        return R.ok().put("page", page);
    	}
    	return R.parameterIsNul();
    }
	
    /**
     * 我参加的活动以及出勤情况
     */
	@Login
    @RequestMapping("getJoinMeet")
    //@RequiresPermissions("sys:meeting:list")
    public R getJoinMeetList(@RequestParam Map<String, Object> params,@RequestAttribute("userId") Long userId){
    	if(userId!=null){
	        PageUtils page = appMeetingService.getJoinMeetList(params,userId);
	
	        return R.ok().put("page", page);
    	}
    	return R.parameterIsNul();
    }
		
	/**
	 * 用户获取活动信息详情
	 * @param params
	 * @param userId
	 * @return
	 */
	@Login
    @GetMapping("userGetOneMeet")
    //@RequiresPermissions("sys:meeting:list")
    public R userGetOneMeet(@RequestParam Integer peopleId){
    	
	    AppOneMeetVo appOneMeetVo = appMeetingService.userGetOneMeet(peopleId);
	
    	return R.ok().put("appOneMeetVo", appOneMeetVo);
    }
	
	/**
	 * 修改阅读状态为已读
	 * @param peopleId
	 * @return
	 */
	@Login
    @PutMapping("updRead")
    //@RequiresPermissions("sys:meeting:list")
    public R updMeetPeople(@RequestParam Integer peopleId){
	      appMeetingService.updMeetPeople(peopleId);
    	return R.ok();
    }
	
	/**
	 * 修改签到状态为未签到
	 * @param peopleId
	 * @return
	 */
	@Login
    @PutMapping("updSignin")
    //@RequiresPermissions("sys:meeting:list")
    public R updSignin(@RequestParam Integer peopleId){
	      appMeetingService.updSignin(peopleId);
    	return R.ok();
    }
	
	/**
	 * 统计用户未读活动信息数量
	 * @param peopleId
	 * @return
	 */
	@Login
    @GetMapping("countNotRead")
    //@RequiresPermissions("sys:meeting:list")
    public R countNotRead(@RequestAttribute("userId") Long userId){
		if(userId!=null){
	       int notReadNum= appMeetingService.countNotRead(userId);
	        return R.ok().put("countNotRead", notReadNum);
    	}
    	return R.parameterIsNul();
    }
	
	/**
	 * 报名接口
	 * @param peopleId
	 * @return
	 */
	@Login
    @PutMapping("toJoin")
    //@RequiresPermissions("sys:meeting:list")
    public R peopleJoin(@RequestParam Integer peopleId,
    					@RequestParam Integer stationId,
    					@RequestParam Integer peopleNeedMeet,
    					@RequestParam String peopleLveMsg){
		 appMeetingService.peopleJoin(peopleId,stationId,peopleNeedMeet,peopleLveMsg);
		 return R.ok();
    }
	
}
