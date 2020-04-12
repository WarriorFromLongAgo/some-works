package com.orhonit.modules.generator.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.app.push.jdpush;


@RestController
@RequestMapping("/app/tuisong")
public class AppPushController {
	
	/**
	 * 安卓推送 demo
	 * @param id
	 * @param title
	 * @param text
	 * @return
	 */
	@RequestMapping(value="/anzhuo",method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String,Object>addAr(@Valid  @RequestBody String id, String title, String msg){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,String>parm = new HashMap<String,String>();
		parm.put("id", id);
		parm.put("title", title);
		parm.put("msg", msg);
		jdpush.jpushAndroid(parm);
		return result;
	}
	

}
