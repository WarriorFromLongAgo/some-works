package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreSystemEntity;
import com.orhonit.modules.generator.service.CoreSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 党建制度表
 *
 * @author xiaobai
 * @date 2019-05-18 11:14:27
 */
@RestController
@RequestMapping("app/coresystem")
public class AppCoreSystemController {
    @Autowired
    private CoreSystemService coreSystemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coresystem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreSystemService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("generator:coresystem:info")
    public R info(@PathVariable("id") int id){
		CoreSystemEntity coreSystem = coreSystemService.selectById(id);

        return R.ok().put("coreSystem", coreSystem);
    }

}
