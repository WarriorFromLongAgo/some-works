package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 领导督办主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@Mapper
public interface LeadershipOverseeDao extends BaseMapper<LeadershipOverseeEntity> {

	/**
	 * 查询领导督办接收人分页列表
	 * @param params
	 * @return
	 */
	List<LeadershipOverseeEntity> receptionList(@Param("params")Map<String, Object> params);

	/**
	 * 查询领导督办接收人列表总数
	 * @param params
	 * @return
	 */
	List<LeadershipOverseeEntity> receptionCount(@Param("params")Map<String, Object> params);
	/**
     * 保存
     */
	void save(LeadershipOverseeEntity leadershipOversee);
	/**
	 * 删除领导督办相关人员
	 * @param overseeId
	 */
	@Delete("delete from tb_oversee_people where oversee_id = #{overseeId}")
	void deletePeople(@Param("overseeId")String overseeId);
	/**
	 * 删除领导督办相关附件
	 * @param overseeId
	 */
	@Delete("delete from tb_oversee_file where oversee_id = #{overseeId}")
	void deleteFile(@Param("overseeId")String overseeId);
	
}
