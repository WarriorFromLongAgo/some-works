package com.orhonit.modules.generator.app.controller;

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
@RequestMapping("app/coreappraisal")
public class AppCoreAppraisalController {
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

}
