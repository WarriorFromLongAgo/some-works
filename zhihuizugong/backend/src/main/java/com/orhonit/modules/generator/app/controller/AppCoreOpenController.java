package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreOpenEntity;
import com.orhonit.modules.generator.service.CoreOpenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 党务公开表
 * @author xiaobai
 * @date 2019-05-18 15:14:02
 */
@RestController
@RequestMapping("app/coreopen")
public class AppCoreOpenController {
    @Autowired
    private CoreOpenService coreOpenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreopen:list")
    public R list(@RequestParam Map<String, Object> params){
        String openType = (String) params.get("openType");
        if(StringUtils.isNotBlank(openType)) {
            PageUtils page = coreOpenService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{openId}")
//    @RequiresPermissions("generator:coreopen:info")
    public R info(@PathVariable("openId") String openId){
		CoreOpenEntity coreOpen = coreOpenService.selectById(openId);

        return R.ok().put("coreOpen", coreOpen);
    }

}
