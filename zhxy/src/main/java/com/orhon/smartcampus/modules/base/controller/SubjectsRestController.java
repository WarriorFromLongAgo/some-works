package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Subjects;
import com.orhon.smartcampus.modules.base.service.ISubjectsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 * 学科 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/subjects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SubjectsRestController extends ApiController {
   
	@Autowired
    private ISubjectsService service;
  

    /**
     * 获取你年级列表
     *
     * @param subjects
     * @param dto
     * @return
     */
    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Subjects subjects, PageDto dto) {
        IPage<Subjects> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Subjects> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(subjects);
        IPage<Subjects> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }

    /**
     * id查询一条数据
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        Subjects byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public R delById(@PathVariable("id") Long id) {
        service.removeById(id);
        return R.ok();
    }

    /**
     * 新增一条记录
     *
     * @param Subjects
     * @return
     */    
    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Subjects subjects = JSONObject.parseObject(JSONObject.toJSONString(maps), Subjects.class);
	    boolean save = service.save(subjects);
	    if (save) {
			return R.ok().put("data", subjects);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    

	/**
	 * id修改一条记录
	 * @param Subjects
	 * @param dto
	 * @return
	 */
    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
    	Subjects subjects = JSONObject.parseObject(JSONObject.toJSONString(maps), Subjects.class);
		service.updateById(subjects);
		return R.ok().put("data", subjects);
	}
}
