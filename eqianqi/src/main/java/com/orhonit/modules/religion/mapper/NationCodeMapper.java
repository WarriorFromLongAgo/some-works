package com.orhonit.modules.religion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.religion.entity.NationCode;

/**
 * 
 * @author 	cyf
 * @date	2018/11/13 上午11:52:22
 */
@Mapper
public interface NationCodeMapper extends BaseMapper<NationCode> {

	@Select({
		"select id,nation_code nationCode,nation_name nationName from nation_code"
	})
	List<NationCode> selectNationCodeAll();
	
}
