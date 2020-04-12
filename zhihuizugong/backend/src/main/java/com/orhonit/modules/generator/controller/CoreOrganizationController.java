package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreOrganizationEntity;
import com.orhonit.modules.generator.service.CoreOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 党组织表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:01:54
 */
@RestController
@RequestMapping("generator/coreorganization")
public class CoreOrganizationController {
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

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreorganization:save")
    public R save(@RequestBody CoreOrganizationEntity coreOrganization){
        String organizationId = coreOrganization.getOrganizationId();
        coreOrganization.setOrganizationId(organizationId);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date.format(new Date());
        coreOrganization.setCreateTime(time);
		coreOrganizationService.insertOrganization(coreOrganization);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreorganization:update")
    public R update(@RequestBody CoreOrganizationEntity coreOrganization){
		coreOrganizationService.updateById(coreOrganization);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{organizationId}")
//    @RequiresPermissions("generator:coreorganization:delete")
    public R delete(@PathVariable("organizationId") String organizationId){
		coreOrganizationService.deleteById(organizationId);

        return R.ok();
    }

}
