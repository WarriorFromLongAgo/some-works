package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreSystemEntity;
import com.orhonit.modules.generator.service.CoreSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 党建制度表
 *
 * @author xiaobai
 * @date 2019-05-18 11:14:27
 */
@RestController
@RequestMapping("generator/coresystem")
public class CoreSystemController {
    @Autowired
    private CoreSystemService coreSystemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coresystem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreSystemService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list1")
//    @RequiresPermissions("generator:coresystem:list")
    public R list1(){
        List<CoreSystemEntity> list = coreSystemService.queryPage1();

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("generator:coresystem:info")
    public R info(@PathVariable("id") int id){
		CoreSystemEntity coreSystem = coreSystemService.selectById(id);

        return R.ok().put("coreSystem", coreSystem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coresystem:save")
    public R save(@RequestBody CoreSystemEntity coreSystem){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date.format(new Date());
        coreSystem.setCreateTime(time);
		coreSystemService.insert(coreSystem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coresystem:update")
    public R update(@RequestBody CoreSystemEntity coreSystem){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date.format(new Date());
        coreSystem.setCreateTime(time);
		coreSystemService.updateById(coreSystem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:coresystem:delete")
    public R delete(@PathVariable("id") Integer id){
		coreSystemService.deleteById(id);

        return R.ok();
    }

}
