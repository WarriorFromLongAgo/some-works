package com.orhonit.modules.generator.controller;

import java.util.List;
import java.util.Map;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.SysUserExtendEntity;
import com.orhonit.modules.generator.service.SysUserExtendService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户扩展表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 10:46:50
 */
@RestController
@RequestMapping("generator/sysuserextend")
public class SysUserExtendController extends AbstractController{
    @Autowired
    private SysUserExtendService sysUserExtendService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:sysuserextend:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserExtendService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
   // @RequiresPermissions("generator:sysuserextend:info")
    public R info(@PathVariable("id") Integer id){
		SysUserExtendEntity sysUserExtend = sysUserExtendService.selectById(id);

        return R.ok().put("sysUserExtend", sysUserExtend);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    //@RequiresPermissions("generator:sysuserextend:save")
    public R save(@RequestBody SysUserExtendEntity sysUserExtendEntity){
		sysUserExtendService.save(sysUserExtendEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    //@RequiresPermissions("generator:sysuserextend:update")
    public R update(@RequestBody SysUserExtendEntity sysUserExtend){
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(sysUserExtend,sysUserEntity);
        sysUserService.updateById(sysUserEntity);
		sysUserExtendService.updateById(sysUserExtend);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("generator:sysuserextend:delete")
    public R delete(Integer id){
		sysUserExtendService.deleteById(id);

        return R.ok();
    }

    /**
     * 根据身份证号获取人员信息
     * @param idCard
     * @return
     */
    @RequestMapping("/findUser")
    public R findUser(String idCard){
        Map<String,Object> map = sysUserExtendService.findUser(idCard);
        if (map != null && map.size() > 0){
            return R.ok().put("data",map);
        }else {
            return R.error(201, "该用户未注册");
        }
    }

    /**
     * 干部列表展示
     * @param params
     * @return
     */
    @RequestMapping("/findLeaderUserList")
    public Map<String,Object> findLeaderUserList(@RequestParam Map<String, Object> params){

        return sysUserExtendService.findLeaderUserList(params);
    }

    /**
     * 党员列表展示
     * @param params
     * @return
     */
    @RequestMapping("/findPartyUserList")
    public Map<String,Object> findPartyUserList(@RequestParam Map<String, Object> params){
        return sysUserExtendService.findPartyUserList(params);
    }

    /**
     * 党员列表展示一级下拉框
     * @return
     */
    @RequestMapping("/findPartyOneList")
    public Map<String,Object> findPartyOneList(){
        return sysUserExtendService.findPartyOneList();
    }

    /**
     * 党员干部详情页
     * @param params
     * @return
     */
    @RequestMapping("/findAllData")
    public Map<String,Object> findAllData(@RequestParam Map<String, Object> params){
        return sysUserExtendService.findAllData(params);
    }

    /**
     * 返回支部列表(党建地图用)
     * @return
     */
    @RequestMapping("/findDeptData")
    public Map<String,Object> findDeptData(){
        return sysUserExtendService.findDeptData();
    }

    /**
     * 返回单位列表(组织分布使用)
     * @return
     */
    @RequestMapping("/findOrgData")
    public Map<String,Object> findOrgData(){
        return sysUserExtendService.findOrgData();
    }

    /**
     * 公用条件搜索人员
     * @param params
     * @return
     */
    @RequestMapping("/common/select")
    public Map<String,Object> findCommonSelect(@RequestParam Map<String, Object> params){
        return sysUserExtendService.findCommonSelect(params);
    }

    /**
     * 条件筛选 不带分页
     * @param params
     * @return
     */
    @RequestMapping("/common/selectNotPage")
    public Map<String,Object> findCommonSelectNotPage(@RequestParam Map<String, Object> params){
        return sysUserExtendService.findCommonSelectNotPage(params);
    }
}
