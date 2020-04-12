package com.orhonit.modules.generator.dao;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.NoteEntity;


/**
 * 捂一捂 
 * 笔记记录
 * @author 
 *
 */
@Mapper
public interface NoteDao extends BaseMapper<NoteEntity>{
	
	
	/*
	 * @Insert("INSERT INTO wyw_note(content,create_time)values(#{content},#{createTime})"
	 * ) void save(String content,Date createTime);
	 */
	void save(NoteEntity entity);

}
