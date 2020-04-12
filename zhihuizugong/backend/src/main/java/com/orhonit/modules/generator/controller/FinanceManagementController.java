package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.entity.FinanceManagementEntity;
import com.orhonit.modules.generator.service.FinanceManagementService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 财务管理主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:11:59
 */
@RestController
@RequestMapping("generator/financemanagement")
public class FinanceManagementController extends AbstractController {
    @Autowired
    private FinanceManagementService financeManagementService;

    /**
     *列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("generator:financemanagement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = financeManagementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("generator:financemanagement:info")
    public R info(@PathVariable("id") String id){
		FinanceManagementEntity financeManagement = financeManagementService.selectFinanceById(id);

        return R.ok().put("financeManagement", financeManagement);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("generator:financemanagement:save")
    public R save(@RequestBody FinanceManagementEntity financeManagement){
    	financeManagement.setCreateTime(new Date());
    	financeManagement.setCreateBy(getUserId());
    	financeManagement.setCreateName(getUser().getUserTrueName());
    	financeManagement.setLowerId(getUser().getLowerId());
		financeManagementService.save(financeManagement);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//    @RequiresPermissions("generator:financemanagement:update")
    public R update(@RequestBody FinanceManagementEntity financeManagement){
		financeManagementService.updateById(financeManagement);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
//    @RequiresPermissions("generator:financemanagement:delete")
    public R delete(@PathVariable("id") String id){
		financeManagementService.deleteById(id);

        return R.ok();
    }

}
