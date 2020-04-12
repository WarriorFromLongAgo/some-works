package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreWorkCrewEntity;
import com.orhonit.modules.generator.service.CoreWorkCrewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 工作队全队人员
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-10 10:11:20
 */
@RestController
@RequestMapping("generator/coreworkcrew")
public class CoreWorkCrewController {
    @Autowired
    private CoreWorkCrewService coreWorkCrewService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreworkcrew:list")
    public R list(@RequestParam Map<String, Object> params) {
        String serveId = (String) params.get("serveId");
        if (StringUtils.isNotBlank(serveId)) {
            PageUtils page = coreWorkCrewService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{serveId}")
//    @RequiresPermissions("generator:coreworkcrew:info")
    public R info(@PathVariable("serveId") String serveId) {
        List<CoreWorkCrewEntity> coreWorkCrew = coreWorkCrewService.getByServeId(serveId);

        return R.ok().put("coreWorkCrew", coreWorkCrew);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreworkcrew:save")
    public R save(@RequestBody List<CoreWorkCrewEntity> coreWorkCrew) {
        coreWorkCrewService.insertBatch(coreWorkCrew);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreworkcrew:update")
    public R update(@RequestBody List<CoreWorkCrewEntity> coreWorkCrew,@RequestParam String serveId) {
        if(serveId != null) {
            coreWorkCrewService.deleteByServeId(serveId);
        }
        coreWorkCrewService.insertBatch(coreWorkCrew);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("generator:coreworkcrew:delete")
    public R delete(@RequestBody Integer[] ids) {
        coreWorkCrewService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
