package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.service.ZgMeetInformService;
import com.orhonit.modules.generator.service.ZgMeetPeopleService;
import com.orhonit.modules.sys.controller.AbstractController;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 会议通知
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 15:05:24
 */
/**
 * Title: AppZgMeetInformController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
/**
 * Title: AppZgMeetInformController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
/**
 * Title: AppZgMeetInformController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
/**
 * Title: AppZgMeetInformController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
@RestController
@RequestMapping("/app/generator/zgmeetinform")
public class AppZgMeetInformController extends AbstractController{
    @Autowired
    private ZgMeetInformService zgMeetInformService;
    @Autowired
    SysUserService userService;
    @Autowired
    ZgMeetPeopleService zgMeetPeopleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgmeetinform:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgMeetInformService.findAll(params);

        return R.ok().put("page", page);
    }
    /**
     * 返回所有会议通知
     * @return
     */
    @RequestMapping("/findMeetList")
    public List<Map<String, Object>> findMeetList(){
    	return zgMeetInformService.findMeetList();
    } 

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgmeetinform:info")
    public R info(@PathVariable("id") String id){
		ZgMeetInformEntity zgMeetInform = zgMeetInformService.selectById(id);

        return R.ok().put("zgMeetInform", zgMeetInform);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgmeetinform:save")
    public R save(@RequestBody ZgMeetInformEntity zgMeetInform){
		zgMeetInformService.save(zgMeetInform);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgmeetinform:update")
    public R update(@RequestBody ZgMeetInformEntity zgMeetInform){
		zgMeetInformService.updateById(zgMeetInform);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("generator:zgmeetinform:delete")
    public R delete( String id){
		zgMeetInformService.del(id);

        return R.ok();
    }
    /**
     * 人员列表
     * @return
     */
    @RequestMapping("/findAllUser")
    public List<Map<String, Object>> findAllUser(Long userId){
    	return zgMeetInformService.findAllUser(userId);
    }
    
    
    /**
     * TODO   APP端 [我的]模块 
     * 查询所有未读条数&&未读列表
     * @param userId
     * @return
     */
    @GetMapping("/count")
    public R count(@RequestParam Map<String,Object>params) {
    	params.put("userId", getUserId().intValue());
    	@SuppressWarnings("rawtypes")
		Map page = zgMeetInformService.unreadList(params);
    	return R.ok().put("map",page);
    }
    
    
    /**
     * TODO    APP端 [我的]模块 
     * 我的信息
     * @param userId
     * @return
     */
    @GetMapping("/my/message")
    public R mymessage() {
    	Integer userId = getUserId().intValue();
    	return R.ok().put("user", userService.selectById(userId));
    }
    
    /**
     * 我的信息  更新
     * @param entity
     * @return
     */
    @PutMapping("/my/updatemessage")
    public R updatemessage(@RequestBody SysUserEntity entity) {
    	 userService.update(entity);
    	return R.ok().put("update", "更新成功!");
    }
    
    
    /**
     * TODO    APP端 [我的]模块
     * 更改已读状态
     * @param meetId
     * @return
     */
    @PutMapping("/up/type")
    public R updateType(String meetId) {
    	Integer userId = getUserId().intValue();
    	zgMeetPeopleService.updateReadType(meetId,userId);
    	return R.ok();
    }

}
