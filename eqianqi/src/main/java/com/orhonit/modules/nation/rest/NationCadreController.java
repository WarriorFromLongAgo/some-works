package com.orhonit.modules.nation.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.nation.entity.NationCadre;
import com.orhonit.modules.nation.service.NationCadreService;

/**
 * 民族干部
 * 
 * @author cyf
 * @date 2018/11/07 下午2:08:04
 */
@RestController
@RequestMapping("nationcadre")
public class NationCadreController {

	@Autowired
	private NationCadreService cadreService;
	
	

	/**
	 * 查询所有民族干部//支持name模糊和idcard准确查询以及分页
	 * @param idcard
	 * @return
	 */
	@RequestMapping(value="/people",method=RequestMethod.GET)
	public R selectNationCadreAll(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="idcard",required=false)String idcard,
			@RequestParam(value="page",required=false,defaultValue="1") Integer page,
			@RequestParam(value="pageSize",required=false,defaultValue="10") Integer pageSize){
		Map<String, Object> params =new HashMap<>();
		params.put("name", name);
		params.put("idcard", idcard);
		Map<String, Object> results = cadreService.selectNationCadreAll(params,page,pageSize);
		return R.ok().put("people",results);
	}
	
	

	/**
	 * 插入民族干部
	 * 
	 * @param nationCadre
	 * @return
	 */
	@RequestMapping(value = "/people", method = RequestMethod.POST)
	public R insertNationCadre(@RequestBody NationCadre nationCadre) {
		if (null != nationCadre) {
			return cadreService.insertNationCadre(nationCadre);
		}
		return R.parameterIsNul();
	}

	/**
	 * 修改民族干部
	 * 
	 * @param nationCadre
	 * @return
	 */
	@RequestMapping(value = "/people", method = RequestMethod.PUT)
	public R updateNationCadre(@RequestBody NationCadre nationCadre) {
		if (null != nationCadre) {
			try {
				cadreService.updateById(nationCadre);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}

	/**
	 * 删除民族干部
	 * 
	 * @param nationCadre
	 * @return
	 */
	@RequestMapping(value = "/people/{id}", method = RequestMethod.DELETE)
	public R deleteNationCadre(@PathVariable(value = "id") Long id) {
		if (null != id) {
			try {
				cadreService.deleteById(id);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}

}
