package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgNearlyThreeYearsEntity;
import com.orhonit.modules.generator.service.ZgNearlyThreeYearsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 近三年考核情况,奖惩
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-19 16:50:11
 */
@RestController
@RequestMapping("generator/zgnearlythreeyears")
public class ZgNearlyThreeYearsController {
    @Autowired
    private ZgNearlyThreeYearsService zgNearlyThreeYearsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgnearlythreeyears:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgNearlyThreeYearsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgnearlythreeyears:info")
    public R info(@PathVariable("id") Integer id){
		ZgNearlyThreeYearsEntity zgNearlyThreeYears = zgNearlyThreeYearsService.selectById(id);

        return R.ok().put("zgNearlyThreeYears", zgNearlyThreeYears);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgnearlythreeyears:save")
    public R save(@RequestBody List<ZgNearlyThreeYearsEntity> entityList){
		zgNearlyThreeYearsService.save(entityList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgnearlythreeyears:update")
    public R update(@RequestBody ZgNearlyThreeYearsEntity zgNearlyThreeYears){
		zgNearlyThreeYearsService.updateById(zgNearlyThreeYears);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:zgnearlythreeyears:delete")
    public R delete(Integer id){
		zgNearlyThreeYearsService.deleteById(id);

        return R.ok();
    }

}
