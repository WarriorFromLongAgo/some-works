package com.orhonit.modules.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.app.annotation.Login;
import com.orhonit.modules.sys.entity.UserStationEntity;
import com.orhonit.modules.sys.service.UserStationService;

@RestController
@RequestMapping("/app/appstation")
public class AppUserStationController {
	
    @Autowired
    private UserStationService userStationService;
	
    /**
     *乘车地点列表
     */
	@Login
    @RequestMapping("/stationList")
    //@RequiresPermissions("sys:userstation:list")
    public R stationList(@RequestParam Integer routeId){
        List<UserStationEntity> stationList = userStationService.getStationList(routeId);

        return R.ok().put("stationList", stationList);
    }
}
