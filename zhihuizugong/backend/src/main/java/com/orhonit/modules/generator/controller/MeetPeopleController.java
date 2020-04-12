package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.MeetPeopleEntity;
import com.orhonit.modules.generator.service.MeetPeopleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 参加人员表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-12 15:00:48
 */
@RestController
@RequestMapping("generator/meetpeople")
public class MeetPeopleController {
    @Autowired
    private MeetPeopleService meetPeopleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:meetpeople:list")
    public R list(@RequestParam Map<String, Object> params) {
        String meetId = (String) params.get("meetId");
        if (StringUtils.isNotBlank(meetId)) {
            PageUtils page = meetPeopleService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
//    @RequiresPermissions("generator:meetpeople:info")
    public R info(@PathVariable("meetId") String meetId) {
        List<MeetPeopleEntity> meetPeople = meetPeopleService.getById(meetId);

        return R.ok().put("meetPeople", meetPeople);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:meetpeople:save")
    public R save(@RequestBody List<MeetPeopleEntity> meetPeople) {
        meetPeopleService.insertBatch(meetPeople);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:meetpeople:update")
    public R update(@RequestBody List<MeetPeopleEntity> meetPeople,@RequestParam String meetId) {
        if(meetId != null) {
            meetPeopleService.deleteByMeetId(meetId);
        }
        meetPeopleService.insertBatch(meetPeople);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("generator:meetpeople:delete")
    public R delete(@RequestBody Integer[] peopleIds) {
        meetPeopleService.deleteBatchIds(Arrays.asList(peopleIds));

        return R.ok();
    }

}
