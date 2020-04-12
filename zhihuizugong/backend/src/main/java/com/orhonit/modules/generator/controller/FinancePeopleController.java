package com.orhonit.modules.generator.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.entity.FinancePeopleEntity;
import com.orhonit.modules.generator.service.FinancePeopleService;
import com.orhonit.common.utils.R;



/**
 * 财务管理人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:02
 */
@RestController
@RequestMapping("generator/financepeople")
public class FinancePeopleController extends AbstractController {
    @Autowired
    private FinancePeopleService financePeopleService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("generator:financepeople:list")
    public R list(@RequestParam Map<String, Object> params){
        List<FinancePeopleEntity> page = financePeopleService.queryList(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 根据财务管理主表查询所属所有人员列表
     */
    @GetMapping("/allList/{financeId}")
//    @RequiresPermissions("generator:financepeople:list")
    public R allList(@PathVariable("financeId") String financeId){
        List<FinancePeopleEntity> List = financePeopleService.allList(financeId);

        return R.ok().put("List", List);
    }
    
    /**
     * 查询登录用户所属科室下的人员列表
     */
    @GetMapping("/lowerList/{lowerId}")
//    @RequiresPermissions("generator:financepeople:list")
    public R lowerList(@PathVariable("lowerId") Integer lowerId){
        List<FinancePeopleEntity> List = financePeopleService.lowerList(lowerId);

        return R.ok().put("List", List);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:financepeople:info")
    public R info(@PathVariable("id") Integer id){
		FinancePeopleEntity financePeople = financePeopleService.selectById(id);

        return R.ok().put("financePeople", financePeople);
    }

    /**
     * 保存
     */
    @PostMapping("/save/{userId}/{financeId}")
//    @RequiresPermissions("generator:financepeople:save")
    public R save(@PathVariable("userId") Integer[] userId,@PathVariable("financeId") String financeId){
		financePeopleService.insertAllPeople(userId,financeId);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:financepeople:update")
    public R update(@RequestBody FinancePeopleEntity financePeople){
		financePeopleService.updateById(financePeople);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}/{financeId}")
//    @RequiresPermissions("generator:financepeople:delete")
    public R delete(@PathVariable("id") Integer userId,@PathVariable("financeId") String financeId){
		financePeopleService.deletePeople(userId,financeId);

        return R.ok();
    }

}
