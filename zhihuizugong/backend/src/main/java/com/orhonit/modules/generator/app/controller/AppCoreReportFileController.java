package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreReportFileEntity;
import com.orhonit.modules.generator.service.CoreReportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 党务公开附件表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@RestController
@RequestMapping("app/corereportfile")
public class AppCoreReportFileController {
    @Autowired
    private CoreReportFileService coreReportFileService;

    /**
     * 信息
     */
    @RequestMapping("/info/{reportId}")
//    @RequiresPermissions("generator:corereportfile:info")
    public R info(@PathVariable("reportId") String reportId){
		List<CoreReportFileEntity> coreReportFile = coreReportFileService.getById(reportId);

        return R.ok().put("coreReportFile", coreReportFile);
    }

}
