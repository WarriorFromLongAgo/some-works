package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreLeaveEntity;
import com.orhonit.modules.generator.service.CoreLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiaobai
 * @date 2019-05-10 15:14:54
 */
@RestController
@RequestMapping("app/Coreleave")
public class AppCoreLeaveController {
    @Autowired
    private CoreLeaveService coreLeaveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = coreLeaveService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        CoreLeaveEntity coreLeave = coreLeaveService.selectById(id);

        return R.ok().put("coreLeave", coreLeave);
    }

}
