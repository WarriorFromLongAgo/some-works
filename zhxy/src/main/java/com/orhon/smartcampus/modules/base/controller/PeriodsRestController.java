package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 * 学段信息 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/periods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PeriodsRestController extends ApiController {
    
	@Autowired
    private IPeriodsService service;

    /**
     * 获取你年级列表
     *
     * @param periods
     * @param dto
     * @return
     */
    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Periods periods, PageDto dto) {
        IPage<Periods> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Periods> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(periods);
        IPage<Periods> pagelist = service.page(page, queryWrapper);

        return R.ok().put("data", pagelist);
    }
    @GetMapping("/getNoPageList")
    @ResponseBody
    public R getNoPageList(Periods periods, PageDto dto) {
        QueryWrapper<Periods> queryWrapper = new QueryWrapper<>();
        List<Periods> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }

    /**
     * id查询一条数据
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        Periods byId = service.getById(id);
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
     * @param periods
     * @return
     */    
    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Periods periods = JSONObject.parseObject(JSONObject.toJSONString(maps), Periods.class);
	    boolean save = service.save(periods);
	    if (save) {
			return R.ok().put("data",periods );
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    

	/**
	 * id修改一条记录
	 * @param Periods
	 * @param dto
	 * @return
	 */
    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Periods periods = JSONObject.parseObject(JSONObject.toJSONString(maps), Periods.class);
		service.updateById(periods);
		return R.ok().put("data",periods );
	}
}
