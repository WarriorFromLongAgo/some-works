package com.orhonit.modules.generator.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.entity.WywNoteEntity;
import com.orhonit.modules.generator.service.WywNoteService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 悟一悟  笔记记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 10:03:13
 */
@RestController
@RequestMapping("generator/wywnote")
public class WywNoteController {
    @Autowired
    private WywNoteService wywNoteService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:wywnote:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wywNoteService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{noteId}")
    //@RequiresPermissions("generator:wywnote:info")
    public R info(@PathVariable("noteId") Integer noteId){
		WywNoteEntity wywNote = wywNoteService.selectById(noteId);

        return R.ok().put("wywNote", wywNote);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("generator:wywnote:save")
    public R save(@RequestBody WywNoteEntity wywNote){
		wywNoteService.insert(wywNote);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:wywnote:update")
    public R update(@RequestBody WywNoteEntity wywNote){
		wywNoteService.updateById(wywNote);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete( String noteIds){
    	if(!("null").equals(noteIds)) {
    		wywNoteService.deleteWywNote(noteIds);
    	}else {
    		return R.parameterIsNul();
    	}
        return R.ok();
    }

}
