package com.orhonit.modules.generator.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.EjSchedulingPeopleEntity;
import com.orhonit.modules.generator.entity.ZgTaskEntity;
import com.orhonit.modules.generator.entity.ZgTaskFinishEntity;
import com.orhonit.modules.generator.service.EjSchedulingPeopleService;
import com.orhonit.modules.generator.service.EjSchedulingService;
import com.orhonit.modules.generator.service.ZgTaskFinishService;
import com.orhonit.modules.generator.service.ZgTaskService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 承诺践诺/工作任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:05:31
 */
@RestController
@RequestMapping("generator/zgtask")
public class ZgTaskController extends AbstractController {
    @Autowired
    private ZgTaskService zgTaskService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private ZgTaskFinishService zgTaskFinishService;
    @Autowired
    private EjSchedulingService ejSchedulingService;
    @Autowired
    private EjSchedulingPeopleService ejSchedulingPeopleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgtask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgTaskService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 查看报告单列表
     */
    @RequestMapping("/reportList")
    //@RequiresPermissions("generator:zgtask:list")
    public R reportList(@RequestParam Map<String, Object> params){
        PageUtils page = zgTaskService.selectReportList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgtask:info")
    public R info(@PathVariable("id") Integer id,@RequestParam(value = "schedulingId",required = false) String schedulingId){
    	ZgTaskEntity zgTask = zgTaskService.selectTaskInfo(id,schedulingId);

        return R.ok().put("zgTask", zgTask);
    }

    /**
     * 督办任务保存-调度任务保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
   // @RequiresPermissions("generator:zgtask:save")
    public R save(@RequestBody List<ZgTaskEntity> zgTask){
        for (ZgTaskEntity zgTaskEntity : zgTask) {
            SysUserEntity user = sysUserDao.selectInfo(zgTaskEntity.getUserId());
            if(!zgTaskEntity.getTaskType().equals("3")){
                if(!StringUtils.isNotBlank(zgTaskEntity.getTaskType())){
                    if(user.getIsCadres() == 1){
                        zgTaskEntity.setTaskType("2");
                    }else{
                        zgTaskEntity.setTaskType("1");
                    }
                }
            }
            zgTaskEntity.setUserName(user.getUserTrueName());
            zgTaskEntity.setUserIdCard(user.getUserNickname());
            zgTaskService.save(zgTaskEntity);
        }
        return R.ok();
    }

    /**
     * 认领任务
     */
    @PutMapping("/taskClaim")
//    @RequiresPermissions("generator:ejschedulingpeople:update")
    public R taskClaim(@RequestBody ZgTaskEntity zgTaskEntity){
        zgTaskEntity.setStatus(2);
        zgTaskEntity.setCreateUser(getUserId());
        zgTaskService.updateTaskClaim(zgTaskEntity);

        return R.ok();
    }

    /**
     * 全部完成任务
     */
    @GetMapping("/allCompleteTask/{schedulingId}")
//    @RequiresPermissions("generator:ejschedulingpeople:update")
    public R allCompleteTask(@PathVariable("schedulingId") String schedulingId){
        ejSchedulingService.updateStatus(schedulingId);
        List<EjSchedulingPeopleEntity> peopleList = ejSchedulingPeopleService.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("scheduling_id ="+"'"+schedulingId+"'"));
        if(peopleList.size() > 0){
            for (EjSchedulingPeopleEntity peopleEntity:peopleList) {
                List<ZgTaskEntity> taskList = zgTaskService.selectList(new EntityWrapper<ZgTaskEntity>().and("people_id ="+peopleEntity.getId()));
                if(taskList.size() > 0){
                    for (ZgTaskEntity taskEntity:taskList) {
                        taskEntity.setStatus(3);
                        zgTaskService.updateById(taskEntity);
                        List<ZgTaskFinishEntity> recordList = zgTaskFinishService.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+taskEntity.getId()).and("schedule = 100").orderBy("create_time desc"));
                        if(recordList.size() == 0){
                            SysUserEntity user = sysUserDao.selectInfo(getUserId());
                            ZgTaskFinishEntity finish = new ZgTaskFinishEntity();
                            finish.setContent("任务完成");
                            finish.setCreateTime(new Date());
                            finish.setCreateBy(user.getUserId());
                            finish.setTaskId(taskEntity.getId());
                            finish.setCreateName(user.getUserTrueName());
                            finish.setSchedule(100);
                            finish.setContentType("1");
                            zgTaskFinishService.insert(finish);
                        }
                    }
                }
            }
        }
        return R.ok();
    }
    /**
     * 完成任务
     */
    @GetMapping("/completeTask/{id}")
    public R completeTask(@PathVariable("id") Integer id){
        ZgTaskEntity zgTaskEntity = zgTaskService.selectById(id);
        zgTaskEntity.setStatus(3);
        zgTaskService.updateById(zgTaskEntity);
        List<ZgTaskFinishEntity> recordList = zgTaskFinishService.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+id).and("schedule = 100").orderBy("create_time desc"));
        if(recordList.size() == 0){
            SysUserEntity user = sysUserDao.selectInfo(getUserId());
            ZgTaskFinishEntity finish = new ZgTaskFinishEntity();
            finish.setContent("任务完成");
            finish.setCreateTime(new Date());
            finish.setCreateBy(user.getUserId());
            finish.setTaskId(id);
            finish.setCreateName(user.getUserTrueName());
            finish.setSchedule(100);
            finish.setContentType("1");
            zgTaskFinishService.insert(finish);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgtask:update")
    public R update(@RequestBody ZgTaskEntity zgTask){
		zgTaskService.updateById(zgTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:zgtask:delete")
    public R delete(@PathVariable("id")Integer id){
		zgTaskService.deleteTask(id);
        return R.ok();
    }
    /**
     * 修改完成状态
     */
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.PUT)
   // @RequiresPermissions("generator:zgtask:save")
    public R updateStatus(@PathVariable("id")Integer id){
		zgTaskService.updateStatus(id);
        return R.ok();
    }
    
    /**
     * 统计列表
     */
    @RequestMapping("/statistiList")
    //@RequiresPermissions("generator:zgtask:list")
    public R statistiList(@RequestParam Map<String, Object> params){
    	Map<String, Object> statis = zgTaskService.statistiList(params);
        if(statis.size() > 0) {
        	return R.ok().put("statis", statis);
        }
        return R.error();
    }

}
