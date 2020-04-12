package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import com.orhonit.modules.generator.service.ZgRemarkService;





/**
 * 领导点评
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 11:33:57
 */
@RestController
@RequestMapping("generator/zgremark")
public class ZgRemarkController extends AbstractController{
    @Autowired
    private ZgRemarkService zgRemarkService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("generator:zgremark:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgRemarkService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
   // @RequiresPermissions("generator:zgremark:info")
    public R info(@PathVariable("id") String id){
		ZgRemarkEntity zgRemark = zgRemarkService.selectById(id);

        return R.ok().put("zgRemark", zgRemark);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgremark:save")
    public R save(@RequestBody ZgRemarkEntity zgRemark){
        Long userId = getUserId();
        zgRemark.setRemarkLeaderId(userId);
        zgRemarkService.save(zgRemark);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgremark:update")
    public R update(@RequestBody ZgRemarkEntity zgRemark){
		zgRemarkService.updateById(zgRemark);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgremark:delete")
    public R delete(String id){
		zgRemarkService.deleteById(id);

        return R.ok();
    }

}
