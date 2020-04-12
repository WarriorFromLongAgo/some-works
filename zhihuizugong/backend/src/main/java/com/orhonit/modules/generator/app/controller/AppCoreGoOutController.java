package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.controller.AbstractController;
import com.orhonit.modules.generator.entity.CoreGoOutEntity;
import com.orhonit.modules.generator.service.CoreGoOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 外出登记表
 *
 * @author xiaobai
 * @date 2019-05-10 13:48:31
 */
@RestController
@RequestMapping("app/Coregoout")
public class AppCoreGoOutController extends AbstractController {
    @Autowired
    private CoreGoOutService coreGoOutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = coreGoOutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        CoreGoOutEntity coreGoOut = coreGoOutService.selectById(id);

        return R.ok().put("coreGoOut", coreGoOut);
    }

}
