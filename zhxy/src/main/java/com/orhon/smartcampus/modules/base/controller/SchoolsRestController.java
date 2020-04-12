package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Schools;
import com.orhon.smartcampus.modules.base.service.ISchoolsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 * 学校 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/schools", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchoolsRestController extends ApiController {
   
	@Autowired
    private ISchoolsService service;


    /**
     * 获取你年级列表
     *
     * @param schools
     * @param dto
     * @return
     */
    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Schools schools, PageDto dto) {
        IPage<Schools> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Schools> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(schools);
        IPage<Schools> pagelist = service.page(page, queryWrapper);

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
        Schools byId = service.getById(id);
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
     * @param Schools
     * @return
     */    
    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Schools schools = JSONObject.parseObject(JSONObject.toJSONString(maps), Schools.class);
	    boolean save = service.save(schools);
	    if (save) {
			return R.ok().put("data", schools);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    

	/**
	 * id修改一条记录
	 * @param Schools
	 * @param dto
	 * @return
	 */
    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
    	Schools schools = JSONObject.parseObject(JSONObject.toJSONString(maps), Schools.class);
		service.updateById(schools);
		return R.ok().put("data", schools);
	}
}
