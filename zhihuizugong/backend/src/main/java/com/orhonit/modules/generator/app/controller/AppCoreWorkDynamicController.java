package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreWorkDynamicEntity;
import com.orhonit.modules.generator.service.CoreWorkDynamicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 工作队动态表
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@RestController
@RequestMapping("app/Coreworkdynamic")
public class AppCoreWorkDynamicController {
    @Autowired
    private CoreWorkDynamicService coreWorkDynamicService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        String workId = (String) params.get("workId");
        if(StringUtils.isNotBlank(workId)) {
     	   PageUtils page = coreWorkDynamicService.queryPage(params);
     	   
     	   return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{dynamicId}")
    public R info(@PathVariable("dynamicId") String dynamicId){
        CoreWorkDynamicEntity workDynamic = coreWorkDynamicService.selectById(dynamicId);

        return R.ok().put("workDynamic", workDynamic);
    }

}
