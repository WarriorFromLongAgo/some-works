package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.MeetPeopleEntity;
import com.orhonit.modules.generator.service.MeetPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 参加人员表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-12 15:00:48
 */
@RestController
@RequestMapping("app/meetpeople")
public class AppMeetPeopleController {
    @Autowired
    private MeetPeopleService meetPeopleService;

    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
//    @RequiresPermissions("generator:meetpeople:info")
    public R info(@PathVariable("meetId") String meetId) {
        List<MeetPeopleEntity> meetPeople = meetPeopleService.getById(meetId);

        return R.ok().put("meetPeople", meetPeople);
    }

}
