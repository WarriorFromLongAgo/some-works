package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.PublicNewsEntity;
import com.orhonit.modules.generator.service.PublicNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 智慧e家人才家园
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-14 14:41:18
 */
@RestController
@RequestMapping("generator/publicnews")
public class PublicNewsController extends AbstractController{
    @Autowired
    private PublicNewsService publicNewsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:publicnews:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = publicNewsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:publicnews:info")
    public R info(@PathVariable("id") Integer id){
		PublicNewsEntity publicNews = publicNewsService.selectById(id);

        return R.ok().put("publicNews", publicNews);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:publicnews:save")
    public R save(@RequestBody PublicNewsEntity publicNews){
        publicNews.setCreateTime(new Date());
        publicNews.setCreateUserId(getUserId());
		publicNewsService.insert(publicNews);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:publicnews:update")
    public R update(@RequestBody PublicNewsEntity publicNews){
		publicNewsService.updateById(publicNews);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:publicnews:delete")
    public R delete(Integer id){
		publicNewsService.deleteById(id);

        return R.ok();
    }

}
