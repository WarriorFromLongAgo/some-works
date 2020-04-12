package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.MeetingEntity;
import com.orhonit.modules.generator.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支部活动表
 *
 * @author
 * @email chobitsyjwz@163.com
 * @date 2019-02-14 14:56:22
 */
@RestController
@RequestMapping("app/meet")
public class AppMeetController {
    @Autowired
    private MeetingService meetingService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    //@RequiresPermissions("sys:meeting:list")
    public R list(@RequestParam Map<String, Object> params , @RequestParam Long userId){
    	if(userId != null){
	        PageUtils page = meetingService.queryPage(params,userId);

	        return R.ok().put("page", page);
    	}
    	return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
    //@RequiresPermissions("sys:meeting:info")
    public R info(@PathVariable("meetId") String meetId){
        MeetingEntity meeting = meetingService.getById(meetId);

        return R.ok().put("meeting", meeting);
    }

}
