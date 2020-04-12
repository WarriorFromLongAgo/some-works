package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.entity.OsExamEntity;
import com.orhonit.modules.generator.service.OsExamService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 试卷表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-07 18:11:19
 */
@RestController
@RequestMapping("generator/osexam")
public class OsExamController {
    @Autowired
    private OsExamService osExamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:osexam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = osExamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{examId}")
    @RequiresPermissions("generator:osexam:info")
    public R info(@PathVariable("examId") String examId){
		OsExamEntity osExam = osExamService.selectById(examId);

        return R.ok().put("osExam", osExam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:osexam:save")
    public R save(@RequestBody OsExamEntity osExam){
		osExamService.insertOs(osExam);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:osexam:update")
    public R update(@RequestBody OsExamEntity osExam){
    	osExam.setUpdateTime(new Date());
		osExamService.updateById(osExam);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:osexam:delete")
    public R delete(@RequestBody String examId){
		osExamService.deleteOsExam(examId);

        return R.ok();
    }

}
