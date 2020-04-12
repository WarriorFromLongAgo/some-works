package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.EjSchedulingRecordEntity;
import com.orhonit.modules.generator.service.EjSchedulingRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 调度记录表(完成情况/督办情况)
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@RestController
@RequestMapping("generator/ejschedulingrecord")
public class EjSchedulingRecordController {
    @Autowired
    private EjSchedulingRecordService ejSchedulingRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:ejschedulingrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ejSchedulingRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ejschedulingrecord:info")
    public R info(@PathVariable("id") Integer id){
		EjSchedulingRecordEntity ejSchedulingRecord = ejSchedulingRecordService.selectById(id);

        return R.ok().put("ejSchedulingRecord", ejSchedulingRecord);
    }

    /**
     * 添加完成情况/督办情况
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:ejschedulingrecord:save")
    public R save(@RequestBody EjSchedulingRecordEntity ejSchedulingRecord){
        ejSchedulingRecordService.insert(ejSchedulingRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ejschedulingrecord:update")
    public R update(@RequestBody EjSchedulingRecordEntity ejSchedulingRecord){
		ejSchedulingRecordService.updateById(ejSchedulingRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ejschedulingrecord:delete")
    public R delete(@RequestBody Integer[] ids){
		ejSchedulingRecordService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
