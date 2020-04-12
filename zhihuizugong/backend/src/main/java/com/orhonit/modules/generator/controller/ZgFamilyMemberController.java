package com.orhonit.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;
import com.orhonit.modules.generator.service.ZgFamilyMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户主要家庭成员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-23 11:11:38
 */
@RestController
@RequestMapping("generator/zgfamilymember")
public class ZgFamilyMemberController {
    @Autowired
    private ZgFamilyMemberService zgFamilyMemberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:zgfamilymember:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zgFamilyMemberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:zgfamilymember:info")
    public R info(@PathVariable("id") Integer id){
		ZgFamilyMemberEntity zgFamilyMember = zgFamilyMemberService.selectById(id);

        return R.ok().put("zgFamilyMember", zgFamilyMember);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:zgfamilymember:save")
    public R save(@RequestBody ZgFamilyMemberEntity zgFamilyMember){
		zgFamilyMemberService.save(zgFamilyMember);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:zgfamilymember:update")
    public R update(@RequestBody ZgFamilyMemberEntity zgFamilyMember){
		zgFamilyMemberService.updateById(zgFamilyMember);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:zgfamilymember:delete")
    public R delete(Integer id){
		zgFamilyMemberService.deleteById(id);

        return R.ok();
    }

}
