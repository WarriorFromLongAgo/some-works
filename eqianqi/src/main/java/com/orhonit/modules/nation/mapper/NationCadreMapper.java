package com.orhonit.modules.nation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.nation.entity.NationCadre;

@Mapper
public interface NationCadreMapper extends BaseMapper<NationCadre>{
	
	/**
	 * 通过idcard查询民族干部
	 * @param idcard
	 * @return
	 */
	@Select({
		"select id,name,sex,nation,native_place nativePlace,"
		+ "id_card idCard,politics_status politicsStatus,phone,unit,duty,"
		+ "join_work_time joinWorkTime,take_office_time takeOfficeTime, "
		+ "educational_information educationalInformation,resume,recognition,create_user createUser, "
		+ "create_time createTime from nation_cadre where id_card=#{idCard}"
	})
	NationCadre selectNationCadreByIdCard(String idCard);
	
	
	
	/**
	 * 查询所有民族干部
	 * @param idcard
	 * @return
	 */
	List<NationCadre> selectNationCadreAll(Map<String, Object> params);
	
}
