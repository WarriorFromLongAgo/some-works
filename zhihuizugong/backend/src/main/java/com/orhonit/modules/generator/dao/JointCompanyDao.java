package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.JointCompanyEntity;
import com.orhonit.modules.generator.vo.JointCompanyVO;


@Mapper
public interface JointCompanyDao extends BaseMapper<JointCompanyEntity>{
	
	List<JointCompanyVO>joinList(@Param("params")Map<String, Object> params);
	
	JointCompanyVO getOneJoint(@Param("id")Integer id);

}
