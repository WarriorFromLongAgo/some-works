package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgTaskFinishEntity;
import com.orhonit.modules.generator.service.ZgTaskFinishService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * 工作任务副表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:28:24
 */
@RestController
@RequestMapping("generator/zgtaskfinish")
public class ZgTaskFinishController {
    @Autowired
    private ZgTaskFinishService zgTaskFinishService;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgtaskfinish:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgTaskFinishService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgtaskfinish:info")
    public R info(@PathVariable("id") Integer id){
		ZgTaskFinishEntity zgTaskFinish = zgTaskFinishService.selectById(id);

        return R.ok().put("zgTaskFinish", zgTaskFinish);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgtaskfinish:save")
    public R save(@RequestBody ZgTaskFinishEntity zgTaskFinish){
    	SysUserEntity user = sysUserDao.selectInfo(zgTaskFinish.getCreateBy());
    	if(zgTaskFinish.getContentType() != null && zgTaskFinish.getContentType().equals("2")){
            zgTaskFinish.setSchedule(1);
        }
    	zgTaskFinish.setCreateBy(user.getUserId());
    	zgTaskFinish.setCreateName(user.getUserTrueName());
        zgTaskFinish.setHeadPortrait(user.getUserHeadPicture());
        zgTaskFinish.setCreateTime(new Date());
		zgTaskFinishService.insert(zgTaskFinish);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgtaskfinish:update")
    public R update(@RequestBody ZgTaskFinishEntity zgTaskFinish){
		zgTaskFinishService.updateById(zgTaskFinish);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:zgtaskfinish:delete")
    public R delete(Integer id){
		zgTaskFinishService.deleteById(id);

        return R.ok();
    }

}
