package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.DocumentContentEntity;
import com.orhonit.modules.generator.service.DocumentContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-04 14:31:05
 */
@RestController
@RequestMapping("/app/documentcontent")
public class AppDocumentContentController {
    @Autowired
    private DocumentContentService documentContentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:documentcontent:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = documentContentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{documentId}")
//    @RequiresPermissions("generator:documentcontent:info")
    public R info(@PathVariable("documentId") String documentId) {
        List<DocumentContentEntity> documentContent = documentContentService.getById(documentId);

        return R.ok().put("documentContent", documentContent);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
//    @RequiresPermissions("generator:documentcontent:save")
    public R save(@RequestBody DocumentContentEntity documentContent) {
        documentContentService.insert(documentContent);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:documentcontent:update")
    public R update(@RequestBody DocumentContentEntity documentContent) {
        documentContentService.updateById(documentContent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("generator:documentcontent:delete")
    public R delete(@RequestBody String[] ids) {
        documentContentService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
