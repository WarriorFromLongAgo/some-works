package com.orhon.smartcampus.modules.core.graphql.gql.mutation;

import com.orhon.smartcampus.modules.base.entity.*;
import com.orhon.smartcampus.modules.base.entity.Dictionary;

import com.orhon.smartcampus.modules.base.service.*;

import com.orhon.smartcampus.modules.material.entity.Room;
import com.orhon.smartcampus.modules.material.service.*;
import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.entity.Eclass;
import com.orhon.smartcampus.modules.student.entity.Learninfo;

import com.orhon.smartcampus.modules.student.service.IArrivesService;
import com.orhon.smartcampus.modules.student.service.IEclassService;
import com.orhon.smartcampus.modules.student.service.ILearninfoService;
import com.orhon.smartcampus.modules.systemctl.entity.*;

import com.orhon.smartcampus.modules.systemctl.service.*;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;

import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeService;
import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeUserService;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.utils.ObjectToMap;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.*;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.Result;
import com.orhon.smartcampus.modules.core.graphql.gql.service.MutationUtil;

import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.service.SIInformationService;

@Component
public class SystemOutPrintln {
	/**
	 * 教师模块
	 * editor : ths
	 */
	@Autowired
	private TIInformationService tinformationService;
	@Autowired
	private IOfficeArrangeService officeArrangeService;
	@Autowired
	private IOfficeArrangeUserService officeArrangeUserService;

	/**
	 * 学生模块
	 * editor : ths
	 */
	@Autowired
	private SIInformationService informationService;
	@Autowired
	private ILearninfoService learninfoService;
	@Autowired
	private IEclassService eclassService;

	/**
	 * 基础信息模块
	 * editor : ths
	 */
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ISubjectsService subjectsService;
	@Autowired
	private IArrivesService arrivesService;
	@Autowired
	private IPeriodsService periodsService;
	@Autowired
	private ISchoolsService schoolsService;
	@Autowired
	private IRegionsService regionsService;

	/**
	 * 系统维护模块
	 * editor : ths
	 */
	@Autowired
	private IModulesService modulesService;
	@Autowired
	private IMenusService menusService;
	@Autowired
	private IWidgetsService widgetsService;
	@Autowired
	private IOperationsService operationsService;

	/**
	 * 学校基础信息-物资基础信息
	 * editor : ths
	 */
	@Autowired
	private IRoomService roomService;
	@Autowired
	private IFloorService floorService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private IBuildingsService buildingsService;
	@Autowired
	private ISiteService siteService;
	@Autowired
	private ICampusService campusService;

	/**
	 * 学校基础信息维护
	 * editor : ths
	 */
	@Autowired
	private ISchoolsettingsService schoolsettingsService;
	@Autowired
	private IOrgDepartmentsService orgDepartmentsService;



	/**
	 * 教师模块
	 * editor : ths
	 */
	private static TIInformationService staticTeacherService;
	private static IOfficeArrangeService staticOfficeArrangeService;
	private static IOfficeArrangeUserService staticOfficeArrangeUserService;


	/**
	 * 学生模块
	 * editor : ths
	 */
	private static SIInformationService staticStudentService;
	private static ILearninfoService staticLearninfoService;
	private static IEclassService staticEclassService;


	/**
	 * 基础信息模块
	 * editor : ths
	 */
	private static IDictionaryService staticDictionaryService;
	private static ISubjectsService staticSubjectsService;
	private static IArrivesService staticArrivesService;
	private static IPeriodsService staticPeriodsService;
	private static ISchoolsService staticSchoolsService;
	private static IRegionsService staticRegionsService;


	/**
	 * 系统维护模块
	 * editor : ths
	 */
	private static IModulesService staticModulesService;
	private static IMenusService staticMenusService;
	private static IWidgetsService staticWidgetsService;
	private static IOperationsService staticOperationsService;

	/**
	 * 学校基础信息-物资基础信息
	 * editor : ths
	 */
	private static IRoomService staticRoomService;
	private static IFloorService staticFloorService;
	private static IUnitService staticUnitService;
	private static IBuildingsService staticBuildingsService;
	private static ISiteService staticSiteService;
	private static ICampusService staticCampusService;

	/**
	 * 学校基础信息维护
	 * editor : ths
	 */
	private static ISchoolsettingsService staticSchoolsettingsService;
	private static IOrgDepartmentsService staticOrgDepartmentsService;

	@PostConstruct
	public void init(){
		/**
		 * 教师模块
		 * editor : ths
		 */
		staticTeacherService = tinformationService;
		staticOfficeArrangeService = officeArrangeService;
		staticOfficeArrangeUserService = officeArrangeUserService;


		/**
		 * 学生模块
		 * editor : ths
		 */
		staticStudentService = informationService;
		staticLearninfoService = learninfoService;
		staticEclassService = eclassService;


		/**
		 * 基础信息模块
		 * editor : ths
		 */
		staticDictionaryService = dictionaryService;
		staticSubjectsService = subjectsService;
		staticArrivesService = arrivesService;
		staticPeriodsService = periodsService;
		staticSchoolsService = schoolsService;
		staticRegionsService = regionsService;

		/**
		 * 系统维护模块
		 * editor : ths
		 */
		staticModulesService = modulesService;
		staticMenusService = menusService;
		staticWidgetsService = widgetsService;
		staticOperationsService = operationsService;

		/**
		 * 学校基础信息-物资基础信息
		 * editor : ths
		 */
		staticRoomService = roomService;
		staticFloorService = floorService;
		staticUnitService = unitService;
		staticBuildingsService = buildingsService;
		staticSiteService = siteService;
		staticCampusService = campusService;

		/**
		 * 学校基础信息维护
		 * editor : ths
		 */
		staticSchoolsettingsService = schoolsettingsService;
		staticOrgDepartmentsService = orgDepartmentsService;

	}

	public static DataFetcher systemOutPrintln = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			System.out.println(environment.getArgument("postId").toString());
			return "Sys.out.println";
		}
	};
	/**
	 * 教师模块
	 * editor : ths
	 */

	/**
	 * 教师新增/修改接口
	 * editor : ths
	 */
	public static DataFetcher addTeacher = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			TInformation tInformation = MutationUtil.addTeacher(environment);
			ArrayList<TInformation> list = new ArrayList<TInformation>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticTeacherService.updateById(tInformation);
				if(update) list.add(tInformation);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticTeacherService.save(tInformation);
				if(save) list.add(tInformation);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 教师删除接口
	 * editor : ths
	 */
	public static DataFetcher deleteTeacher = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							TInformation information = new TInformation();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticTeacherService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改教师办公室
	 * editor : ths
	 */
	public static DataFetcher addTeacherOffice = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			OfficeArrange officeArrange = MutationUtil.addTeacherOffice(environment);
			ArrayList<OfficeArrange> list = new ArrayList<OfficeArrange>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticOfficeArrangeService.updateById(officeArrange);
				if(update) list.add(officeArrange);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticOfficeArrangeService.save(officeArrange);
				if(save) list.add(officeArrange);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 删除教师办公室维护
	 * editor : ths
	 */
	public static DataFetcher deleteTeacherOffice = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							OfficeArrange information = new OfficeArrange();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticOfficeArrangeService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改分配办公室信息
	 * editor : ths
	 */
	public static DataFetcher addOfficeArrangeUser = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			OfficeArrangeUser officeArrangeUser = MutationUtil.addOfficeArrangeUser(environment);
			ArrayList<OfficeArrangeUser> list = new ArrayList<OfficeArrangeUser>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticOfficeArrangeUserService.updateById(officeArrangeUser);
				if(update) list.add(officeArrangeUser);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticOfficeArrangeUserService.save(officeArrangeUser);
				if(save) list.add(officeArrangeUser);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 删除分配办公室信息
	 * editor : ths
	 */
	public static DataFetcher deleteOfficeArrangeUser = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							OfficeArrangeUser information = new OfficeArrangeUser();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticOfficeArrangeUserService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};


	/**
	 * 学生模块
	 * editor : ths
	 */

	/**
	 * 学生新增/修改接口
	 */
	public static DataFetcher addStudent = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			ArrayList<SInformation> list = new ArrayList<SInformation>();
			SInformation sInformation = MutationUtil.addStudent(environment);
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticStudentService.updateById(sInformation);
				if(update) list.add(sInformation);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticStudentService.save(sInformation);
				if(save) list.add(sInformation);
				return Result.ok().put("data", list);
			}
		}
	};

	/**
	 * 学生删除接口
	 */
	public static DataFetcher deleteStudent = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					SInformation information = new SInformation();
					information.setId(id);
					information.setDeleted_at(new Date());
					staticStudentService.updateById(information);
				});
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改学生学籍基本信息
	 * editor : ths
	 */
	public static DataFetcher addLearninfo = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Learninfo learninfo = MutationUtil.addLearninfo(environment);
			ArrayList<Learninfo> list = new ArrayList<Learninfo>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticLearninfoService.updateById(learninfo);
				if(update) list.add(learninfo);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticLearninfoService.save(learninfo);
				if(save) list.add(learninfo);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 删除学生学籍基本信息
	 * editor : ths
	 */
	public static DataFetcher deleteLearninfo = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Learninfo information = new Learninfo();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticLearninfoService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改班级信息
	 * editor : ths
	 */
	public static DataFetcher addEclass = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Eclass eclass = MutationUtil.addEclass(environment);
			ArrayList<Eclass> list = new ArrayList<Eclass>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticEclassService.updateById(eclass);
				if(update) list.add(eclass);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticEclassService.save(eclass);
				if(save) list.add(eclass);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 删除班级信息
	 * editor : ths
	 */
	public static DataFetcher deleteEclass = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Eclass information = new Eclass();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticEclassService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};


	/**
	 * 基础信息模块
	 * editor : ths
	 */

	/**
	 * 新增/修改数据字典
	 * editor : ths
	 */
	public static DataFetcher addDictionary = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Dictionary dictionary = MutationUtil.addDictionary(environment);
			ArrayList<Dictionary> list = new ArrayList<Dictionary>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticDictionaryService.updateById(dictionary);
				if(update) list.add(dictionary);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticDictionaryService.save(dictionary);
				if(save) list.add(dictionary);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 数据字典删除
	 * editor : ths
	 */
	public static DataFetcher deleteDictionary = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Dictionary information = new Dictionary();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticDictionaryService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改学科信息
	 * editor : ths
	 */
	public static DataFetcher addSubject = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Subjects subject = MutationUtil.addSubject(environment);
			ArrayList<Subjects> list = new ArrayList<Subjects>();
			ArrayList grade = new ArrayList();
			ArrayList period = new ArrayList();
			if(ObjectToMap.to(environment.getArgument("inputData")).get("grade")!=null){
				grade = (ArrayList) ObjectToMap.to(environment.getArgument("inputData")).get("grade");
			}
			if(ObjectToMap.to(environment.getArgument("inputData")).get("period")!=null){
				period = (ArrayList) ObjectToMap.to(environment.getArgument("inputData")).get("period");
			}
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("period")!=null) {
				boolean update = staticSubjectsService.updateById(subject);
				if(update) list.add(subject);
				System.out.println("修改"+list);
			}else{
				boolean save = staticSubjectsService.save(subject);
				if(save) list.add(subject);
				System.out.println("创建"+list);
			}
			if(grade.size() != 0) {
				HashMap item = new HashMap();
				item.put("subject_id",list.get(0).getId());
				item.put("grade_id",grade);
				staticSubjectsService.subjectToGrade(item);
			}
			if(period.size() != 0) {
				HashMap item = new HashMap();
				item.put("subject_id",list.get(0).getId());
				item.put("period_id",period);
				staticSubjectsService.subjectToPeriod(item);
			}
			return Result.ok().put("data", list);
		}

	};

	/**
	 * 学科信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteSubject = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Subjects information = new Subjects();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticSubjectsService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改届信息
	 * editor : ths
	 */
	public static DataFetcher addArrives = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Arrives arrives = MutationUtil.addArrives(environment);
			ArrayList<Arrives> list = new ArrayList<Arrives>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticArrivesService.updateById(arrives);
				if(update) list.add(arrives);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticArrivesService.save(arrives);
				if(save) list.add(arrives);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 届信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteArrives = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Arrives information = new Arrives();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticArrivesService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改学段信息
	 * editor : ths
	 */
	public static DataFetcher addPeriod = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Periods periods = MutationUtil.addPeriods(environment);
			ArrayList<Periods> list = new ArrayList<Periods>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticPeriodsService.updateById(periods);
				if(update) list.add(periods);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticPeriodsService.save(periods);
				if(save) list.add(periods);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 学段信息删除
	 * editor : ths
	 */
	public static DataFetcher deletePeriod = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Periods information = new Periods();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticPeriodsService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改学校信息
	 * editor : ths
	 */
	public static DataFetcher addSchool = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Schools school = MutationUtil.addSchool(environment);
			ArrayList<Schools> list = new ArrayList<Schools>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticSchoolsService.updateById(school);
				if(update) list.add(school);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticSchoolsService.save(school);
				if(save) list.add(school);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 学校信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteSchool = new DataFetcher() {
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Schools information = new Schools();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticSchoolsService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改地区信息
	 * editor : ths
	 */
	public static DataFetcher addRegion = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Regions regions = MutationUtil.addRegion(environment);
			ArrayList<Regions> list = new ArrayList<Regions>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticRegionsService.updateById(regions);
				if(update) list.add(regions);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticRegionsService.save(regions);
				if(save) list.add(regions);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 地区信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteRegion = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Regions information = new Regions();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticRegionsService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};


	/**
	 * 系统维护模块
	 * editor : ths
	 */

	/**
	 * 新增/修改模块信息
	 * editor : ths
	 */
	public static DataFetcher addModules = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Modules modules = MutationUtil.addModule(environment);
			ArrayList<Modules> list = new ArrayList<Modules>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticModulesService.updateById(modules);
				if(update) list.add(modules);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticModulesService.save(modules);
				if(save) list.add(modules);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 模块信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteModules = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					Modules information = new Modules();
					information.setId(id);
					information.setDeleted_at(new Date());
					boolean save = staticModulesService.updateById(information);
				}
						);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改菜单信息
	 * editor : ths
	 */
	public static DataFetcher addMenus = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Menus menus = MutationUtil.addMenus(environment);
			ArrayList<Menus> list = new ArrayList<Menus>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticMenusService.updateById(menus);
				if(update) list.add(menus);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticMenusService.save(menus);
				if(save) list.add(menus);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 菜单信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteMenus = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					Menus information = new Menus();
					information.setId(id);
					information.setDeleted_at(new Date());
					boolean save = staticMenusService.updateById(information);
				}
						);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改页面组件
	 * editor : ths
	 */
	public static DataFetcher addWidgets = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Widgets widgets = MutationUtil.addWidgets(environment);
			ArrayList<Widgets> list = new ArrayList<Widgets>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticWidgetsService.updateById(widgets);
				if(update) list.add(widgets);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticWidgetsService.save(widgets);
				if(save) list.add(widgets);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 页面组件删除
	 * editor : ths
	 */
	public static DataFetcher deleteWidgets = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					Widgets information = new Widgets();
					information.setId(id);
					information.setDeleted_at(new Date());
					boolean save = staticWidgetsService.updateById(information);
				}
						);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改操作权限
	 * editor : ths
	 */
	public static DataFetcher addOperation = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Operations operations = MutationUtil.addOperation(environment);
			ArrayList<Operations> list = new ArrayList<Operations>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticOperationsService.updateById(operations);
				if(update) list.add(operations);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticOperationsService.save(operations);
				if(save) list.add(operations);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 操作权限删除
	 * editor : ths
	 */
	public static DataFetcher deleteOperation = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					Operations information = new Operations();
					information.setId(id);
					information.setDeleted_at(new Date());
					boolean save = staticOperationsService.updateById(information);
				}
						);
				return Result.ok();
			}
			return Result.error();
		}
	};


	/**
	 * 学校基础信息-物资基础信息
	 * editor : ths
	 */

	/**
	 * 房间管理
	 * editor : ths
	 */
	public static DataFetcher addRoom = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Room room = MutationUtil.addRoom(environment);
			ArrayList<Room> list = new ArrayList<Room>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticRoomService.updateById(room);
				if(update) list.add(room);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticRoomService.save(room);
				if(save) list.add(room);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 房间管删除
	 * editor : ths
	 */
	public static DataFetcher deleteRoom = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					Room information = new Room();
						information.setId(id);
						information.setDeleted_at(new Date());
						boolean save = staticRoomService.updateById(information);
					}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};


	/**
	 * 学校基础信息维护
	 * editor : ths
	 */

	/**
	 * 新增/修改学校设定信息
	 * editor : ths
	 */
	public static DataFetcher addSchoolSettings = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			Schoolsettings schoolsettings = MutationUtil.addSchoolSettings(environment);
			ArrayList<Schoolsettings> list = new ArrayList<Schoolsettings>();
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticSchoolsettingsService.updateById(schoolsettings);
				if(update) list.add(schoolsettings);
				return Result.ok().put("data", list);
			}else{
				boolean save = staticSchoolsettingsService.save(schoolsettings);
				if(save) list.add(schoolsettings);
				return Result.ok().put("data", list);
			}
		}

	};

	/**
	 * 学校设定信息删除
	 * editor : ths
	 */
	public static DataFetcher deleteSchoolSettings = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
							Schoolsettings information = new Schoolsettings();
							information.setId(id);
							information.setDeleted_at(new Date());
							boolean save = staticSchoolsettingsService.updateById(information);
						}
				);
				return Result.ok();
			}
			return Result.error();
		}
	};

	/**
	 * 新增/修改部门添加
	 * editor : ths
	 */
	public static DataFetcher addDepartment = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			Result result = new Result();
			OrgDepartments orgDepartments = MutationUtil.addDepartment(environment);
			ArrayList<OrgDepartments> list = new ArrayList<OrgDepartments>();
			ArrayList grade = new ArrayList();
			ArrayList subject = new ArrayList();
			ArrayList duties = new ArrayList();
			if(ObjectToMap.to(environment.getArgument("inputData")).get("grade")!=null){
				grade = (ArrayList) ObjectToMap.to(environment.getArgument("inputData")).get("grade");
			}
			if(ObjectToMap.to(environment.getArgument("inputData")).get("subject")!=null){
				subject = (ArrayList) ObjectToMap.to(environment.getArgument("inputData")).get("subject");
			}
			if(ObjectToMap.to(environment.getArgument("inputData")).get("duties")!=null){
				duties = (ArrayList) ObjectToMap.to(environment.getArgument("inputData")).get("duties");
			}
			//判断id是否存在  存在 修改  不存在就是创建
			if(ObjectToMap.to(environment.getArgument("inputData")).get("id")!=null) {
				boolean update = staticOrgDepartmentsService.updateById(orgDepartments);
				if(update) list.add(orgDepartments);
			}else{
				boolean save = staticOrgDepartmentsService.save(orgDepartments);
				if(save) list.add(orgDepartments);
			}
			if(grade.size() != 0) {
				HashMap item = new HashMap();
				item.put("department_id",list.get(grade.size()-1).getId());
				item.put("grade_id",grade);
				staticOrgDepartmentsService.departmentToGrade(item);
			}
			if(subject.size() != 0) {
				HashMap item = new HashMap();
				item.put("department_id",list.get(subject.size()-1).getId());
				item.put("subject_id",subject);
				staticOrgDepartmentsService.departmentToSubject(item);
			}
			if(duties.size() != 0) {
				HashMap item = new HashMap();
				item.put("department_id",list.get(subject.size()-1).getId());
				item.put("duty_id",duties);
				staticOrgDepartmentsService.departmentToDuties(item);
			}
			return Result.ok().put("data", list);
		}

	};

	/**
	 * 部门删除
	 * editor : ths
	 */
	public static DataFetcher deleteDepartment = new DataFetcher() {

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Object get(DataFetchingEnvironment environment) throws Exception {
			if(environment.getArgument("id")!=null) {
				List<Integer> ids = environment.getArgument("id");
				ids.forEach(id-> {
					OrgDepartments information = new OrgDepartments();
					information.setId(id);
					information.setDeleted_at(new Date());
					boolean save = staticOrgDepartmentsService.updateById(information);
				}
						);
				return Result.ok();
			}
			return Result.error();
		}
	};
}
