package com.orhonit.modules.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.app.service.AppNewsLbtService;
import com.orhonit.modules.generator.entity.NewsLbtEntity;

@RestController
@RequestMapping("/app/newslbt")
public class AppNewsLbtController {

	    @Autowired
	    private AppNewsLbtService appNewsLbtService;
	    
	    /**
	     *轮播图展示
	     */
	    @GetMapping("/lbtListToApp")
	    //@RequiresPermissions("sys:newslbt:list")
	    public R getALLlist(){
		        List<NewsLbtEntity> list = appNewsLbtService.getALLlist();
		        return R.ok().put("list", list);
	    }
}
