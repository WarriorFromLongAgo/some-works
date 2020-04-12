package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.controller.AbstractController;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.entity.OverseeRecordsEntity;
import com.orhonit.modules.generator.service.OverseeRecordsService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orhonit.common.utils.ShiroUtils.getUserId;


/**
 * 领导督办记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-09 11:14:32
 */
@RestController
@RequestMapping("/app/generator/overseerecords")
public class AppOverseeRecordsController {
    @Autowired
    private OverseeRecordsService overseeRecordsService;
    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:overseerecords:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = overseeRecordsService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 领导批示列表
     */
    @GetMapping("/instructionsList/{overseeId}")
//    @RequiresPermissions("generator:overseerecords:list")
    public R instructionsList(@PathVariable("overseeId") String overseeId){
    	List<OverseeRecordsEntity> list = overseeRecordsService.instructionsList(overseeId);
        return R.ok().put("list", list);
    }
    
    /**
     *完成进度列表
     */
    @GetMapping("/scheduleList/{overseeId}")
//    @RequiresPermissions("generator:overseerecords:list")
    public R scheduleList(@PathVariable("overseeId") String overseeId){
    	List<OverseeRecordsEntity> list = overseeRecordsService.scheduleList(overseeId);
        return R.ok().put("list", list);
    }
    
    /**
     * 领导点评列表
     */
    @GetMapping("/reviewList/{overseeId}")
//    @RequiresPermissions("generator:overseerecords:list")
    public R reviewList(@PathVariable("overseeId") String overseeId){
    	List<OverseeRecordsEntity> list = overseeRecordsService.reviewList(overseeId);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("generator:overseerecords:info")
    public R info(@PathVariable("recordId") Integer recordId){
		OverseeRecordsEntity overseeRecords = overseeRecordsService.selectById(recordId);

        return R.ok().put("overseeRecords", overseeRecords);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:overseerecords:save")
    public R save(@RequestBody OverseeRecordsEntity overseeRecords){
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(overseeRecords.getCreateBy());
    	overseeRecords.setCrtTime(new Date());
    	overseeRecords.setLowerId(userInfo.getLowerId());
    	overseeRecords.setCreateDeptId(userInfo.getUserDept());
    	overseeRecords.setCreateName(userInfo.getUserTrueName());
		overseeRecordsService.insert(overseeRecords);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:overseerecords:update")
    public R update(@RequestBody OverseeRecordsEntity overseeRecords){
		overseeRecordsService.updateById(overseeRecords);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{recordId}")
//    @RequiresPermissions("generator:overseerecords:delete")
    public R delete(@PathVariable("recordId") Integer recordId){
		overseeRecordsService.deleteById(recordId);

        return R.ok();
    }

}
