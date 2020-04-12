package com.orhonit.modules.enterprise.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.enterprise.entity.NopublicFile;

/**
 *  文件
 * @author 	cyf
 * @date	2018/11/05 下午8:19:51
 */
@Mapper
public interface NopublicFileMapper extends BaseMapper<NopublicFile> {
	
	@Insert({
		"insert into nopublic_file (file_path,file_type,file_nike,file_name,suffix,create_time ) values"
		+ "(#{filePath},#{fileType},#{fileNike},#{fileName},#{suffix},#{createTime}) "
	})
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insertReturnId(NopublicFile nopublicFile);

}
