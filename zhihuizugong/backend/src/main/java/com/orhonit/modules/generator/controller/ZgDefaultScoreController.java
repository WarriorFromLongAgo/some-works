package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgDefaultScoreEntity;
import com.orhonit.modules.generator.service.ZgDefaultScoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 组工画像人员默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-18 15:58:12
 */
@RestController
@RequestMapping("generator/zgdefaultscore")
public class ZgDefaultScoreController {
    @Autowired
    private ZgDefaultScoreService zgDefaultScoreService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("generator:zgdefaultscore:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgDefaultScoreService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgdefaultscore:info")
    public R info(@PathVariable("id") String id){
		ZgDefaultScoreEntity zgDefaultScore = zgDefaultScoreService.selectById(id);

        return R.ok().put("zgDefaultScore", zgDefaultScore);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgdefaultscore:save")
    public R save(@RequestBody ZgDefaultScoreEntity zgDefaultScore){
		zgDefaultScoreService.save(zgDefaultScore);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgdefaultscore:update")
    public R update(@RequestBody ZgDefaultScoreEntity zgDefaultScore){
        zgDefaultScore.setUpdateTime(new Date());
		zgDefaultScoreService.updateById(zgDefaultScore);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgdefaultscore:delete")
    public R delete(String id){
		zgDefaultScoreService.deleteById(id);

        return R.ok();
    }

}
