package com.orhon.smartcampus.modules.core.graphql.gql.service;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.core.graphql.gql.model.*;
import com.orhon.smartcampus.utils.ObjectToMap;

public class GQLUtil {
	//获取数据字典
	static HashMap<String, Object>  dictionaryMap = GQLRunService.DictionaryMap();
	//获取地区表
	static HashMap<String, Object>  RegionMap = GQLRunService.RegionMap();
	//获取学校表
	static HashMap<String, Object>  SchoolMap = GQLRunService.SchoolMap();
	//获取用户列表
	static HashMap<String, Object>  UserMap = GQLRunService.UserMap();
	//获取学生列表
	static HashMap<String, Object>  StudentMap = GQLRunService.SchoolMap();
	//获取教师列表
	static HashMap<String, Object>  TeacherMap = GQLRunService.TeacherMap();
	//获取教师列表
	static HashMap<String, Object>  SchoolSettingMap = GQLRunService.SchoolSettingMap();
	//获取年级列表
	static HashMap<String, Object>  GradeMap = GQLRunService.GradeMap();
	//获取学段列表
	static HashMap<String, Object>  PeriodMap = GQLRunService.PeriodMap();
	//获取学科列表
	static HashMap<String, Object>  SubjectMap = GQLRunService.SubjectMap();
	//获取职位列表
	static HashMap<String, Object>  DutiesMap = GQLRunService.DutiesMap();
	//获取部门用户关系列表
	static HashMap<String, Object>  DepartmentUsersMap = GQLRunService.DepartmentUsersMap();
	//获取部门列表
	static HashMap<String, Object>  departmentMap = GQLRunService.departmentMap();
	
	public static GQLStudent Student (HashMap<String, Object> item, RedisTemplate<String, HashMap<String, Object>> staticredisTemplate) {
		GQLStudent gqstudent = new GQLStudent();
		if(item!=null) {
			gqstudent.setId(Long.valueOf(item.get("id").toString()));
			gqstudent.setName(Lang(item.get("student_name")));
			gqstudent.setNumber((String) item.get("student_number"));
			gqstudent.setLearn_code((String) item.get("student_learncode"));
			gqstudent.setPinyin_name((String) item.get("pinyin_name"));
			gqstudent.setCountry((String) item.get("country"));
			gqstudent.setIdcard((String) item.get("idcard"));
			gqstudent.setBirthday((String) item.get("birthday"));
			gqstudent.setGender(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("sex")))));
			gqstudent.setPicture((String) item.get("picture"));
			gqstudent.setNative_place(Lang(item.get("native_place")));
			gqstudent.setNation(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("nation")))));
			gqstudent.setSpeciality(Lang(item.get("speciality") ));
			gqstudent.setBirth_place(Lang(item.get("birth_place")));
			gqstudent.setHkmtb((Integer)item.get("hkmtb"));
			gqstudent.setPolitical((String)item.get("political"));
			gqstudent.setIdentity_ivalidity((String)item.get("identity_ivalidity"));
			gqstudent.setBlood((String)item.get("blood"));
			gqstudent.setTel_number((String)item.get("tel_number"));
			gqstudent.setEmail((String)item.get("email"));
			gqstudent.setPostal_code((String)item.get("postal_code"));
			gqstudent.setHome_page((String)item.get("home_page"));
			gqstudent.setHousehold_register_type(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("household_register_type")))));
			gqstudent.setPresent_address((String)item.get("present_address"));
			gqstudent.setCorresponding_address((String)item.get("corresponding_address"));
			gqstudent.setFamily_address((String)item.get("family_address"));
			gqstudent.setIs_onlychild((Integer)item.get("is_onlychild"));
			gqstudent.setIs_pre_school_education((Integer)item.get("is_pre_school_education"));
			gqstudent.setIs_left_behind_children((Integer)item.get("is_left_behind_children"));
			gqstudent.setIs_orphan((Integer)item.get("is_orphan"));
			gqstudent.setIs_martyr((Integer)item.get("is_martyr"));
			gqstudent.setIs_study_in_class((Integer)item.get("is_study_in_class"));
			gqstudent.setIs_government_degree((Integer)item.get("is_government_degree"));
			gqstudent.setIs_funding((Integer)item.get("is_funding"));
			gqstudent.setIs_supplement((Integer)item.get("is_supplement"));
			gqstudent.setIs_move_with((Integer)item.get("is_move_with"));
			gqstudent.setDisabled_status((String)item.get("disabled_status"));
			gqstudent.setHealth((String)item.get("health"));
			gqstudent.setCreated_at(DateService.convertDbDate(item.get("created_at")));
			gqstudent.setUpdated_at(DateService.convertDbDate(item.get("updated_at")));
			gqstudent.setDeleted_at(DateService.convertDbDate(item.get("deleted_at")));	
			Object user = staticredisTemplate.opsForHash().entries("user_users{"+item.get("user_id")+"}");
			gqstudent.setUser(GQLUtil.User(ObjectToMap.to(user)));//
			
			Object school = staticredisTemplate.opsForHash().entries("base_schools{"+item.get("school_id")+"}");
			gqstudent.setSchool(GQLUtil.getSchool(ObjectToMap.to(school)));
			gqstudent.setCard_type(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("cradkind_id")))));
			if(item.get("household_registed_id")!=null) {
				gqstudent.setHousehold_registed(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("household_registed_id")+""))));
			}
			if(item.get("household_registed_city_id")!=null) {
				gqstudent.setHousehold_registed_city(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("household_registed_city")+""))));
			}
			if(item.get("household_registed_county_id")!=null) {
				gqstudent.setHousehold_registed_county(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("household_registed_county")+""))));

			}
		}
		return gqstudent;
	}

	/**
	 * 获取用户信息
	 * @param item
	 * @return
	 * editor : ths
	 */
	public static GQLUser User (HashMap<String, Object> item) {

		GQLUser gqluser = new GQLUser();
		if(item.get("use_id")!=null) {
			gqluser.setId(Long.valueOf(item.get("use_id").toString()));
		}
		gqluser.setEmail((String) item.get("use_email"));
        gqluser.setUsername((String) item.get("username"));
        gqluser.setPassword((String)item.get("use_password"));
		gqluser.setStatus((Integer) item.get("use_status"));
//		if(item.get("sch_id")!=null) {
//			gqluser.setSchool(getSchool(item));
//		}
		gqluser.setIdcard((String) item.get("use_idcard"));
		gqluser.setLast_login_ip((String) item.get("use_last_login_ip"));
		
		gqluser.setLast_login_time(DateService.convertDbDate(item.get("use_last_login_time")));
		gqluser.setRemember_token((String) item.get("use_remember_token"));
		gqluser.setApi_token((String) item.get("use_api_token"));
		gqluser.setCreated_at(DateService.convertDbDate(item.get("ues_created_at")));
		gqluser.setUpdated_at(DateService.convertDbDate(item.get("ues_updated_at")));
		gqluser.setDeleted_at(DateService.convertDbDate(item.get("ues_deleted_at")));
		gqluser.setUser_type( GQLType.UserType.valueOf(item.get("use_user_type")==null?"other":(String) item.get("use_user_type")));
		gqluser.setSystem_lang((String) item.get("use_system_lang"));
		gqluser.setMobile((String) item.get("use_mobile"));
		gqluser.setEquipment((String) item.get("use_equipment"));
		gqluser.setUnion_id((String) item.get("use_unionid"));
		return gqluser;
	}

	/**
	 * 届管理
	 * @param item
	 * @return
	 */
	public static GQLArrives Arrives (HashMap<String, Object> item) {

		GQLArrives gqlArrives = new GQLArrives();
		if(item!=null&&item.size()>0) {
			gqlArrives.setId(Long.valueOf(item.get("id").toString()));
			gqlArrives.setMark((String)item.get("mark"));
			gqlArrives.setName(Lang(item.get("name")));
			gqlArrives.setGrade(GQLUtil.grades(ObjectToMap.to(GradeMap.get((String)item.get("grade")))));
			gqlArrives.setPeriod(GQLUtil.period(ObjectToMap.to(PeriodMap.get((String)item.get("period")))));
			gqlArrives.setOrder((Integer)item.get("order"));
			gqlArrives.setBegin_year((String) item.get("begin_year"));
			gqlArrives.setGraduate_year((String) item.get("graduate_year"));
			gqlArrives.setSchool(GQLUtil.getSchool(ObjectToMap.to(SchoolMap.get((String)item.get("school")))));
		}
		return gqlArrives;
	}

	/**
	 * 年级管理
	 * @param item
	 * @return
	 */
	public static GQLGrades grades (HashMap<String, Object> item) {

		GQLGrades gqlGrades = new GQLGrades();
		if(item!=null&&item.size()>0) {
			gqlGrades.setId(Long.valueOf(item.get("id").toString()));
			gqlGrades.setSlug((String)item.get("grade_slug"));
			gqlGrades.setMark((String)item.get("mark"));
			gqlGrades.setNumber((String)item.get("grade_number"));
			gqlGrades.setOrder((Integer) item.get("grade_order"));
			gqlGrades.setPeriod((GQLPeriod)item.get("period_id"));
			gqlGrades.setName(Lang(item.get("grade_name").toString()));
		}
		return gqlGrades;
	}

	/**
	 * 学段管理
	 * @param item
	 * @return
	 */
	public static GQLPeriod period (HashMap<String, Object> item) {

		GQLPeriod gqlPeriod = new GQLPeriod();
		if(item!=null&&item.size()>0) {
			gqlPeriod.setId(Long.valueOf(item.get("id").toString()));
			gqlPeriod.setSlug((String)item.get("period_slug"));
			gqlPeriod.setNumber((String)item.get("period_number"));
			gqlPeriod.setOrder((Integer) item.get("period_order"));
			gqlPeriod.setMark((String)item.get("mark"));
			gqlPeriod.setName(Lang(item.get("period_name").toString()));
		}
		return gqlPeriod;
	}
	public static GQLRegion Region (HashMap<String,Object> item) {
		GQLRegion gqlregion = new GQLRegion();
		if(item!=null&&item.size()>0) {
			gqlregion.setId(Long.valueOf(item.get("id").toString()));
			gqlregion.setSlug((String)item.get("region_slug"));
			gqlregion.setLevel((Integer)item.get("level"));
			gqlregion.setOrder((Integer)item.get("region_order"));
//			gqlregion.setCreated_at(DateService.convertDbDate(item.get("created_at")));
//			gqlregion.setUpdated_at(DateService.convertDbDate(item.get("updated_at")));
//			gqlregion.setDeleted_at(DateService.convertDbDate(item.get("deleted_at")));
			gqlregion.setName(Lang(item.get("region_name").toString()));
		}
		return gqlregion;
	}


	public static GQLLang Lang(Object item) {
		if(item ==null || item.equals(null)) {
			return null;
		}
		//Optional<String> a = Optional.of(((String)item) );
		Optional<String> a = Optional.of((item.toString()) );
		GQLLang b = new GQLLang(a.or("{}"));
		return b;

	}

	/**
	 * 获取教师信息
	 * @param item
	 * @return
	 * editor : ths
	 */
	public static GQLTeacher Teacher (HashMap<String, Object> item) {
		GQLTeacher gqlteacher = new GQLTeacher();
		gqlteacher.setId(Long.valueOf(item.get("id").toString()));
		if(item.get("use_id")!=null) {
			gqlteacher.setUser(User(item));
		}
		gqlteacher.setIdcard((String)item.get("idcard"));
		//性别
		gqlteacher.setGender(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("gender")))));
		gqlteacher.setBirthday((String) item.get("birthday"));
		//"籍贯-省";
		if(item.get("nativeprovince")!=null) {
			gqlteacher.setNative_province(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("nativeprovince")+""))));

		}
		//"籍贯-市"
		if(item.get("nativecity")!=null) {
			gqlteacher.setNative_city(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("nativecity")+""))));

		}
		//"籍贯-县"
		if(item.get("nativecounty")!=null) {
			gqlteacher.setNative_county(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("nativecounty")+""))));

		}
		//"出生-省"
		if(item.get("birthprovince")!=null) {
			gqlteacher.setBirth_province(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("birthprovince")+""))));

		}
		//"出生-市"
		if(item.get("birthcity")!=null) {
			gqlteacher.setBirth_city(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("birthcity")+""))));

		}
		//"出生-县"
		if(item.get("birthcounty")!=null) {
			gqlteacher.setBirth_county(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("birthcounty")+""))));

		}
		//人才类型
		gqlteacher.setType(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("type")))));
		gqlteacher.setEmail((String)item.get("email"));
		gqlteacher.setTelnumber((String)item.get("telnumber"));
		gqlteacher.setNationality(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("nationality")))));
		gqlteacher.setPolitical_type(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("politicaltype")))));
		gqlteacher.setWork_status(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("workstatus")))));
		gqlteacher.setWork_date((String) item.get("workdate"));
		gqlteacher.setJob_title(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("jobtitle")))));
		gqlteacher.setEducation(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("education")))));
		gqlteacher.setDegree(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("degree")))));
		gqlteacher.setMaster_supervisor(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("mastersupervisor")))));
		gqlteacher.setTeach(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("teach")))));
		gqlteacher.setTalent_category_six(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("talentcategory_six")))));
		gqlteacher.setTalent_category_nine(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String)item.get("talentcategory_nine")))));
		//"现住址-省"
		if(item.get("cmbprovince3")!=null) {
			gqlteacher.setCmbprovince3(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbprovince3")+""))));

		}
		//"现住址-市"
		if(item.get("cmbcity3")!=null) {
			gqlteacher.setCmbcity3(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbcity3")+""))));

		}
		//"现住址-县"
		if(item.get("cmbcounty3")!=null) {
			gqlteacher.setCmbcounty3(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbcounty3")+""))));

		}
		//"户籍所在地-省"
		if(item.get("cmbprovince2")!=null) {
			gqlteacher.setCmbprovince2(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbprovince2")+""))));

		}
		//"户籍所在地-市"
		if(item.get("cmbcity2")!=null) {
			gqlteacher.setCmbcity2(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbcity2")+""))));

		}
		//"户籍所在地-县"
		if(item.get("cmbcounty2")!=null) {
			gqlteacher.setCmbcounty2(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("cmbcounty2")+""))));

		}
		gqlteacher.setType(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String) item.get("type")))));
		//"学校"
		if(item.get("sch_id")!=null) {
			gqlteacher.setSchool(getSchool(item));
		}
		//"民族"
		gqlteacher.setNation(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get((String) item.get("nation")))));
		//"公安身份证备案照片"
		gqlteacher.setImgphotos((String)item.get("imgphotos"));
		//"本人签字图片"
		gqlteacher.setSignimg((String)item.get("signimg"));
		//"教师姓名"
		gqlteacher.setName(Lang(item.get("teacher_name")));
		//"简介"
		gqlteacher.setIntroduction(Lang(JSONObject.parse((String) item.get("introduction")).toString()));
		gqlteacher.setCreated_at(DateService.convertDbDate(item.get("created_at")));
		gqlteacher.setUpdated_at(DateService.convertDbDate(item.get("updated_at")));
		gqlteacher.setDeleted_at(DateService.convertDbDate(item.get("deleted_at")));
		return gqlteacher;
	}
	/**
	 * 获取学校信息
	 * @param item
	 * @return
	 * editor : ths
	 */
	public static GQLSchool getSchool(HashMap<String, Object> item){
		GQLSchool school = new GQLSchool();
		if(item.get("sch_id")!=null) {
			school.setId(Long.valueOf(item.get("sch_id").toString()));
		}
		school.setName(Lang(item.get("school_name")));
		school.setOrder((Integer) item.get("sch_school_order"));
		school.setNumber((String) item.get("sch_school_number"));
		school.setIntroduction(Lang(item.get("sch_introduction")));
		school.setIcon((String) item.get("icon"));
		school.setOrder((Integer) item.get("sch_school_order"));
		school.setProvince_id(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("sch_province_id")+""))));
		school.setCity_id(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("sch_city_id")+""))));
		school.setDistrict_id(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("sch_district_id")+""))));
		school.setRegion(GQLUtil.Region(ObjectToMap.to(RegionMap.get(item.get("sch_region_id")+""))));
		school.setHeader_img((String) item.get("sch_header_img"));
		school.setMain_color((String) item.get("sch_main_color"));
		school.setMark((String) item.get("sch_mark"));
		school.setSchool_type(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get(item.get("sch_school_type")+""))));
		school.setCloud_status((Integer) item.get("sch_cloud_status"));
		school.setClient_key((String) item.get("sch_school_client_key"));
		school.setClient_secret((String) item.get("sch_client_secret"));
		school.setOrhonedu_base((String) item.get("sch_orhonedu_base"));
		school.setLng((String) item.get("sch_lng"));
		school.setLat((String) item.get("sch_lat"));
		school.setAddress((String) item.get("sch_address"));
		//school.setParent(GQLUtil.getSchool(ObjectToMap.to(SchoolMap.get(item.get("sch_parent")+""))));
		//school.setUsers(GQLUtil.User(ObjectToMap.to(UserMap.get(item.get("sch_users")+""))));
		//school.setStudents(GQLUtil.Student(ObjectToMap.to(StudentMap.get(item.get("sch_students")+""))));
		//school.setTeachers(GQLUtil.Teacher(ObjectToMap.to(TeacherMap.get(item.get("sch_teachers")+""))));
		//school.setSettings(GQLUtil.getSchoolSetting(ObjectToMap.to(SchoolSettingMap.get(item.get("sch_settings")+""))));
		school.setCreated_at(DateService.convertDbDate(item.get("sch_created_at")));
		school.setUpdated_at(DateService.convertDbDate(item.get("sch_updated_at")));
		school.setDeleted_at(DateService.convertDbDate(item.get("sch_deleted_at")));
//		school.setType();
		return school;
	}
	/**
	 *获取学校设置信息
	 */
	public static GQLSchoolSettings getSchoolSetting(HashMap<String, Object> item){
		GQLSchoolSettings schoolSettings = new GQLSchoolSettings();
		if(item!=null) {
			schoolSettings.setSchool_id(Long.parseLong(item.get("set_school_id").toString()));
			schoolSettings.setPlatform_name(Lang(item.get("set_platform_name").toString()));
			schoolSettings.setGrades(GQLUtil.grades(ObjectToMap.to(GradeMap.get(item.get("set_grades")+""))));
			schoolSettings.setPeriods(GQLUtil.period(ObjectToMap.to(PeriodMap.get(item.get("set_periods")+""))));
			schoolSettings.setSubjects(GQLUtil.subject(ObjectToMap.to(SubjectMap.get(item.get("set_subjects")+""))));
			schoolSettings.setDuties(GQLUtil.duties(ObjectToMap.to(DutiesMap.get(item.get("set_duties")+""))));
			schoolSettings.setDutilogoes((String)item.get("set_dutilogoes"));
			schoolSettings.setFavicon((String)item.get("set_favicon"));
			schoolSettings.setTheme_settings((String)item.get("set_theme_settings"));
			schoolSettings.setMeta((String)item.get("set_meta"));
		}
		return schoolSettings;
	}
	/**
	 * 获取学科信息
	 */
	public static GQLSubject subject(HashMap<String, Object> item){
		GQLSubject subject = new GQLSubject();
		if(item!=null) {
			subject.setId(Long.parseLong(item.get("sub_id").toString()));
			subject.setSubject_name(Lang(item.get("sub_subject_name").toString()));
			subject.setSubject_slug((String)item.get("sub_subject_slug"));
			subject.setSubject_number((String)item.get("sub_subject_number"));
			subject.setSubject_order((Integer)item.get("sub_subject_order"));
			subject.setMark((String)item.get("sub_mark"));
			subject.setType((String)item.get("sub_type"));
			subject.setPeriod(GQLUtil.period(ObjectToMap.to(PeriodMap.get(item.get("sub_period")+""))));
			subject.setGrade(GQLUtil.grades(ObjectToMap.to(GradeMap.get(item.get("sub_grade")+""))));

		}
		return subject;
	}
	/**
	 * 职位列表信息
	 */
	public static GQLDuty duties(HashMap<String, Object> item){
		GQLDuty duty = new GQLDuty();
		if(item!=null) {
			duty.setId(Long.parseLong(item.get("duties_id").toString()));
			duty.setDuties_name(Lang(item.get("duties_name").toString()));
			duty.setDuties_order((Integer)item.get("duties_order"));
			duty.setParent(GQLUtil.duties(ObjectToMap.to(DutiesMap.get(item.get("duties_parent")+""))));
			duty.setLevel((String)item.get("duties_level"));
			duty.setUsers(GQLUtil.DepartmentUsers(ObjectToMap.to(DepartmentUsersMap.get(item.get("duties_users")+""))));
		}
		return duty;
	}
	/**
	 * 部门与人员关系列表
	 */
	public static GQLDepartmentUsers DepartmentUsers(HashMap<String, Object> item){
		GQLDepartmentUsers departmentUsers = new GQLDepartmentUsers();
		if(item!=null) {
			departmentUsers.setId(Long.parseLong(item.get("id").toString()));
			departmentUsers.setEmail((String)item.get("email"));
			departmentUsers.setGender(GQLUtil.getDictionary(ObjectToMap.to(dictionaryMap.get(item.get("gender")+""))));
			departmentUsers.setIdcard((String)item.get("idcard"));
			departmentUsers.setMobile((String)item.get("mobile"));
			departmentUsers.setImgphotos((String)item.get("imgphotos"));
			departmentUsers.setWorkdate((String)item.get("workdate"));
			departmentUsers.setIs_leaders((Integer) item.get("is_leaders"));
			departmentUsers.setLeave_at((Date) item.get("leave_at"));
			departmentUsers.setDepartment(GQLUtil.getDepartment(ObjectToMap.to(departmentMap.get(item.get("department")+""))));
			departmentUsers.setDuty(GQLUtil.duties(ObjectToMap.to(DutiesMap.get(item.get("duty")+""))));

		}
		return departmentUsers;
	}
	/**
	 * 部门列表
	 */
	public static GQLDepartment getDepartment(HashMap<String, Object> item){
		GQLDepartment department = new GQLDepartment();
		if(item!=null) {
			department.setId(Long.parseLong(item.get("id").toString()));
			department.setDepartment_name(Lang(item.get("department_name").toString()));
			department.setParent(GQLUtil.getDepartment(ObjectToMap.to(departmentMap.get(item.get("parent")+""))));
			department.setDepartment_order((Integer) item.get("department_order"));
			department.setSchool(GQLUtil.getSchool(ObjectToMap.to(SchoolMap.get(item.get("school")+""))));
			department.setType((String) item.get("type"));
			department.setGroup_type((String) item.get("group_type"));
			department.setStatus((Integer) item.get("status"));
			department.setGrade(GQLUtil.grades(ObjectToMap.to(GradeMap.get(item.get("grade")+""))));
			department.setSubject(GQLUtil.subject(ObjectToMap.to(SubjectMap.get(item.get("subject")+""))));
			department.setDuties(GQLUtil.duties(ObjectToMap.to(DutiesMap.get(item.get("duties")+""))));
			department.setUsers(GQLUtil.DepartmentUsers(ObjectToMap.to(DepartmentUsersMap.get(item.get("users")+""))));
		}
		return department;
	}


	/**
	 * 数据字典
	 */

	public static GQLDictionary getDictionary(HashMap<String, Object> item){
		GQLDictionary dictionary = new GQLDictionary();
		if(item!=null) {
			dictionary.setId(Long.parseLong(item.get("id").toString()));
			dictionary.setParent_id(Integer.parseInt(item.get("parent_id").toString()));
			dictionary.setAlias((String)item.get("alias"));
			dictionary.setType((String)item.get("type"));
			dictionary.setName(Lang(item.get("dictionary_name").toString()));
			dictionary.setDescription(Lang(item.get("dictionary_description").toString()));
			dictionary.setStatus(item.get("status")+"");
			dictionary.setMeta(Lang(item.get("meta")));
			dictionary.setValue((String)item.get("dictionary_value"));	
		}
		return dictionary;
	}
}
