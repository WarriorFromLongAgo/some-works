package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.EjSchedulingEntity;
import com.orhonit.modules.generator.service.EjSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;




/**
 * 调度主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@RestController
@RequestMapping("generator/ejscheduling")
public class EjSchedulingController extends AbstractController {
    @Autowired
    private EjSchedulingService ejSchedulingService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("generator:ejscheduling:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ejSchedulingService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("generator:ejscheduling:info")
    public R info(@PathVariable("id") String id){
        EjSchedulingEntity ejScheduling = ejSchedulingService.selectSchedulingInfo(id);

        return R.ok().put("ejScheduling", ejScheduling);
    }

    /**
     * 查询调度详细信息(参会人员的任务展示)
     */
    @GetMapping("/schedulingInfo/{id}")
//    @RequiresPermissions("generator:ejscheduling:info")
    public R schedulingInfo(@PathVariable("id") String id){
        EjSchedulingEntity ejScheduling = ejSchedulingService.selectschedulingTaskInfo(id,getUserId());

        return R.ok().put("ejSchedulingInfo", ejScheduling);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("generator:ejscheduling:save")
    public R save(@RequestBody EjSchedulingEntity ejScheduling){
		ejSchedulingService.save(ejScheduling);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//    @RequiresPermissions("generator:ejscheduling:update")
    public R update(@RequestBody EjSchedulingEntity ejScheduling){
		ejSchedulingService.updateById(ejScheduling);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
//    @RequiresPermissions("generator:ejscheduling:delete")
    public R delete(@PathVariable String id){
		ejSchedulingService.deleteById(id);

        return R.ok();
    }

}
