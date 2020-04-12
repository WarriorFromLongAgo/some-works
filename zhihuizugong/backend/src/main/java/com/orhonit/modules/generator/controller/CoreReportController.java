package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreReportEntity;
import com.orhonit.modules.generator.service.CoreReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 每季报告表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@RestController
@RequestMapping("generator/CoreReport")
public class CoreReportController {
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

    /**
     * 保存
     */
    @RequestMapping(value = "/save" , method = RequestMethod.POST)
//    @RequiresPermissions("generator:corereport:save")
    public R save(@RequestBody CoreReportEntity coreReport){
        String reportId = coreReport.getReportId();
        coreReport.setReportId(reportId);
		coreReportService.insertReport(coreReport);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:corereport:update")
    public R update(@RequestBody CoreReportEntity coreReport){
		coreReportService.updateById(coreReport);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{reportId}")
//    @RequiresPermissions("generator:corereport:delete")
    public R delete(@PathVariable("reportId") String reportId){
		coreReportService.deleteByReportId(reportId);

        return R.ok();
    }

}
