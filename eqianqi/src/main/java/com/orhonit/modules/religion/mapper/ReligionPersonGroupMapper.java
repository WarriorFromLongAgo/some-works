package com.orhonit.modules.religion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.religion.entity.ReligionPerson;
import com.orhonit.modules.religion.entity.ReligionPersonGroup;

/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Mapper
public interface ReligionPersonGroupMapper extends BaseMapper<ReligionPersonGroup> {
	
	
	/**
	 * 通过活动场所id删除所有人员信息
	 * @param id
	 */
	@Delete({
		"delete from religion_person_group  where religion_site_id=#{id}"
	})
	void deleteByReligionSiteId(Long id);
	
	/**
	 * 通过活动场所id查询主要教职人员
	 * @param id
	 * @return
	 */
	@Select({
		" SELECT   group_concat(p.id separator ',') id  " + 
//		"				p.id,     " + 
//		"				p.NAME,     " + 
//		"				p.old_name oldName,     " + 
//		"				p.appellation,     " + 
//		"				p.religion,     " + 
//		"				p.their_temple theirTemple,     " + 
//		"				p.no_tenure noTenure,     " + 
//		"				p.birthday,     " + 
//		"				p.sex,     " + 
//		"				p.nation,     " + 
//		"				c.nation_name nationName,     " + 
//		"				p.education_degree,     " + 
//		"				p.graduate,     " + 
//		"				p.id_card idCard,     " + 
//		"				p.phone,     " + 
//		"				p.census_register censusRegister,     " + 
//		"				p.take_office_group_about takeOfficeGroupAbout,     " + 
//		"				p.take_office_academy_about takeOfficeAcademyAbout,     " + 
//		"				p.activity_about activityAbout,     " + 
//		"				p.certificate_unit certificateUnit,     " + 
//		"				p.certificate_id certificateId,     " + 
//		"				p.certificate_time certificateTime,     " + 
//		"				p.put_on_records putOnRecords,     " + 
//		"				p.put_on_records_time putOnRecordsTime,     " + 
//		"				p.shen,     " + 
//		"				p.shi,     " + 
//		"				p.xian,     " + 
//		"				p.remark,     " + 
//		"				p.create_user createUser,     " + 
//		"				p.create_time createTime      " + 
		"			FROM  religion_person_group g   " + 
		"			left join  religion_person p on p.id=g.religion_person_id "
		+ "         left join nation_code c on c.id = p.nation  where g.type=0 and g.religion_site_id=#{id} "
	})
	String selectPrimaryPersonByReligionSiteId(Long id);
	
	
	/**
	 * 通过活动场所id查询民主管理小组组长
	 * @param id
	 * @return
	 */
	@Select({
		" SELECT     group_concat(p.id separator ',') id " + 
//		"				p.id,     " + 
//		"				p.NAME,     " + 
//		"				p.old_name oldName,     " + 
//		"				p.appellation,     " + 
//		"				p.religion,     " + 
//		"				p.their_temple theirTemple,     " + 
//		"				p.no_tenure noTenure,     " + 
//		"				p.birthday,     " + 
//		"				p.sex,     " + 
//		"				p.nation,     " + 
//		"				c.nation_name nationName,     " + 
//		"				p.education_degree,     " + 
//		"				p.graduate,     " + 
//		"				p.id_card idCard,     " + 
//		"				p.phone,     " + 
//		"				p.census_register censusRegister,     " + 
//		"				p.take_office_group_about takeOfficeGroupAbout,     " + 
//		"				p.take_office_academy_about takeOfficeAcademyAbout,     " + 
//		"				p.activity_about activityAbout,     " + 
//		"				p.certificate_unit certificateUnit,     " + 
//		"				p.certificate_id certificateId,     " + 
//		"				p.certificate_time certificateTime,     " + 
//		"				p.put_on_records putOnRecords,     " + 
//		"				p.put_on_records_time putOnRecordsTime,     " + 
//		"				p.shen,     " + 
//		"				p.shi,     " + 
//		"				p.xian,     " + 
//		"				p.remark,     " + 
//		"				p.create_user createUser,     " + 
//		"				p.create_time createTime      " + 
		"			FROM  religion_person_group g   " + 
		"			left join  religion_person p on p.id=g.religion_person_id "
		+ "         left join nation_code c on c.id = p.nation  where g.type=1 and g.religion_site_id=#{id}  "
	})
	String selectGoupPersonByReligionSiteId(Long id);
	
	/**
	 * 通过活动场所id民主领导小组
	 * @param id
	 * @return
	 */
	@Select({
		" SELECT   group_concat(p.id separator ',') id  " + 
//		"				p.id,     " + 
//		"				p.NAME,     " + 
//		"				p.old_name oldName,     " + 
//		"				p.appellation,     " + 
//		"				p.religion,     " + 
//		"				p.their_temple theirTemple,     " + 
//		"				p.no_tenure noTenure,     " + 
//		"				p.birthday,     " + 
//		"				p.sex,     " + 
//		"				p.nation,     " + 
//		"				c.nation_name nationName,     " + 
//		"				p.education_degree,     " + 
//		"				p.graduate,     " + 
//		"				p.id_card idCard,     " + 
//		"				p.phone,     " + 
//		"				p.census_register censusRegister,     " + 
//		"				p.take_office_group_about takeOfficeGroupAbout,     " + 
//		"				p.take_office_academy_about takeOfficeAcademyAbout,     " + 
//		"				p.activity_about activityAbout,     " + 
//		"				p.certificate_unit certificateUnit,     " + 
//		"				p.certificate_id certificateId,     " + 
//		"				p.certificate_time certificateTime,     " + 
//		"				p.put_on_records putOnRecords,     " + 
//		"				p.put_on_records_time putOnRecordsTime,     " + 
//		"				p.shen,     " + 
//		"				p.shi,     " + 
//		"				p.xian,     " + 
//		"				p.remark,     " + 
//		"				p.create_user createUser,     " + 
//		"				p.create_time createTime      " + 
		"			FROM  religion_person_group g   " + 
		"			left join  religion_person p on p.id=g.religion_person_id "
		+ "         left join nation_code c on c.id = p.nation  where g.type=2  and g.religion_site_id=#{id}"
	})
	String selectLeadGroupByReligionSiteId(Long id);
	
}
