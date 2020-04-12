package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CorePoliticalEntity;
import com.orhonit.modules.generator.service.CorePoliticalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 生活时时讲
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 16:36:25
 */
@RestController
@RequestMapping("app/corepolitical")
public class AppCorePoliticalController {
    @Autowired
    private CorePoliticalService corePoliticalService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
//    @RequiresPermissions("generator:corepolitical:list")
    public R list(@RequestParam Map<String, Object> params){
        String politicalType = (String) params.get("politicalType");
        if(StringUtils.isNotBlank(politicalType)) {
            PageUtils page = corePoliticalService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}" , method = RequestMethod.GET)
//    @RequiresPermissions("generator:corepolitical:info")
    public R info(@PathVariable("id") Integer id){
		CorePoliticalEntity corePolitical = corePoliticalService.selectById(id);

        return R.ok().put("corePolitical", corePolitical);
    }

}
