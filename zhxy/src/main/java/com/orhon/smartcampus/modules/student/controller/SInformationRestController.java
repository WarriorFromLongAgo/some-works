package com.orhon.smartcampus.modules.student.controller;

import com.orhon.smartcampus.modules.student.entity.Learninfo;
import com.orhon.smartcampus.modules.student.service.ILearninfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.service.SIInformationService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 学生基础信息表 前端控制器
 * </p>
 *
 * @author bao
 */

@RestController
@RequestMapping(value = "/student/information", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SInformationRestController extends ApiController {

	@Autowired
	private SIInformationService service;

	@Autowired
	private InfoService infoService;
	@Autowired
	private ILearninfoService learninfoService;

	@Autowired
	private TIInformationService tinformationService ;

	/**
	 * 条件查询学生集合集合
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getStudentByLearninfo")
	@ResponseBody
	public R getStudentByLearninfo(@RequestParam HashMap<String, Object> maps) {
		Map student_name = JSON.parseObject((String)maps.get("student_name"));
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		maps.put("school_id", school_id);
		List<SInformation> list = service.getStudentByLearninfo(maps,student_name);
		return R.ok().put("data", list);
	}

	/**
	 * 分页查询某学校全部学生
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/page/getStudentByLearninfo")
	@ResponseBody
	public R getStudentByLearninfo(@RequestParam HashMap<String, Object> maps,PageDto dto) {
		Map student_name = JSON.parseObject((String)maps.get("student_name"));  
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		maps.put("school_id", school_id);
		List<SInformation> list = service.getStudentByLearninfo(maps,dto,student_name);
		int count = service.getStudentByLearninfo(maps,student_name)==null?0:service.getStudentByLearninfo(maps,student_name).size();
		return R.ok().put("data", list).put("count",count).put("limit", dto.getLimit()).put("page", dto.getPage());
	}




	/**
	 * 条件查询学生集合集合
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getEclassByStudent")
	@ResponseBody
	public R getEclassByStudent(@RequestParam HashMap<Object, Object> maps) {
		Map mapTypes = JSON.parseObject((String)maps.get("student_name"));  
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		maps.put("school_id", school_id);
		List<SInformation> list = service.getEclassByStudent(maps,mapTypes);
		return R.ok().put("data", list);
	}



	/**
	 * 学生管理系统 届 统计数量
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/grades")
	@ResponseBody
	public R managementTj() {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementTj(school_id.toString());
		return R.ok().put("data", map);
	}

	/**
	 * 学生管理系统 届 男女 总数 统计数量
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/arrives")
	@ResponseBody
	public R managementjTj() {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementjTj(school_id.toString());
		return R.ok().put("data", map);
	}

	/**
	 * 学生管理系统 入学年份 统计数量
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/startyear")
	@ResponseBody
	public R managementrxTj() {
		int i = Calendar.getInstance().get(Calendar.YEAR)-10;
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementrxTj(i+"",school_id.toString());
		return R.ok().put("data", map);
	}

	/**
	 * 学生管理系统 蒙古族男女比 统计数量
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/nation/gendersum")
	@ResponseBody
	public R managementmgznvTj() {
		int i = Calendar.getInstance().get(Calendar.YEAR)-10;
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementmgznvTj(i+"",school_id.toString());
		return R.ok().put("data", map);
	}


	/**
	 * 学生管理系统 男女比例
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/gendersum")
	@ResponseBody
	public R managementnvTj() {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementnvTj(school_id.toString());
		return R.ok().put("data", map);
	}

	/**
	 * 学生管理系统 年龄段
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/agegroup")
	@ResponseBody
	public R managemennldTj() {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementnldTj(school_id.toString());
		return R.ok().put("data", map);
	}

	/**
	 * 学生管理系统 民族统计
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/statistics/nation")
	@ResponseBody
	public R managementmzTj() {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		List<Map<String, Object>>  map = service.getManagementmzTj(school_id.toString());
		return R.ok().put("data", map);
	}




	/**
	 * 条件加分页查询集合
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(SInformation information,PageDto dto) {
		IPage<SInformation> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<SInformation> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(information);
		IPage<SInformation> pagelist = service.page(page, queryWrapper);
		return R.ok().put("data", pagelist);
	}

	/**
	 * 条件加分页查询集合
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/allList")
	@ResponseBody
	public R allList(SInformation information) {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		QueryWrapper<SInformation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("school_id", school_id);
		queryWrapper.setEntity(information);
		List<SInformation> list = service.list(queryWrapper);
		return R.ok().put("data", list);
	}

	/**
	 * id查询一条数据
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		SInformation byId = service.getById(id);
		return R.ok().put("data", byId);
	}

	/**
	 * id删除数据
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@DeleteMapping(value="/delById/{id}")
	@ResponseBody
	public R delById(@PathVariable("id") Integer id) {
		service.removeById(id);
		return R.ok().put("msg", "删除成功");
	}

	/**
	 * 新增一条记录
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		SInformation information = JSONObject.parseObject(JSONObject.toJSONString(maps), SInformation.class);
		information.setSchool_id(school_id);
		boolean save = service.save(information);
		if (save) {
			Integer studentid = information.getId();
			Learninfo studentinfo = new Learninfo();
			studentinfo.setStudent_id(studentid);
			studentinfo.setSchool_id(school_id);
			studentinfo.setAt_school(school_id);
			learninfoService.save(studentinfo);
			return R.ok().put("data",information);
		}
		return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}

	/**
	 * id修改一条记录
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		SInformation information = JSONObject.parseObject(JSONObject.toJSONString(maps), SInformation.class);
		Long currentLoginUserId = infoService.getCurrentLoginUserId();
		information.setUser_id(Integer.parseInt(currentLoginUserId.toString()));
		service.updateById(information);
		return R.ok().put("data",information);
	}

	/**
	 * 用户-教师表-班级表-分班调班-获取学生 男女比例
	 * @param SInformation
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/eclass/student/statistics")
	@FastJsonView
//    @JsonFormat({
//            @JsonForamtCmd(cmd = "raw" , okey = "eclass_name")
//    })
	@ResponseBody
	public R studentStatistics() {
		Long UserId = infoService.getCurrentLoginUserId();
		//班级统计
		List<HashMap<String,Object>> map = tinformationService.getEclassIds(UserId.toString());
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		for (HashMap<String, Object> hashMap : map) {
			HashMap<String,Object> map3 = new HashMap<String,Object>();
			
			//总数量，男女数量
			HashMap<String,Object> hashMap2 = tinformationService.getSexCount(hashMap.get("id").toString());
			map3.put("Sexcount", hashMap2);
			HashMap<String,Object> map4 = new HashMap<String,Object>();
			//民族统计
			List<HashMap<String, Object>> hashMap3 = tinformationService.getNationCount(hashMap.get("id").toString());
			for (HashMap<String, Object> hashMap4 : hashMap3) {
				
				map4.put(hashMap4.get("nation").toString(), hashMap4.get("count"));
			}
			map3.put("nation", map4);
			map3.put("eclass", hashMap);
			
			map2.put(hashMap.get("id").toString(), map3);

		}
		return R.ok().put("data", map2);
	}


}
