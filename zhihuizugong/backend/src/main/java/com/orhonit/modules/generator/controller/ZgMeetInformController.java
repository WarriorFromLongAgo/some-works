package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.dao.ZgMeetInformDao;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.service.ZgMeetInformService;
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
@RestController
@RequestMapping("generator/zgmeetinform")
public class ZgMeetInformController extends AbstractController{
    @Autowired
    private ZgMeetInformService zgMeetInformService;
    @Autowired
    private ZgMeetInformDao zgMeetInformDao;

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
        zgMeetInformDao.updateReadType(id,getUserId());
		ZgMeetInformEntity zgMeetInform = zgMeetInformService.selectById(id);

        return R.ok().put("zgMeetInform", zgMeetInform);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgmeetinform:save")
    public R save(@RequestBody ZgMeetInformEntity zgMeetInform){
        Long userId = getUserId();
        zgMeetInform.setUserId(userId);
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
    public List<Map<String, Object>> findAllUser(){
        Long userId = getUserId();
        return zgMeetInformService.findAllUser(userId);
    }


}
