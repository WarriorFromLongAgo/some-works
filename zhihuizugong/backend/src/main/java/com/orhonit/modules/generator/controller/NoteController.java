package com.orhonit.modules.generator.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.NoteEntity;
import com.orhonit.modules.generator.service.NoteService;


/**
 * 悟一悟
 * 笔记记录
 * @author 
 *
 */
@RestController
@RequestMapping("generator/note")
public class NoteController {
	
	
	@Autowired
	private NoteService Noteservice;
	
	
	/**
	 * 插入一条笔记
	 * @param entity
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions("generator:note:save")
	public void save (@RequestBody NoteEntity entity) {
		Noteservice.save(entity);
	}
	
	
	/**
	 * 捂一捂
	 * 单挑笔记查询
	 * @param noteId
	 * @return
	 */
	@RequestMapping("/info/{noteId}")
	public R info(@PathVariable ("noteId") Integer noteId) {
		NoteEntity note = Noteservice.selectById(noteId);
		return R.ok().put("note", note);
	}

}
