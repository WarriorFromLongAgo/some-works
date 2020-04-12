package com.orhonit.modules.sys.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;
import com.orhonit.modules.sys.service.TaDepartmentMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



/**
 * 科室表
 *
 * @author 张元
 * @date 2019-04-30 17:07:15
 */
@RestController
@RequestMapping("generator/tadepartmentmember")
public class TaDepartmentMemberController extends AbstractController {
    @Autowired
    private TaDepartmentMemberService taDepartmentMemberService;

    /**
     * 列表
     */
    @RequestMapping(value="list",method=RequestMethod.GET)
//    @RequiresPermissions("generator:tadepartmentmember:list")
    public R list(@RequestParam Map<String, Object> params){
    	params.put("deptId", getUser().getUserDept());
        PageUtils page = taDepartmentMemberService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 查询当前登录用户的支部下归属的科室列表
     */
    @RequestMapping(value="lowerList",method=RequestMethod.GET)
//    @RequiresPermissions("generator:tadepartmentmember:list")
    public R lowerList(){
        List<TaDepartmentMemberEntity> list = taDepartmentMemberService.selecLowerList(getUser().getUserDept());

        return R.ok().put("list", list);
    }

    /**
     * 查询科室列表及其归属人员
     */
    @RequestMapping(value="lowerUserList",method=RequestMethod.GET)
//    @RequiresPermissions("generator:tadepartmentmember:list")
    public R lowerUserList(){
        List<TaDepartmentMemberEntity> list = taDepartmentMemberService.selectLowerUserList();

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping(value="/info/{lowerId}",method=RequestMethod.GET)
//    @RequiresPermissions("generator:tadepartmentmember:info")
    public R info(@PathVariable("lowerId") String lowerId){
		TaDepartmentMemberEntity taDepartmentMember = taDepartmentMemberService.selectById(lowerId);

        return R.ok().put("taDepartmentMember", taDepartmentMember);
    }

    /**
     * 保存
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
//    @RequiresPermissions("generator:tadepartmentmember:save")
    public R save(@RequestBody TaDepartmentMemberEntity taDepartmentMember){
    	taDepartmentMember.setDeptId(getUser().getUserDept());
		taDepartmentMemberService.insert(taDepartmentMember);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value="/update",method=RequestMethod.PUT)
//    @RequiresPermissions("generator:tadepartmentmember:update")
    public R update(@RequestBody TaDepartmentMemberEntity taDepartmentMember){
		taDepartmentMemberService.updateById(taDepartmentMember);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete/{lowerId}",method=RequestMethod.DELETE)
//    @RequiresPermissions("generator:tadepartmentmember:delete")
    public R delete(@PathVariable("lowerId") String lowerId){
		taDepartmentMemberService.deleteById(lowerId);
        return R.ok();
    }

}
