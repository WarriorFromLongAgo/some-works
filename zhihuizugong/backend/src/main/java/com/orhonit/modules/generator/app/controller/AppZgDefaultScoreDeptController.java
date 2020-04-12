package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgDefaultScoreDeptEntity;
import com.orhonit.modules.generator.service.ZgDefaultScoreDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * 组工画像科室默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-19 14:43:39
 */
@RestController
@RequestMapping("/app/generator/zgdefaultscoredept")
public class AppZgDefaultScoreDeptController {
    @Autowired
    private ZgDefaultScoreDeptService zgDefaultScoreDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgdefaultscoredept:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgDefaultScoreDeptService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgdefaultscoredept:info")
    public R info(@PathVariable("id") String id){
		ZgDefaultScoreDeptEntity zgDefaultScoreDept = zgDefaultScoreDeptService.selectById(id);

        return R.ok().put("zgDefaultScoreDept", zgDefaultScoreDept);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgdefaultscoredept:save")
    public R save(@RequestBody ZgDefaultScoreDeptEntity zgDefaultScoreDept){
		zgDefaultScoreDeptService.save(zgDefaultScoreDept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgdefaultscoredept:update")
    public R update(@RequestBody ZgDefaultScoreDeptEntity zgDefaultScoreDept){
        zgDefaultScoreDept.setUpdateTime(new Date());
		zgDefaultScoreDeptService.updateById(zgDefaultScoreDept);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgdefaultscoredept:delete")
    public R delete( String id){
		zgDefaultScoreDeptService.deleteById(id);

        return R.ok();
    }

}
