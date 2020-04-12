package com.orhon.smartcampus.modules.core.graphql.gql.service;


import java.util.*;

import com.alibaba.fastjson.JSON;
import com.orhon.smartcampus.modules.base.entity.*;
import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.material.entity.Room;
import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.entity.Eclass;
import com.orhon.smartcampus.modules.student.entity.Learninfo;
import com.orhon.smartcampus.modules.systemctl.entity.*;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;

import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLLang;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.ObjectToMap;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component
public class MutationUtil {
	
	public static GQLLang Lang(Object map) {
		if(map ==null || map.equals(null)) {
			return null;
		}
		Optional<String> a = Optional.of(((String)map) );
		GQLLang b = new GQLLang(a.or("{}"));
		return b;

	}

	/**
	 * 教师模块
	 * editor : ths
	 */

	/**
	 * 教师新增/修改接口
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static TInformation addTeacher (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		TInformation information = new TInformation();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setUser_id((Integer) map.get("user_id"));
		information.setIdcard(map.get("idcard").toString());
		//性别
		information.setGender((String)map.get("gender"));
		information.setBirthday((String) map.get("birthday"));
		//"籍贯-省";
		information.setNativeprovince((Integer) map.get("native_province_id"));
		//"籍贯-市"
		information.setNativecity((Integer) map.get("native_city_id"));
		//"籍贯-县"
		information.setNativecounty((Integer) map.get("native_county_id"));
		//"出生-省"
		information.setBirthprovince((Integer) map.get("birth_province_id"));
		//"出生-市"
		information.setBirthcity((Integer) map.get("birth_city_id"));
		//"出生-县"
		information.setBirthcounty((Integer) map.get("birth_county_id"));
		//人才类型
		information.setType((String)map.get("type"));
		information.setEmail((String)map.get("email"));
		information.setTelnumber((String)map.get("telnumber"));
		information.setNationality((String)map.get("nationality"));
		information.setPoliticaltype((String)map.get("political_type"));
		information.setWorkstatus((String)map.get("work_status"));
		information.setWorkdate((String) map.get("work_date"));
		information.setJobtitle((String)map.get("job_title"));
		information.setEducation((String)map.get("education"));
		information.setDegree((String)map.get("degree"));
		information.setMastersupervisor((String)map.get("master_supervisor"));
		information.setTeach((String)map.get("teach"));
		information.setTalentcategory_six((String)map.get("talent_category_six"));
		information.setTalentcategory_nine((String)map.get("talent_category_nine"));
		//"现住址-省"
		information.setCmbprovince3((Integer) map.get("cmbprovince3_id"));
		//"现住址-市"
		information.setCmbcity3((Integer) map.get("cmbcity3_id"));
		//"现住址-县"
		information.setCmbcounty3((Integer) map.get("cmbcounty3_id"));
		//"户籍所在地-省"
		information.setCmbprovince2((Integer) map.get("cmbprovince2_id"));
		//"户籍所在地-市"
		information.setCmbcity2((Integer) map.get("cmbcity2_id"));
		//"户籍所在地-县"
		information.setCmbcounty2((Integer) map.get("cmbcounty2_id"));
		//"学校"
		information.setSchool_id((Integer) map.get("school_id"));
		//"民族"
		information.setNation((String) map.get("nation"));
		//"公安身份证备案照片"
		information.setImgphotos((String)map.get("imgphotos"));
		//"本人签字图片"
		information.setSignimg((String)map.get("signimg"));
		//"教师姓名"
		information.setTeacher_name(JSON.toJSONString(map.get("name")));
		//"简介"
		information.setIntroduction(JSON.toJSONString(map.get("introduction")));
		return information;
	}

	/**
	 * 新增/修改教师办公室
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static OfficeArrange addTeacherOffice (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		OfficeArrange information = new OfficeArrange();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setOffice_name(JSON.toJSONString(map.get("name")));
		information.setBuildings_id((Integer) map.get("buildings_id"));
		information.setUnit_id((Integer) map.get("unit_id"));
		information.setFloor_id((Integer) map.get("floor_id"));
		information.setRoom_id((Integer) map.get("room_id"));
		information.setDepartment_id((Integer) map.get("department_id"));
		information.setHead_user((Integer) map.get("head_user"));
		return information;
	}

	/**
	 * 新增/修改分配办公室信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static OfficeArrangeUser addOfficeArrangeUser (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		OfficeArrangeUser information = new OfficeArrangeUser();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setRoom_id((Integer) map.get("room_id"));
		information.setUser_id((Integer) map.get("user_id"));
		return information;
	}

	/**
	 * 学生模块
	 * editor : ths
	 */

	/**
	 * 学生新增/修改接口
	 * @param ronment
	 * @return
	 */
	public static SInformation addStudent (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		SInformation information = new SInformation();
		information.setStudent_name(JSON.toJSONString(map.get("name")));
		information.setUsed_name(JSON.toJSONString(map.get("used_name")));
		information.setIdcard((String)map.get("idcard"));
		information.setStudent_number((String)map.get("number"));
		information.setStudent_learncode((String)map.get("learn_code"));
		information.setPinyin_name((String)map.get("pinyin_name"));
		information.setCountry((String)map.get("country"));
		information.setBirthday((String)map.get("birthday"));
		information.setSex((String)map.get("gender"));
		information.setSchool_id((Integer)map.get("school_id"));
		information.setPicture((String)map.get("picture"));
		information.setNative_place(JSON.toJSONString(map.get("native_place")));
		information.setNation((String)map.get("nation"));
		information.setHkmtb((Integer)map.get("hkmtb"));
		information.setPolitical((String)map.get("political"));
		information.setSpeciality(JSON.toJSONString(map.get("speciality")));
		information.setIdentity_ivalidity((String)map.get("identity_ivalidity"));
		information.setBlood((String)map.get("blood"));
		information.setTel_number((String)map.get("tel_number"));
		information.setEmail((String)map.get("email"));
		information.setPostal_code((String)map.get("postal_code"));
		information.setHome_page((String)map.get("home_page"));
		information.setHousehold_register_type((String)map.get("household_register_type"));
		information.setBirth_place((String)map.get("birth_place"));
		information.setPresent_address((String)map.get("present_address"));
		information.setCorresponding_address((String)map.get("corresponding_address"));
		information.setFamily_address((String)map.get("family_address"));
		information.setHousehold_registed_id((Integer)map.get("household_registed_id"));
		information.setHousehold_registed_city_id((Integer)map.get("household_registed_city_id"));
		information.setHousehold_registed_county_id((Integer)map.get("household_registed_county_id"));
		information.setCradkind_id((String)map.get("card_type"));
		information.setIs_onlychild((Integer)map.get("is_onlychild"));
		information.setIs_pre_school_education((Integer)map.get("is_pre_school_education"));
		information.setIs_left_behind_children((Integer)map.get("is_left_behind_children"));
		information.setIs_orphan((Integer)map.get("is_orphan"));
		information.setIs_martyr((Integer)map.get("is_martyr"));
		information.setIs_study_in_class((Integer)map.get("is_study_in_class"));
		information.setIs_government_degree((Integer)map.get("is_government_degree"));
		information.setIs_funding((Integer)map.get("is_funding"));
		information.setIs_supplement((Integer)map.get("is_supplement"));
		information.setIs_move_with((Integer)map.get("is_move_with"));
		information.setDisabled_status((String)map.get("disabled_status"));
		information.setHealth((String)map.get("health"));
		return information;
	}

	/**
	 * 新增/修改学生学籍基本信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Learninfo addLearninfo (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Learninfo information = new Learninfo();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setUser_id((Integer) map.get("user_id"));
		information.setStudent_id((Integer) map.get("student_id"));
		information.setSchool_id((Integer) map.get("school_id"));
		information.setArrive_id((Integer) map.get("arrive_id"));
		information.setAt_school((Integer) map.get("at_school"));
		information.setTransaction_type((String) map.get("transaction_type"));
		information.setMembership_number((String) map.get("membership_number"));
		information.setClass_code((String) map.get("class_code"));
		information.setStart_year((String) map.get("start_year"));
		information.setFinished_year((String) map.get("finished_year"));
		information.setAdmission_method((String) map.get("admission_method"));
		information.setWay_to_study((Integer) map.get("way_to_study"));
		information.setWay_to_annex((String) map.get("way_to_annex"));
		information.setStudent_from((String) map.get("student_from"));
		information.setConfirmed((Integer) map.get("confirmed"));
		information.setConfirmed_by((String) map.get("confirmed_by"));
		return information;
	}

	/**
	 * 新增/修改班级信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Eclass addEclass (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Eclass information = new Eclass();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setSchool_id((Integer) map.get("school_id"));
		information.setGrade_id((Integer) map.get("grade_id"));
		information.setArrives_id((Integer) map.get("arrives_id"));
		information.setEclass_code((String) map.get("code"));
		information.setClass_status((Integer) map.get("status"));
		information.setClass_kind_dict((Integer) map.get("kind_dict"));
		information.setClass_nature((Integer) map.get("nature"));
		information.setBuildings_id((Integer) map.get("building_id"));
		information.setUnit_id((Integer) map.get("unit_id"));
		information.setFloor_id((Integer) map.get("floor_id"));
		information.setClassroom((Integer) map.get("classroom_id"));
		information.setGraduated_flag((Integer) map.get("graduated_flag"));
		information.setEclass_order((Integer) map.get("eclass_order"));
		information.setEclass_number((String) map.get("eclass_number"));
		information.setIoc_path((String) map.get("ioc_path"));
		information.setLogo((String) map.get("logo"));
		information.setEclass_name( JSON.toJSONString(map.get("name")));
		information.setIntroduction( JSON.toJSONString(map.get("intro")));
		information.setTeacher_comment( JSON.toJSONString(map.get("teacher_comment")));
		information.setHeadteacher_message( JSON.toJSONString(map.get("head_teacher_message")));
		return information;
	}


	/**
	 * 基础信息模块
	 * editor : ths
	 */

	/**
	 * 新增/修改数据字典
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Dictionary addDictionary (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Dictionary information = new Dictionary();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setDictionary_name( JSON.toJSONString(map.get("name")));
		information.setValue_code((String) map.get("value_code"));
		information.setDictionary_code((String) map.get("dictionary_code"));
		information.setDictionary_description((String) map.get("description"));
		information.setType((String) map.get("type"));
		information.setParent_id((Integer) map.get("parent_id"));
		information.setAlias((String) map.get("alias"));
		information.setStatus((Integer) map.get("status"));
		information.setMeta((String) map.get("meta"));
		return information;
	}

	/**
	 * 新增/修改学科信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Subjects addSubject (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Subjects information = new Subjects();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setSubject_name(JSON.toJSONString(map.get("subject_name")));
		information.setSubject_slug((String) map.get("subject_slug"));
		information.setSubject_number((String) map.get("subject_number"));
		information.setSubject_order((Integer) map.get("subject_order"));
		information.setMark((String) map.get("mark"));
		information.setType((String) map.get("type"));
		information.setIcon((String) map.get("icon"));
		return information;
	}

	/**
	 * 新增/修改届信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Arrives addArrives (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Arrives information = new Arrives();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setName( JSON.toJSONString(map.get("name")));
		information.setMark((String) map.get("mark"));
		information.setGrade_id((Integer) map.get("grade"));
		information.setPeriod_id((Integer) map.get("period"));
		information.setArrives_order((Integer) map.get("order"));
		information.setBegin_year((String) map.get("begin_year"));
		information.setGraduate_year((String) map.get("graduate_year"));
		information.setSchool_id((Integer) map.get("school"));
		information.setGarduate_status((Integer) map.get("garduate_status"));
		return information;
	}

	/**
	 * 新增/修改学段信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Periods addPeriods (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Periods information = new Periods();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setPeriod_slug((String) map.get("period_slug"));
		information.setPeriod_number((String) map.get("period_number"));
		information.setPeriod_name( JSON.toJSONString(map.get("period_name")));
		information.setPeriod_order((Integer) map.get("period_order"));
		information.setMark((String) map.get("mark"));
		return information;
	}

	/**
	 * 新增/修改学校信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Schools addSchool (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Schools information = new Schools();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setSchool_name( JSON.toJSONString(map.get("school_name")));
		information.setSchool_order((Integer) map.get("school_order"));
		information.setSchool_number((String)map.get("school_number"));
		information.setIntroduction( JSON.toJSONString(map.get("introduction")));
		information.setIcon((String) map.get("icon"));
		information.setProvince_id((Integer) map.get("province_id"));
		information.setCity_id((Integer) map.get("city_id"));
		information.setDistrict_id((Integer) map.get("district_id"));
		information.setRegion_id((Integer) map.get("region_id"));
		information.setHeader_img((String) map.get("header_img"));
		information.setMain_color((String) map.get("main_color"));
		information.setMark((String) map.get("mark"));
		information.setSchool_type((String) map.get("school_type"));
		information.setCloud_status((Integer) map.get("cloud_status"));
		information.setSchool_client_key((String) map.get("client_key"));
		information.setClient_secret((String) map.get("client_secret"));
		information.setOrhonedu_base((String) map.get("orhonedu_base"));
		information.setLng((String) map.get("lng"));
		information.setLat((String) map.get("lat"));
		information.setAddress((String) map.get("address"));
		information.setParent_id((Integer) map.get("parent_id"));
		return information;
	}

	/**
	 * 新增/修改地区信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Regions addRegion (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Regions information = new Regions();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setRegion_slug((String) map.get("slug"));
		information.setRegion_name( JSON.toJSONString(map.get("name")));
		information.setParent_id((Integer) map.get("parent"));
		information.setLevel((Integer) map.get("level"));
		information.setRegion_order((Integer) map.get("order"));
		return information;
	}


	/**
	 * 系统维护模块
	 * editor : ths
	 */

	/**
	 * 新增/修改模块信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Modules addModule (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Modules information = new Modules();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setModule_name(JSON.toJSONString(map.get("module_name")));
		information.setCategory((String) map.get("category"));
		information.setModule_order((Integer) map.get("module_order"));
		information.setHome_id((Integer) map.get("home_id"));
		information.setMark((String) map.get("mark"));
		information.setIcon((String) map.get("icon"));
		information.setClients((String) map.get("clients"));
		information.setMeta((String) map.get("meta"));
		information.setStatus((Integer) map.get("status"));
		return information;
	}

	/**
	 * 新增/修改菜单信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Menus addMenus (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Menus information = new Menus();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setTitle( JSON.toJSONString(map.get("title")));
		information.setName((String) map.get("name"));
		information.setPath((String) map.get("path"));
		information.setComponent((String) map.get("component"));
		information.setModule_id((Integer) map.get("module_id"));
		information.setCategory((String) map.get("category"));
		information.setType((String) map.get("type"));
		information.setParent_id((Integer) map.get("parent"));
		information.setLevel((Integer) map.get("level"));
		information.setMeta(JSON.toJSONString(map.get("meta")));
		information.setClients(JSON.toJSONString( map.get("clients")));
		information.setIcon((String) map.get("icon"));
		information.setOrdered((Integer) map.get("ordered"));
		information.setStatus((Integer) map.get("status"));
		return information;
	}

	/**
	 * 新增/修改页面组件
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Widgets addWidgets (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Widgets information = new Widgets();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setTitle( JSON.toJSONString(map.get("title")));
		information.setName((String) map.get("name"));
		information.setApis((String) map.get("apis"));
		information.setType((String) map.get("type"));
		information.setMenu_id((Integer) map.get("menu_id"));
		information.setModule_id((Integer) map.get("module_id"));
		information.setClients((String) map.get("clients"));
		information.setMeta((String) map.get("meta"));
		information.setStatus((Integer) map.get("status"));
		return information;
	}

	/**
	 * 新增/修改操作权限
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Operations addOperation (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Operations information = new Operations();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setTitle( JSON.toJSONString(map.get("title")));
		information.setApis((String) map.get("apis"));
		information.setMenus((String) map.get("menus"));
		information.setWidgets((String) map.get("widgets"));
		information.setModule_id((Integer) map.get("module_id"));
		return information;
	}


	/**
	 * 学校基础信息-物资基础信息
	 * editor : ths
	 */

	/**
	 * 房间管理
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Room addRoom (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Room information = new Room();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setFloor_id((Integer) map.get("floor_id"));
		information.setRoom_area((Double) map.get("area"));
		information.setRoom_code((String) map.get("code"));
		information.setRoom_type((Integer) map.get("type"));
		information.setKey_num((Integer) map.get("kye_num"));
		information.setRoom_status((Integer) map.get("status"));
		information.setSchool_id((Integer) map.get("school_id"));
		information.setRoom_manager(JSON.toJSONString(map.get("manager")));
		information.setKeys_user_name( JSON.toJSONString(map.get("keys_user_name")));
		information.setName( JSON.toJSONString(map.get("name")));
		information.setRemarks( JSON.toJSONString(map.get("remarks")));
		return information;
	}


	/**
	 * 学校基础信息维护
	 * editor : ths
	 */

	/**
	 * 新增/修改学校设定信息
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static Schoolsettings addSchoolSettings (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		Schoolsettings information = new Schoolsettings();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setSchool_id((Integer) map.get("school_id"));
		information.setPlatform_name( JSON.toJSONString(map.get("platform_name")));
		information.setGrades((String) map.get("grades"));
		information.setPeriods((String) map.get("periods"));
		information.setSubjects((String) map.get("subjects"));
		information.setDuties((String) map.get("duties"));
		information.setLogo((String) map.get("logo"));
		information.setFavicon((String) map.get("favicon"));
		information.setTheme_settings((String) map.get("theme_settings"));
		information.setMeta((String) map.get("meta"));
		return information;
	}

	/**
	 * 新增/修改部门添加
	 * @param ronment
	 * @return
	 * editor : ths
	 */
	public static OrgDepartments addDepartment (DataFetchingEnvironment ronment) {
		HashMap<String,Object> map = ObjectToMap.to(ronment.getArgument("inputData"));
		OrgDepartments information = new OrgDepartments();
		if(map.get("id") != null) {
			information.setId(Integer.valueOf((String) map.get("id")));
		}
		information.setDepartment_name( JSON.toJSONString(map.get("department_name")));
		information.setParent_id((Integer) map.get("parent_id"));
		information.setDepartment_order((Integer) map.get("department_order"));
		information.setSchool_id((Integer) map.get("school_id"));
		information.setType((String) map.get("type"));
		information.setGroup_type((String) map.get("group_type"));
		information.setStatus((Integer) map.get("status"));
		return information;
	}

}
