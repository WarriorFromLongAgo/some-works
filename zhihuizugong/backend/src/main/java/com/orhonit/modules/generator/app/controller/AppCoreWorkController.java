package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreWorkEntity;
import com.orhonit.modules.generator.service.CoreWorkService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 民心连心桥表
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@RestController
@RequestMapping("app/CoreWork")
public class AppCoreWorkController{
    @Autowired
    private CoreWorkService coreWorkService;

    /**
     * 连心桥列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
       String workId = (String) params.get("workId");
       if(StringUtils.isNotBlank(workId)) {
    	   PageUtils page = coreWorkService.queryPage(params);
    	   
    	   return R.ok().put("page", page);
       }
       return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{serveId}")
    public R info(@PathVariable("serveId") String serveId){
		CoreWorkEntity coreWork = coreWorkService.selectByServeId(serveId);
        return R.ok().put("coreWork", coreWork);
    }

}
