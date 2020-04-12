package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgInformationEntity;
import com.orhonit.modules.generator.service.ZgInformationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 资讯表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-15 17:40:51
 */
@RestController
@RequestMapping("generator/zginformation")
public class ZgInformationController {
    @Autowired
    private ZgInformationService zgInformationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zginformation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgInformationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zginformation:info")
    public R info(@PathVariable("id") Integer id){
		ZgInformationEntity zgInformation = zgInformationService.selectById(id);

        return R.ok().put("zgInformation", zgInformation);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zginformation:save")
    public R save(@RequestBody ZgInformationEntity zgInformation){
        zgInformation.setCreateTime(new Date());
		zgInformationService.insert(zgInformation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zginformation:update")
    public R update(@RequestBody ZgInformationEntity zgInformation){
		zgInformationService.updateById(zgInformation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zginformation:delete")
    public R delete(Integer id){
		zgInformationService.deleteById(id);

        return R.ok();
    }

    /**
     * 按创建时间分类查询全部
     * @param params
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(@RequestParam Map<String, Object> params){
        PageUtils all = zgInformationService.findAll(params);
        return  R.ok().put("results",all);
    }

}
