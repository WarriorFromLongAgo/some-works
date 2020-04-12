package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreAppraisalFileEntity;
import com.orhonit.modules.generator.service.CoreAppraisalFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 评比附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 17:20:44
 */
@RestController
@RequestMapping("app/coreappraisalfile")
public class AppCoreAppraisalFileController {
    @Autowired
    private CoreAppraisalFileService coreAppraisalFileService;

    /**
     * 信息
     */
    @RequestMapping("/info/{appraisalId}")
//    @RequiresPermissions("generator:coreappraisalfile:info")
    public R info(@PathVariable("appraisalId") String appraisalId){
		List<CoreAppraisalFileEntity> coreAppraisalFile = coreAppraisalFileService.getByAppraisalId(appraisalId);

        return R.ok().put("coreAppraisalFile", coreAppraisalFile);
    }

}
