package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.CoreOpinionReplyEntity;
import com.orhonit.modules.generator.service.CoreOpinionReplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 16:10:56
 */
@RestController
@RequestMapping("generator/coreopinionreply")
public class CoreOpinionReplyController {
    @Autowired
    private CoreOpinionReplyService coreOpinionReplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreopinionreply:list")
    public R list(@RequestParam Map<String, Object> params) {
        String userId = (String) params.get("userId");
        if (StringUtils.isNotBlank(userId)) {
            PageUtils page = coreOpinionReplyService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{replyId}")
//    @RequiresPermissions("generator:coreopinionreply:info")
    public R info(@PathVariable("replyId") Integer replyId) {
        CoreOpinionReplyEntity coreOpinionReply = coreOpinionReplyService.selectById(replyId);

        return R.ok().put("coreOpinionReply", coreOpinionReply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreopinionreply:save")
    public R save(@RequestBody CoreOpinionReplyEntity coreOpinionReply) {
        coreOpinionReplyService.insert(coreOpinionReply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreopinionreply:update")
    public R update(@RequestBody CoreOpinionReplyEntity coreOpinionReply) {
        coreOpinionReplyService.updateById(coreOpinionReply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{replyId}")
//    @RequiresPermissions("generator:coreopinionreply:delete")
    public R delete(@PathVariable("replyId") Integer replyId) {
        coreOpinionReplyService.deleteById(replyId);

        return R.ok();
    }

}
