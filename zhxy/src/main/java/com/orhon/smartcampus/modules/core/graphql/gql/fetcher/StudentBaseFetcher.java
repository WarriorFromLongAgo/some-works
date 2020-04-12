package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.base.service.IRegionsService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLLang;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLSchool;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLStudent;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLUser;
import com.orhon.smartcampus.modules.core.graphql.gql.model.Result;
import com.orhon.smartcampus.modules.core.graphql.orm.entity.Student;
import com.orhon.smartcampus.modules.document.mapper.StudentMapper;
import com.orhon.smartcampus.modules.student.service.SIInformationService;
import org.hibernate.Query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class StudentBaseFetcher {

	@Autowired
	private SIInformationService informationService;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IRegionsService regionsService;
	
	 @Autowired
	    private SessionFactory sessionFactory;
	    private static SessionFactory statisSessionFactory;
	


	private static SIInformationService staticStudentService;

	private static IDictionaryService staticdictionaryService;

	private static IRegionsService staticregionsService;

	private static StudentMapper staticStudentMapper;



	@PostConstruct
	public void init(){
		staticStudentMapper = studentMapper;
		staticStudentService = informationService;
		staticdictionaryService = dictionaryService;
		staticregionsService = regionsService;
		statisSessionFactory = sessionFactory;
	}


	/**
	 * 处理students请求...
	 */
	public static DataFetcher studentsFetcher = new DataFetcher() {

		@Override
		public Object get(DataFetchingEnvironment env) throws Exception {
//			ArrayList<GQLStudent> student = new ArrayList<>();
//
//			Integer page = 0;
//			Integer limit = 10;
//
//			if (env.containsArgument("page")){
//				page = Integer.parseInt(env.getArgument("page").toString());
//			}
//
//			if (env.containsArgument("limit")){
//				limit = Integer.parseInt(env.getArgument("limit").toString());
//			}
//
//			if (page < 0) page = 0;
//			if (page != 0) page = (page - 1) * limit;
//
//			HashMap<String,Object> map = new HashMap<String,Object>();
//			map.put("page", page);
//			map.put("limit", limit);
//			map.put("withUser", env.getSelectionSet().contains("data/user"));
//			map.put("withSchool", env.getSelectionSet().contains("data/school"));
//			
//			
//			
//			List<HashMap<String, Object>> list = staticStudentService.PageListStudent(map);
//			Integer count =  staticStudentService.StudentCount(map);
			
			
			
			//============================
			
			 List<GQLStudent> students = new ArrayList<>();
			    String counthql = "from count(*) Student";
	            String hql = "from Student";

	            Query q = statisSessionFactory.openSession().createQuery(hql);
	            q.setMaxResults(10);
	            q.setFirstResult(1);

	            List<Student> list = q.list();

	            list.forEach(item -> {
	            	GQLStudent stu = new GQLStudent();
	                stu.setName(Lang(item.getStudent_name()));
	                stu.setIdcard(item.getIdcard());
                    

	                GQLSchool school = new GQLSchool();
	                school.setId(item.getSchool().getId());
	                school.setName(Lang(item.getSchool().getSchool_name()));
	                stu.setSchool(school);
	                
	                
	                GQLUser user = new GQLUser();
	                user.setId(item.getUser().getId());
	                user.setUsername(item.getUser().getUsername());
	                stu.setUser(user);
	                
	                students.add(stu);
	            });

	            return Result.ok().put("data", students);
			
			
			//=============================
			
			
//			//分页参数
//			HashMap<String,Object> hashMap = new HashMap<String,Object>();
//			hashMap.put("total", count);
//			hashMap.put("current", page);
//			hashMap.put("limit", limit);
//			
//			list.forEach(item->{
//				RemoveNullKeyValueUtils.removeNullValue(item);
//				GQLStudent gqlStudent = GQLUtil.Student(item);
//				student.add(gqlStudent);
//			});
			//return Result.ok().put("data", student).put("pagenation", hashMap);
		}

		
	};
	public static GQLLang Lang(Object item) {
		if(item ==null || item.equals(null)) {
			return null;
		}
		Optional<String> a = Optional.of(((String)item) );
		GQLLang b = new GQLLang(a.or("{}"));
		return b;

	}

}
