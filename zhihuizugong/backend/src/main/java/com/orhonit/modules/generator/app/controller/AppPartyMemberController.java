package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 党员
 *
 * @author xiaobai
 * @date 2019-05-18 14:31:17
 */
@RestController
@RequestMapping("app/party")
public class AppPartyMemberController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:sysuser:list")
    public R list(@RequestParam Map<String, Object> params){
    	String isPartyMember = (String) params.get("isPartyMember");
        if(StringUtils.isNotBlank(isPartyMember)) {
	        PageUtils page = sysUserService.queryPageParty(params);
	
	        return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
//    @RequiresPermissions("generator:sysuser:info")
    public R info(@PathVariable("userId") Long userId){
        SysUserEntity sysUser = sysUserService.selectById(userId);

        return R.ok().put("sysUser", sysUser);
    }

}
