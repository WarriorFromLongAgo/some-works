package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreOrganizationEntity;
import com.orhonit.modules.generator.service.CoreOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 党组织表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:01:54
 */
@RestController
@RequestMapping("app/coreorganization")
public class AppCoreOrganizationController {
    @Autowired
    private CoreOrganizationService coreOrganizationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreorganization:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreOrganizationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{organizationId}")
//    @RequiresPermissions("generator:coreorganization:info")
    public R info(@PathVariable("organizationId") String organizationId){
		CoreOrganizationEntity coreOrganization = coreOrganizationService.selectById(organizationId);

        return R.ok().put("coreOrganization", coreOrganization);
    }

}
