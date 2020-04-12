package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppWywNoteEntity;
import com.orhonit.modules.generator.app.vo.AppWywNoteVO;

/**
 * APP捂一捂
 * @author 
 *
 */
@Mapper
public interface AppWywNoteDao  extends BaseMapper<AppWywNoteEntity>{
	
	/**
	 * 单条查询
	 * @param noteId
	 * @return
	 */
	AppWywNoteEntity getOneWyw(@Param("noteId")Integer noteId);
    
	
	/**
	  *  查出所有用户
	 * @return
	 */
	@Select("SELECT create_userid AS allUser FROM wyw_note  GROUP BY create_userid")
    List<Integer> selectAllUser();
	
	
	/**
	  * 所有用户发布的数量
	 * @param allUser
	 * @return
	 */
	@Select("SELECT \r\n" + 
			"count(note.note_id) AS numberOfReleases,\r\n" + 
			"user.user_true_name AS userName  \r\n" + 
			"FROM wyw_note note\r\n" + 
			"LEFT JOIN sys_user user ON note.create_userid=user.user_id\r\n" + 
			"GROUP BY user.user_true_name  \r\n" + 
			"ORDER BY numberOfReleases desc\r\n" + 
			"LIMIT #{beginLimit},#{limit}")
	List<AppWywNoteVO> userPh(@Param("beginLimit")Integer beginLimit,@Param("limit")Integer limit);
	
	//查询所有笔记包括发布人姓名
	List<AppWywNoteEntity>selectListUserTruName(@Param("beginLimit")int beginLimit,@Param("limit")int limit);
	//查询我的所有笔记列表
	List<AppWywNoteEntity>mySelectList(@Param("createUserid") int createUserid,@Param("beginLimit")int beginLimit,@Param("limit")int limit);
}
