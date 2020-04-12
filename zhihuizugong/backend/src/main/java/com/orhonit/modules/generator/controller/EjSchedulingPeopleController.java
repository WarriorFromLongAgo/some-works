package com.orhonit.modules.generator.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.EjSchedulingPeopleEntity;
import com.orhonit.modules.generator.service.EjSchedulingPeopleService;
import com.orhonit.modules.generator.service.EjSchedulingRecordService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;




/**
 * 调度参会人
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@RestController
@RequestMapping("generator/ejschedulingpeople")
public class EjSchedulingPeopleController {
    @Autowired
    private EjSchedulingPeopleService ejSchedulingPeopleService;
    @Autowired
    private EjSchedulingRecordService ejSchedulingRecordService;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:ejschedulingpeople:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ejSchedulingPeopleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ejschedulingpeople:info")
    public R info(@PathVariable("id") Integer id){
		EjSchedulingPeopleEntity ejSchedulingPeople = ejSchedulingPeopleService.selectPeopleInfo(id);

        return R.ok().put("ejSchedulingPeople", ejSchedulingPeople);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("generator:ejschedulingpeople:save")
    public R save(@RequestBody List<EjSchedulingPeopleEntity> ejSchedulingPeople){
        if(ejSchedulingPeople.size() > 0){
            List<EjSchedulingPeopleEntity> peopleList= ejSchedulingPeopleService.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("scheduling_id ="+"'"+ejSchedulingPeople.get(0).getSchedulingId()+"'"));
            if(peopleList.size() > 0){
                ejSchedulingPeopleService.deleteBatchIds(peopleList);
            }
            for (EjSchedulingPeopleEntity people:ejSchedulingPeople) {
                SysUserEntity user = sysUserDao.selectInfo(people.getUserId());
                people.setUserName(user.getUserTrueName());
                people.setUserIdCard(user.getUserNickname());
                people.setHeadPortrait(user.getUserHeadPicture());
                ejSchedulingPeopleService.insert(people);
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ejschedulingpeople:update")
    public R update(@RequestBody EjSchedulingPeopleEntity ejSchedulingPeople){
		ejSchedulingPeopleService.updateById(ejSchedulingPeople);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ejschedulingpeople:delete")
    public R delete(@RequestBody Integer[] ids){
		ejSchedulingPeopleService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
