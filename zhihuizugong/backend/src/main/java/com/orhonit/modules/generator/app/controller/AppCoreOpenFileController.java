package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreOpenFileEntity;
import com.orhonit.modules.generator.service.CoreOpenFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 党务公开附件表
 *
 * @author xiaobai
 * @date 2019-05-22 17:55:44
 */
@RestController
@RequestMapping("app/coreopenfile")
public class AppCoreOpenFileController {
    @Autowired
    private CoreOpenFileService coreOpenFileService;

    /**
     * 信息
     */
    @RequestMapping("/info/{openId}")
//    @RequiresPermissions("generator:coreopenfile:info")
    public R info(@PathVariable("openId") String openId){
		List<CoreOpenFileEntity> coreOpenFile = coreOpenFileService.getById(openId);

        return R.ok().put("coreOpenFile", coreOpenFile);
    }

}
