package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.base.service.IGradesService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.HashMap;

/**
 * <p>
 * 年级 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/grades", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GradesRestController extends ApiController {

    @Autowired
    private IGradesService service;

    /**
     * 获取你年级列表
     *
     * @param grades
     * @param dto
     * @return
     */
    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Grades grades, PageDto dto) {
        IPage<Grades> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Grades> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(grades);
        IPage<Grades> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }

    /**
     * id查询一条数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}",method=RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        Grades byId = service.getById(id);
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
     * @param Grades
     * @return
     */    
    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Grades grades = JSONObject.parseObject(JSONObject.toJSONString(maps), Grades.class);
	    boolean save = service.save(grades);
	    if (save) {
			return R.ok().put("data", grades);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    
	/**
	 * id修改一条记录
	 * @param Grades
	 * @param dto
	 * @return
	 */
    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
    	Grades grades = JSONObject.parseObject(JSONObject.toJSONString(maps), Grades.class);
		service.updateById(grades);
		return R.ok().put("data", grades);
	}

}
