package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.service.ZgMeetPeopleService;





/**
 * 参会人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 16:28:47
 */
@RestController
@RequestMapping("generator/zgmeetpeople")
public class ZgMeetPeopleController {
    @Autowired
    private ZgMeetPeopleService zgMeetPeopleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgmeetpeople:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgMeetPeopleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgmeetpeople:info")
    public R info(@PathVariable("id") String id){
		ZgMeetPeopleEntity zgMeetPeople = zgMeetPeopleService.selectById(id);

        return R.ok().put("zgMeetPeople", zgMeetPeople);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgmeetpeople:save")
    public R save(@RequestBody ZgMeetPeopleEntity zgMeetPeople){
    	List<ZgMeetPeopleEntity> list = zgMeetPeopleService.findPeo(zgMeetPeople.getMeetId(), zgMeetPeople.getUserId());
    	if (list != null && list.size() > 0) {
			return R.ok("该用户已添加");
		}else {
			zgMeetPeopleService.save(zgMeetPeople);
		}
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgmeetpeople:update")
    public R update(@RequestBody ZgMeetPeopleEntity zgMeetPeople){
		zgMeetPeopleService.updateById(zgMeetPeople);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:zgmeetpeople:delete")
    public R delete(String id){
		zgMeetPeopleService.deleteById(id);

        return R.ok();
    }

    /**
     * 用户参加的会议列表
     * @param userId
     * @return
     */
    @RequestMapping("/findJoinMeetList")
    public List<Map<String,Object>> findJoinMeetList(Long userId){
        return zgMeetPeopleService.findJoinMeetList(userId);
    }

}
