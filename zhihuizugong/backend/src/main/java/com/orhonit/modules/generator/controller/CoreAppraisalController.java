package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreAppraisalEntity;
import com.orhonit.modules.generator.service.CoreAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 每季评比表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@RestController
@RequestMapping("generator/coreappraisal")
public class CoreAppraisalController {
    @Autowired
    private CoreAppraisalService coreAppraisalService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreappraisal:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreAppraisalService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{appraisalId}")
//    @RequiresPermissions("generator:coreappraisal:info")
    public R info(@PathVariable("appraisalId") String appraisalId){
		CoreAppraisalEntity coreAppraisal = coreAppraisalService.selectById(appraisalId);

        return R.ok().put("coreAppraisal", coreAppraisal);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreappraisal:save")
    public R save(@RequestBody CoreAppraisalEntity coreAppraisal){
        String appraisalId = coreAppraisal.getAppraisalId();
        coreAppraisal.setAppraisalId(appraisalId);
		coreAppraisalService.save(coreAppraisal);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreappraisal:update")
    public R update(@RequestBody CoreAppraisalEntity coreAppraisal){
		coreAppraisalService.updateById(coreAppraisal);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{appraisalId}")
//    @RequiresPermissions("generator:coreappraisal:delete")
    public R delete(@PathVariable("appraisalId") String appraisalId){
		coreAppraisalService.deleteByAppraisalId(appraisalId);

        return R.ok();
    }

}
