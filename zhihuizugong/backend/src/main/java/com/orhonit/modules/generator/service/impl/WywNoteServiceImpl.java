package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.WywNoteDao;
import com.orhonit.modules.generator.entity.WywNoteEntity;
import com.orhonit.modules.generator.service.WywNoteService;


@Service("wywNoteService")
public class WywNoteServiceImpl extends ServiceImpl<WywNoteDao, WywNoteEntity> implements WywNoteService {
    
	
	@Autowired
	WywNoteDao dao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if(!params.containsKey("noteTitle")) {
    		int currPage = 1;
	    	int limit = 10;
	    	 if(params.get("page") != null){
	             currPage = Integer.parseInt((String)params.get("page"));
	         }
	         if(params.get("limit") != null){
	             limit = Integer.parseInt((String)params.get("limit"));
	         }
	         Page<WywNoteEntity> page = new Page<>(currPage, limit);
	         int beginLimit = (currPage-1)*limit;
		     params.put("limit", limit);
		     params.put("beginLimit", beginLimit);
		     params.remove("page",page);
		     page.setRecords(dao.wywlist(params));
		     page.setTotal(this.selectCount(new EntityWrapper<WywNoteEntity>()));
		     return new PageUtils(page);
    	}else {
    		String noteTitle = params.get("noteTitle").toString();
			int currPage = 1;
	    	int limit = 10;
	    	 if(params.get("page") != null){
	             currPage = Integer.parseInt((String)params.get("page"));
	         }
	         if(params.get("limit") != null){
	             limit = Integer.parseInt((String)params.get("limit"));
	         }
	        Page<WywNoteEntity> page = new Page<>(currPage, limit);
	        int beginLimit = (currPage-1)*limit;
	        params.put("limit", limit);
	        params.put("noteTitle", noteTitle);
	        params.put("beginLimit", beginLimit);
	        params.remove("page",page);
	        page.setRecords(dao.wywlist(params));
	        page.setTotal(this.selectCount(new EntityWrapper<WywNoteEntity>().like("note_title", noteTitle).eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
	        return new PageUtils(page);
    	}
      }

	@Override
	public void deleteWywNote(String noteId) {
		WywNoteEntity entity = new WywNoteEntity();
		entity.setUpdateTime(new Date());
		entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
		dao.update(entity, new EntityWrapper<WywNoteEntity>().eq("note_id", noteId));
	}
}