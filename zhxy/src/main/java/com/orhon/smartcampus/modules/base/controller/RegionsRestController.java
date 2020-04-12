package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.modules.base.service.IRegionsService;
import com.orhon.smartcampus.modules.moral.entity.Item;
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
 * 地区信息 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/regions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RegionsRestController extends ApiController {
    
	@Autowired
    private IRegionsService service;
    
	/**
     *
     * @param regions
     * @param dto
     * @return
     */
    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Regions regions, PageDto dto) {
        IPage<Regions> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Regions> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(regions);
        IPage<Regions> pagelist = service.page(page, queryWrapper);

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
        Regions byId = service.getById(id);
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
     * @param Regions
     * @return
     */    
    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Regions regions = JSONObject.parseObject(JSONObject.toJSONString(maps), Regions.class);
	    boolean save = service.save(regions);
	    if (save) {
			return R.ok().put("data", regions);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    

	/**
	 * id修改一条记录
	 * @param Regions
	 * @param dto
	 * @return
	 */
    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
    	Regions regions = JSONObject.parseObject(JSONObject.toJSONString(maps), Regions.class);
		service.updateById(regions);
		return R.ok().put("data", regions);
	}

    @GetMapping("/get/noPageList")
    @ResponseBody
    public R noPageList(Regions regions) {
        QueryWrapper<Regions> queryWrapper = new QueryWrapper<>();
        if(regions.getParent_id()==null) {
        	regions.setParent_id(0);
        }
        queryWrapper.setEntity(regions);
        List<Regions> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }
    

    @GetMapping("/get/allList")
    @ResponseBody
    public R allList(Regions regions) {
        QueryWrapper<Regions> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(regions);
        List<Regions> list = service.list(queryWrapper);
        return R.ok().put("data", list);
    }
}
