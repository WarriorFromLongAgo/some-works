package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.MeetFileEntity;
import com.orhonit.modules.generator.service.MeetFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会议附件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-17 16:37:36
 */
@RestController
@RequestMapping("app/meetfile")
public class AppMeetFileController {
    @Autowired
    private MeetFileService meetFileService;

    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
//    @RequiresPermissions("generator:meetfile:info")
    public R info(@PathVariable("meetId") String meetId){
		List<MeetFileEntity> meetFile = meetFileService.getById(meetId);

        return R.ok().put("meetFile", meetFile);
    }

}
