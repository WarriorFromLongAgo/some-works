package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgMeetRecordEntity;
import com.orhonit.modules.generator.service.ZgMeetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 会议通知和记录总结中间表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 17:17:20
 */
@RestController
@RequestMapping("/app/generator/zgmeetrecord")
public class AppZgMeetRecordController {
    @Autowired
    private ZgMeetRecordService zgMeetRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("generator:zgmeetrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgMeetRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgmeetrecord:info")
    public R info(@PathVariable("id") String id){
		ZgMeetRecordEntity zgMeetRecord = zgMeetRecordService.selectById(id);

        return R.ok().put("zgMeetRecord", zgMeetRecord);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgmeetrecord:save")
    public R save(@RequestBody ZgMeetRecordEntity zgMeetRecord){
		zgMeetRecordService.save(zgMeetRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgmeetrecord:update")
    public R update(@RequestBody ZgMeetRecordEntity zgMeetRecord){
		zgMeetRecordService.updateById(zgMeetRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgmeetrecord:delete")
    public R delete(String id){
		zgMeetRecordService.deleteById(id);

        return R.ok();
    }

    /**
     * 返回单个会议记录
     * @param params
     * @return
     */
    @RequestMapping("/findInfo")
    public Map<String, List<ZgMeetRecordEntity>> findInfo(@RequestParam Map<String,Object> params){
        return zgMeetRecordService.findInfo(params);
    }

}
