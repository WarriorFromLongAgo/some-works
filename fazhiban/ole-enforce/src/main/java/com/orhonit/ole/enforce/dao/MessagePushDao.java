package com.orhonit.ole.enforce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.enforce.entity.MessagePushEntity;

@Mapper
public interface MessagePushDao {
	@Update("update ole_message_push t set t.pc_push_success = #{push_success} where t.id = #{id}")
	void updatePcPushSuccess(@Param("id") String id, @Param("push_success") int push_success);

	List<MessagePushEntity> getOfflinePush(@Param("push_person") String push_person);
	
	@Update("update ole_message_push t set t.push_success = #{push_success} where t.id = #{id}")
	void updatePush(@Param("id") String id, @Param("push_success") int push_success);
	
	List<MessagePushEntity> getPcOfflinePush(@Param("pc_push_person") String pc_push_person);
}
