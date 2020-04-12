package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.controller.AbstractController;
import com.orhonit.modules.generator.entity.CoreOpinionEntity;
import com.orhonit.modules.generator.service.CoreOpinionService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @date 2019-05-13 14:37:49
 */
@RestController
@RequestMapping("app/coreopinion")
public class AppCoreOpinionController extends AbstractController {
    @Autowired
    private CoreOpinionService coreOpinionService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreopinion:list")
    public R list(@RequestParam Map<String, Object> params) {
        String userId = (String) params.get("userId");
        if (StringUtils.isNotBlank(userId)) {
            PageUtils page = coreOpinionService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{opinionId}")
//    @RequiresPermissions("generator:coreopinion:info")
    public R info(@PathVariable("opinionId") String opinionId) {
        CoreOpinionEntity coreOpinion = coreOpinionService.selectById(opinionId);

        return R.ok().put("coreOpinion", coreOpinion);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("generator:coreopinion:save")
    public R save(@RequestBody CoreOpinionEntity coreOpinion) {

        coreOpinionService.insertOpinion(coreOpinion);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{opinionId}")
//    @RequiresPermissions("generator:coreopinion:delete")
    public R delete(@PathVariable("opinionId") String opinionId) {
        coreOpinionService.deleteByOpinionId(opinionId);

        return R.ok();
    }

    /**
     * 获取所有人员
     */
    @RequestMapping("/user/list")
//    @RequiresPermissions("generator:coreopinion:info")
    public R userList() {
        List<SysUserEntity> user = sysUserService.getUser();

        return R.ok().put("user", user);
    }

}
