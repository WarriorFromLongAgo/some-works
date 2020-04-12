package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgTaskEntity;
import com.orhonit.modules.generator.service.ZgTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 承诺践诺/工作任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:05:31
 */
@RestController
@RequestMapping("/app/generator/zgtask")
public class AppZgTaskController {
    @Autowired
    private ZgTaskService zgTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgtask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgtask:info")
    public R info(@PathVariable("id") Integer id){
    	ZgTaskEntity zgTask = zgTaskService.selectTaskInfo(id,"");

        return R.ok().put("zgTask", zgTask);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgtask:save")
    public R save(@RequestBody ZgTaskEntity zgTask){
		zgTaskService.save(zgTask);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgtask:update")
    public R update(@RequestBody ZgTaskEntity zgTask){
		zgTaskService.updateById(zgTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:zgtask:delete")
    public R delete(@PathVariable("id")Integer id){
		zgTaskService.deleteTask(id);
        return R.ok();
    }
    /**
     * 修改完成状态
     */
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.PUT)
   // @RequiresPermissions("generator:zgtask:save")
    public R updateStatus(@PathVariable("id")Integer id){
		zgTaskService.updateStatus(id);
        return R.ok();
    }
    /**
     * 统计列表
     */
    @RequestMapping("/statistiList")
    //@RequiresPermissions("generator:zgtask:list")
    public R statistiList(@RequestParam Map<String, Object> params){
    	Map<String, Object> statis = zgTaskService.statistiList(params);
        if(statis.size() > 0) {
        	return R.ok().put("statis", statis);
        }
        return R.error();
    }

}
