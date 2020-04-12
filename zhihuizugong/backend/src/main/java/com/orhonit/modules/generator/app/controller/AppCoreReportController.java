package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreReportEntity;
import com.orhonit.modules.generator.service.CoreReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 每季报告表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@RestController
@RequestMapping("app/CoreReport")
public class AppCoreReportController {
    @Autowired
    private CoreReportService coreReportService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:corereport:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreReportService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{reportId}")
//    @RequiresPermissions("generator:corereport:info")
    public R info(@PathVariable("reportId") String reportId){
		CoreReportEntity coreReport = coreReportService.selectById(reportId);

        return R.ok().put("coreReport", coreReport);
    }

}
