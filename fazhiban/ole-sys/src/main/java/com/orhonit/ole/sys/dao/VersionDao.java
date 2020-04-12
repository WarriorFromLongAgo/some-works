package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.sys.dto.VersionDTO;

@Mapper
public interface VersionDao {

	int count(@Param("params")Map<String, Object> params);

	List<VersionDTO> list(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);

	@Insert("insert into ole_app_version(version_code, version_name, apk_name,create_date,create_name,create_by,content,min_support) "
			+ "values(#{version_code}, #{version_name},#{apk_name}, #{create_date},#{create_name},#{create_by},#{content},#{min_support})")
	void save(VersionDTO versionDTO);

	@Update("update ole_app_version set flag=0")
	void updateAll();
	
	@Update("update ole_app_version set flag=1,update_name=#{params.update_name},update_date=#{params.update_date},update_by=#{params.update_by} where id = #{params.id}")
	void updateOne(@Param("params")Map<String, Object> params);

	@Select("select * from ole_app_version where flag=1")
	VersionDTO getNewVersion();
	
	@Select("select * from ole_app_version where id=#{id}")
	VersionDTO getVersion(@Param("id")int id);
}
