package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreWorkCrewEntity;
import com.orhonit.modules.generator.service.CoreWorkCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 工作队全队人员
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-10 10:11:20
 */
@RestController
@RequestMapping("app/coreworkcrew")
public class AppCoreWorkCrewController {
    @Autowired
    private CoreWorkCrewService coreWorkCrewService;

    /**
     * 信息
     */
    @RequestMapping("/info/{serveId}")
//    @RequiresPermissions("generator:coreworkcrew:info")
    public R info(@PathVariable("serveId") String serveId) {
        List<CoreWorkCrewEntity> coreWorkCrew = coreWorkCrewService.getByServeId(serveId);

        return R.ok().put("coreWorkCrew", coreWorkCrew);
    }

}
