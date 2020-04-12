package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.sys.dto.AppBannerDTO;

@Mapper
public interface AppBannerDao {

	int count(@Param("params")Map<String, Object> params);

	List<AppBannerDTO> list(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);

	@Insert("insert into ole_app_banner(url, file_name, content, sort, if_show, role_id, create_date) "
			+ "values (#{url}, #{file_name},#{content}, #{sort},#{if_show},#{role_id},#{create_date})")
	void save(AppBannerDTO appBannerDTO);

	@Update("update ole_app_banner set content=#{content},sort=#{sort},if_show=#{if_show},role_id=#{role_id},update_date=#{update_date} where id = #{id}")
	void update(AppBannerDTO appBannerDTO);
	
	@Update("update ole_app_banner set del_flag=0 where id = #{id}")
	void delete(@Param("id")Integer id);
	
	@Select("select * from ole_app_banner where id = #{id}")
	AppBannerDTO findOne(@Param("id")Integer id);
	
	List<AppBannerDTO> findByRoleId(@Param("roleId")Integer roleId);
}
