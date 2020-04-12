package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.DocumentEntity;
import com.orhonit.modules.generator.service.DocumentService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;
import com.orhonit.modules.sys.service.TaDepartmentMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公文管理主表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-04 14:31:05
 */
@RestController
@RequestMapping("generator/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TaDepartmentMemberService taDepartmentMemberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:document:list")
    public R list(@RequestParam Map<String, Object> params){
        String userId = (String) params.get("userId");
        if(StringUtils.isNotBlank(userId)) {
            PageUtils page = documentService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 获取当前科室底下的人员
     */
    @RequestMapping("/listdept/{lowerId}")
//    @RequiresPermissions("generator:document:list")
    public R listdept(@PathVariable("lowerId")Integer lowerId){
        List<SysUserEntity> list = sysUserService.listdept(lowerId);
        return R.ok().put("list", list);
    }

    /**
     * 所有科室列表
     */
    @RequestMapping(value="listDepart",method=RequestMethod.GET)
//    @RequiresPermissions("generator:tadepartmentmember:list")
    public R listDepart(@RequestParam Map<String, Object> params){
        PageUtils page = taDepartmentMemberService.queryPageDepart(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{documentId}")
//    @RequiresPermissions("generator:document:info")
    public R info(@PathVariable("documentId") String documentId){
		DocumentEntity document = documentService.getById(documentId);

        return R.ok().put("document", document);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:document:save")
    public R save(@RequestBody DocumentEntity document){
		documentService.insertDocument(document);

        return R.ok();
    }

    /**
     * 修改相关内容（审核、编辑、办理情况）
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:document:update")
    public R updateSh(@RequestBody DocumentEntity document){
        documentService.updateById(document);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{documentId}")
//    @RequiresPermissions("generator:document:delete")
    public R delete(@PathVariable("documentId") String documentId){
		documentService.removeById(documentId);

        return R.ok();
    }

}
