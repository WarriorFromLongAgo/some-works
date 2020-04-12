package com.orhonit.modules.religion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.enterprise.entity.NopublicFile;
import com.orhonit.modules.religion.entity.ReligionSite;
import com.orhonit.modules.religion.model.ReligionSiteModel;

/**
 * 活动场所
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Mapper
public interface ReligionSiteMapper extends BaseMapper<ReligionSite> {
	
	@Insert({
		"insert into religion_site ( "
		+ "     shen,"
		+ "     shi,"
		+ "     xian,"
		+ "     type,"
		+ "     name,"
		+ "     address,"
		+ "     gacha,"
		+ "		site_type,"
		+ "     ratify_establish_office ,"
				+ "ratify_establish_time  ,"
				+ "register_office ,"
				+ "register_time ,"
				+ "register_id ,"
				+ " phone,"
				+ "area,"
				+ "cover_an_area ,"
				+ "resident_num ,"
				+ "relic_lvl ,"
				+ "put_on_records ,"
				+ "about,"
				+ "file_ids ,"
				+ "create_user ,"
				+ "create_time"
				+ ") "
	 + "values("
				+ "#{shen},"
				+ "#{shi},"
				+ "#{xian},"
				+ "#{type},"
				+ "#{name},"
				+ "#{address},"
				+ "#{gacha},"
				+ "#{siteType},"
				+ "#{ratifyEstablishOffice},"
				+ "#{ratifyEstablishTime},"
				+ "#{registerOffice},"
				+ "#{registerTime},"
				+ "#{registerId},"
				+ "#{phone},"
				+ "#{area},"
				+ "#{coverAnArea},"
				+ "#{residentNum},"
				+ "#{relicLvl},"
				+ "#{putOnRecords},"
				+ "#{about} ,"
				+ "#{fileIds} ,"
				+ "#{createUser} ,"
				+ "#{createTime}"
				+ ") "
	})
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertReturnId(ReligionSite religionSite);
	
	/**
	 * 查询所有活动场所信息,可更具所属嘎查和类型搜索
	 * @return
	 */
	@Select({
		"<script>"
		+ " 	select  s.id , " + 
		"				shen,    " + 
		"				shi,    " + 
		"				xian,    " + 
		"				type,    " + 
		"				s.name,    " + 
		"				address,    " + 
		"				s.gacha gachaId,"
		+ "             a.name gachaName,    " + 
		"				site_type siteType,     " + 
		"				ratify_establish_office ratifyEstablishOffice,    " + 
		"				ratify_establish_time ratifyEstablishTime ,    " + 
		"				register_office  registerOffice,    " + 
		"				register_time registerTime,    " + 
		"				register_id registerId,    " + 
		"				phone,    " + 
		"				s.area,    " + 
		"				cover_an_area coverAnArea ,    " + 
		"				resident_num residentNum ,    " + 
		"				relic_lvl relicLvl ,    " + 
		"				put_on_records putOnRecords,    " + 
		"				about,    " + 
		"				file_ids fileIds,    " + 
		"				s.create_user  createUser,    " + 
		"				s.create_time createTime     " + 
		"		from religion_site s "
		+ "     left join sys_area a  on a.id =s.gacha" + 
		"		<where>"
		+ "          1=1   " + 
		"			<if test=\" type!=null and type!='' \" >   " + 
		"				 and  type =#{type}   " + 
		"			</if>   " + 
		"			<if test=\" gacha!=null and gacha !='' \">   " + 
		"				 and  s.gacha=#{gacha}   " + 
		"			</if>   " + 
		"	   </where>"
		+ " </script>"
	})
	@Results({
		@Result(column="id",property="id",jdbcType=JdbcType.BIGINT),
//		@Result(column="id",property="primaryPerson",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectPrimaryPersonByReligionSiteId")),
//		@Result(column="id",property="goupPerson",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectGoupPersonByReligionSiteId")),
//		@Result(column="id",property="leadGroup",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectLeadGroupByReligionSiteId"))
		@Result(column="id",property="primaryPersonIds",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectPrimaryPersonByReligionSiteId")),
		@Result(column="id",property="goupPersonIds",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectGoupPersonByReligionSiteId")),
		@Result(column="id",property="leadGroupIds",many=@Many(select="com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper.selectLeadGroupByReligionSiteId"))
	})
	List<ReligionSiteModel> selectReligionSiteAll(String type, String gacha);
	
	
	
	
}
