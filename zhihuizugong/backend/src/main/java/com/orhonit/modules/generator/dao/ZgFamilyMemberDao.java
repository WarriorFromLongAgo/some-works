package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户主要家庭成员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-23 11:11:38
 */
@Mapper
public interface ZgFamilyMemberDao extends BaseMapper<ZgFamilyMemberEntity> {
	
}
