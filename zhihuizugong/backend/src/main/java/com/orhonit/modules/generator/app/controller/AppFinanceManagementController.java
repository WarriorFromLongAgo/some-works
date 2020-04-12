package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.controller.AbstractController;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.entity.FinanceManagementEntity;
import com.orhonit.modules.generator.service.FinanceManagementService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static com.orhonit.common.utils.ShiroUtils.getUserId;


/**
 * 财务管理主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:11:59
 */
@RestController
@RequestMapping("/app/generator/financemanagement")
public class AppFinanceManagementController{
    @Autowired
    private FinanceManagementService financeManagementService;

    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

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
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(financeManagement.getCreateBy());
        financeManagement.setCreateTime(new Date());
    	financeManagement.setCreateName(userInfo.getUserTrueName());
    	financeManagement.setLowerId(userInfo.getLowerId());
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
