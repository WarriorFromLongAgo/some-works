package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.SubmittingEntity;
import com.orhonit.modules.generator.vo.SubmittingVO;


@Mapper
public interface SubmittingDao extends BaseMapper<SubmittingEntity>{
	
	@Select("SELECT id,submitted_company FROM se_submitting_unit")
	List<SubmittingEntity>list();
	
	/**
	  * 报送单位列表
	 * @param params
	 * @return
	 */
	List<SubmittingVO>getSubmittingList(@Param("params")Map<String,Object>params);
	
	/**
	  * 报送单位详情
	 * @param id
	 * @return
	 */
	SubmittingVO getOneSubmitt(@Param("id")Integer id);

}
