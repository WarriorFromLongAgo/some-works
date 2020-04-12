package com.orhonit.modules.enterprise.mapper;

import com.orhonit.modules.enterprise.entity.NopublicEnterpriseMsg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 企业或民宗局消息
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-02 17:26:29
 */
@Mapper
public interface NopublicEnterpriseMsgMapper extends BaseMapper<NopublicEnterpriseMsg> {
	
	/**
	 * 通过企业id火工商联id和类型查询发布信息
	 * @param id
	 * @param type
	 * @return
	 */
	@Select({
		"<script>"
		+ "SELECT     " + 
		"	m.id,     " + 
		"	m.type,     " + 
		"	m.title,     " + 
		"	m.content,     " + 
		"	m.accessory_ids accessoryIds,     " + 
		"	m.user_id,     " + 
		"	m.receive_enterprise_id,     " + 
		"	m.is_read isRead,     " + 
		"	m.create_user createUser,     " + 
		"	m.create_time createTime ,     " + 
		"	u.username userName,        " + 
		"	ee.id receiveEnterpriseId ,"
		+ "ee.name receiveEnterpriseName  "
		+ " <if test=' type==\"0\"  '> ,r.audit_status auditStatus </if>   " + 
		"FROM nopublic_enterprise_msg m     " + 
		"	left join sys_user u on u.user_id=m.user_id       " + 
		"	left join nopublic_enterprise ee on ee.id=m.receive_enterprise_id "
		+ " <if test=' type==\"0\" '>  left join nopublic_reply r on r.nopublic_em_id =m.id    </if>" + 
		"WHERE     " + 
		"	m.user_id = #{userId} and m.type=#{type} "
		+ "</script>"
	})
	List<NopublicEnterpriseMsg> selectNopublicEnterpriseByUserId(@RequestParam(value="userId") Long userId,@RequestParam(value="type")String type);
	 
}
