package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.entity.NoteEntity;



public interface NoteService  extends IService<NoteEntity>  {
	
	
	/*
	 * @Insert("INSERT INTO wyw_note(content,create_time)values(#{content},#{createTime})"
	 * ) void save(String content,Date createTime);
	 */

	void save(NoteEntity entity);

}
