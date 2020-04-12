package com.orhon.smartcampus.modules.document.controller;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.document.entity.Teacher;
import com.orhon.smartcampus.modules.document.service.IExamineService;
import com.orhon.smartcampus.modules.document.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.document.entity.Circulation;
import com.orhon.smartcampus.modules.document.service.ICirculationService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/document/circulation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CirculationRestController extends ApiController {

	@Autowired
	private ICirculationService service;
	@Autowired
	private IExamineService examineService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private InfoService infoService;
	
	/**
	 * 条件加分页查询集合
	 * @param Circulation
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
		HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		Integer examine_status = (Integer)infoService.getCurrentUser().get("examine_status");
		maps.put("school_id", school_id);
		maps.put("examine_status", examine_status);
		maps.put("semester_id", Semester.get("id"));
		List<HashMap<String, Object>> list = service.getDocument(maps,dto);
		return R.ok().put("data", list).put("count", service.getDocument(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
	}
	//我发布的列表
	@GetMapping(value="/myGetList")
	@ResponseBody
	public R myGetList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
		HashMap<String,Object> Semester =  infoService.getCurruentUsresSemster();
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		Integer examine_status = (Integer)infoService.getCurrentUser().get("examine_status");
		Integer user_id = (Integer)infoService.getCurrentUser().get("id");
		maps.put("school_id", school_id);
		maps.put("created_by", user_id);
		maps.put("examine_status", examine_status);
		maps.put("semester_id", Semester.get("id"));
		List<HashMap<String, Object>> list = service.getDocument(maps,dto);
		return R.ok().put("data", list).put("count", service.getDocument(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
	}
	//待我审核的列表
	@GetMapping(value="/myExamineList")
	@ResponseBody
	public R myExamineList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
		Integer user_id = (Integer)infoService.getCurrentUser().get("id");
		maps.put("user_id", user_id);
		List<HashMap<String, Object>> list = examineService.getDocument(maps,dto);
		return R.ok().put("data", list).put("count", examineService.getDocument(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
	}
	/**
	 * id查询一条数据
	 * @param Circulation
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Circulation byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Circulation
	 * @param bao
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
	 * @param Circulation
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps,@RequestParam(required = false) MultipartFile[] file) {
//		ArrayList<Object> files = new ArrayList<>();
//		if(file != null){
//			for (MultipartFile multipartFile : file) {
//				files.add(multipartFile);
//			}
//		}
//		maps.put("files",files);
		return service.inserts(maps);
	}
		
	/**
	 * id修改一条记录
	 * @param Circulation
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Circulation circulation = JSONObject.parseObject(JSONObject.toJSONString(maps), Circulation.class);
		service.updateById(circulation);
		return R.ok().put("data", circulation);
	}
	//下发给我的公文列表
	@GetMapping(value="/toMeList")
	@ResponseBody
	public R toMeList(@RequestParam HashMap<String, Object> maps,PageDto dto) {
		Integer user_id = (Integer)infoService.getCurrentUser().get("id");
		maps.put("user_id", user_id);
		List<HashMap<String, Object>> list = teacherService.getDocument(maps,dto);
		return R.ok().put("data", list).put("count", teacherService.getDocument(maps).size()).put("limit", dto.getLimit()).put("page", dto.getPage());
	}
}
