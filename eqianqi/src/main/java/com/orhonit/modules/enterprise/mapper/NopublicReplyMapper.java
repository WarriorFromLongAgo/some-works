package com.orhonit.modules.enterprise.mapper;

import com.orhonit.modules.enterprise.entity.NopublicReply;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-02 17:26:28
 */
@Mapper
public interface NopublicReplyMapper extends BaseMapper<NopublicReply> {
	
	
	@Select({
		"select id,audit_status auditStatus,audit_opinion auditOpinion,audit_user auditUser,audit_create_time auditCreateTime from nopublic_reply where nopublic_em_id =#{id}"
	})
	NopublicReply selectNopublicReplyById(Long id);
	
}
