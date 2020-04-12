package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgThirteenMaxEntity;
import com.orhonit.modules.generator.service.ZgThirteenMaxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 个人十三边型画像最高值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 15:30:46
 */
@RestController
@RequestMapping("generator/zgthirteenmax")
public class ZgThirteenMaxController {
    @Autowired
    private ZgThirteenMaxService zgThirteenMaxService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgthirteenmax:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgThirteenMaxService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgthirteenmax:info")
    public R info(@PathVariable("id") String id){
		ZgThirteenMaxEntity zgThirteenMax = zgThirteenMaxService.selectById(id);

        return R.ok().put("zgThirteenMax", zgThirteenMax);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgthirteenmax:save")
    public R save(@RequestBody ZgThirteenMaxEntity zgThirteenMax){
		zgThirteenMaxService.save(zgThirteenMax);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgthirteenmax:update")
    public R update(@RequestBody ZgThirteenMaxEntity zgThirteenMax){
		zgThirteenMaxService.updateById(zgThirteenMax);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgthirteenmax:delete")
    public R delete(String id){
		zgThirteenMaxService.deleteById(id);

        return R.ok();
    }

}
