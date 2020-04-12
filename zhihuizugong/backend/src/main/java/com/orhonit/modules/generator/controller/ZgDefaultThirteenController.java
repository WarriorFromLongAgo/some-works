package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgDefaultThirteenEntity;
import com.orhonit.modules.generator.service.ZgDefaultThirteenService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 个人画像十三边型默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 11:25:16
 */
@RestController
@RequestMapping("generator/zgdefaultthirteen")
public class ZgDefaultThirteenController {
    @Autowired
    private ZgDefaultThirteenService zgDefaultThirteenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgdefaultthirteen:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgDefaultThirteenService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgdefaultthirteen:info")
    public R info(@PathVariable("id") String id){
		ZgDefaultThirteenEntity zgDefaultThirteen = zgDefaultThirteenService.selectById(id);

        return R.ok().put("zgDefaultThirteen", zgDefaultThirteen);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgdefaultthirteen:save")
    public R save(@RequestBody ZgDefaultThirteenEntity zgDefaultThirteen){
		zgDefaultThirteenService.save(zgDefaultThirteen);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgdefaultthirteen:update")
    public R update(@RequestBody ZgDefaultThirteenEntity zgDefaultThirteen){
		zgDefaultThirteenService.updateById(zgDefaultThirteen);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgdefaultthirteen:delete")
    public R delete(String id){
		zgDefaultThirteenService.deleteById(id);

        return R.ok();
    }

}
