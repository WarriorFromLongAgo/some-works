package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;
import com.orhonit.modules.generator.service.LeadershipOverseeService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 领导督办主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@RestController
@RequestMapping("generator/leadershipoversee")
public class LeadershipOverseeController extends AbstractController {
    @Autowired
    private LeadershipOverseeService leadershipOverseeService;

    /**
     * 领导督办已发列表
     */
    @RequestMapping(value="list",method=RequestMethod.GET)
//    @RequiresPermissions("generator:leadershipoversee:list")
    public R list(@RequestParam Map<String, Object> params){
    	params.put("userId", getUserId());
        PageUtils page = leadershipOverseeService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 领导督办已接受列表
     */
    @RequestMapping(value="receptionList",method=RequestMethod.GET)
//    @RequiresPermissions("generator:leadershipoversee:list")
    public R  receptionList(@RequestParam Map<String, Object> params){
    	params.put("userId", getUserId());
        PageUtils page = leadershipOverseeService.receptionList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping(value="/info/{overseeId}",method=RequestMethod.GET)
//    @RequiresPermissions("generator:leadershipoversee:info")
    public R info(@PathVariable("overseeId") String overseeId){
		LeadershipOverseeEntity leadershipOversee = leadershipOverseeService.selectById(overseeId);

        return R.ok().put("leadershipOversee", leadershipOversee);
    }

    /**
     * 保存
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
//    @RequiresPermissions("generator:leadershipoversee:save")
    public R save(@RequestBody LeadershipOverseeEntity leadershipOversee){
    	leadershipOversee.setUserId(getUserId());
    	leadershipOversee.setDeptId(getUser().getUserDept());
    	leadershipOversee.setLowerId(getUser().getLowerId());
    	leadershipOversee.setCrtTime(new Date());
    	leadershipOversee.setOverseeStatus(0);
		leadershipOverseeService.save(leadershipOversee);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value="/update",method=RequestMethod.PUT)
//    @RequiresPermissions("generator:leadershipoversee:update")
    public R update(@RequestBody LeadershipOverseeEntity leadershipOversee){
		leadershipOverseeService.updateById(leadershipOversee);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete/{overseeId}",method=RequestMethod.DELETE)
//    @RequiresPermissions("generator:leadershipoversee:delete")
    public R delete(@PathVariable String overseeId){
		leadershipOverseeService.deleteLeadershipOversee(overseeId);
        return R.ok();
    }

}
