package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.entity.FinancePeopleEntity;
import com.orhonit.modules.generator.entity.OverseePeopleEntity;
import com.orhonit.modules.generator.service.OverseePeopleService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 领导督办人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@RestController
@RequestMapping("generator/overseepeople")
public class OverseePeopleController {
    @Autowired
    private OverseePeopleService overseePeopleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:overseepeople:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = overseePeopleService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 根据领导督办主表查询所接受任务的所有人员列表
     */
    @GetMapping("/allList/{overseeId}")
//    @RequiresPermissions("generator:financepeople:list")
    public R allList(@PathVariable("overseeId") String overseeId){
        List<OverseePeopleEntity> List = overseePeopleService.allList(overseeId);

        return R.ok().put("List", List);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{peopleId}")
//    @RequiresPermissions("generator:overseepeople:info")
    public R info(@PathVariable("peopleId") Integer peopleId){
		OverseePeopleEntity overseePeople = overseePeopleService.selectById(peopleId);

        return R.ok().put("overseePeople", overseePeople);
    }

    /**
     * 保存
     */
    @RequestMapping(value="/save/{userId}/{overseeId}",method=RequestMethod.POST)
//    @RequiresPermissions("generator:overseepeople:save")
    public R save(@PathVariable("userId") Long userId,@PathVariable("overseeId") String overseeId){
        return overseePeopleService.insertAllPeople(userId,overseeId);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:overseepeople:update")
    public R update(@RequestBody OverseePeopleEntity overseePeople){
		overseePeopleService.updateById(overseePeople);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{peopleId}/{overseeId}")
//    @RequiresPermissions("generator:overseepeople:delete")
    public R delete(@PathVariable("peopleId") Integer userId,@PathVariable("overseeId") String overseeId){
		overseePeopleService.deletePeople(userId,overseeId);
        return R.ok();
    }

}
