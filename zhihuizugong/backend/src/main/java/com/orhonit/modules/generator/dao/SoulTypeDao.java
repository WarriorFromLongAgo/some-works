package com.orhonit.modules.generator.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.SoulTypeEntity;

@Mapper
public interface SoulTypeDao extends BaseMapper<SoulTypeEntity>{
	
	
	@Delete("DELETE FROM soul_type WHERE type_id=#{typeId}")
	void deleteType(@Param("typeId")Integer typeId);
	
	@Update("UPDATE soul_type set type_name=#{typeName} WHERE type_id=#{typeId}")
	void updatetype(SoulTypeEntity entity);
	//全部分类
	@Select("SELECT type_id,type_name,create_time,create_user_id,type_identifier FROM soul_type")
	List<SoulTypeEntity>soulTypeList();

}
