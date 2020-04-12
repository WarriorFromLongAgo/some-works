package com.orhon.smartcampus.modules.base.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.student.entity.SInformation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/base/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DictionaryRestController extends ApiController {

	@Autowired
    private IDictionaryService service;


    @GetMapping("/get/list")
    @ResponseBody
    public R getList(Dictionary dictionary, PageDto dto) {
        IPage<Dictionary> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(dictionary);
        IPage<Dictionary> pagelist = service.page(page, queryWrapper);
        HashMap<String, Object> list = service.getDictionaryList();
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
    	Dictionary byId = service.getById(id);
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


    @PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
    	Dictionary dictionary = JSONObject.parseObject(JSONObject.toJSONString(maps), Dictionary.class);
	    boolean save = service.save(dictionary);
	    if (save) {
			return R.ok().put("data", dictionary);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
    


    @PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
    	Dictionary dictionary = JSONObject.parseObject(JSONObject.toJSONString(maps), Dictionary.class);
		service.updateById(dictionary);
        return R.ok().put("data", dictionary);
	}

    /**
     * 用mapper的方法获取数据字典选项
     * @param code
     * @return
     */
    @GetMapping("/get/bycode/{code}")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "dictionary_name"),
            @JsonForamtCmd(cmd = "raw" , okey = "dictionary_description")
    })
    public R getByCode(@PathVariable("code") String code) {
        List<HashMap<String, Object>> dicList = service.getDicOptionsByCode(code);
        return R.ok().put("data", dicList);
    }

    /**
     * 获取数据字典类型列表
     * @param dictionary
     * @return
     */
    @GetMapping("/get/category/list")
    @ResponseBody
    public R getCategoryList(Dictionary dictionary) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","category");
        queryWrapper.setEntity(dictionary);
        List<Dictionary> diclist = service.list(queryWrapper);

        return R.ok().put("data", diclist);
    }

    /**
     * 用实体类加条件的方法获取选项列表
     * @param code
     * @param dictionary
     * @return
     */
    @GetMapping("/get/options/{code}")
    @ResponseBody
    public R getOptionsList(@PathVariable("code") String code, Dictionary dictionary) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","alias","dictionary_name","value_code")
                .eq("type","option")
                .eq("dictionary_code",code)
                .orderByAsc("value_code");

        queryWrapper.setEntity(dictionary);
        List<Dictionary> diclist = service.list(queryWrapper);

        return R.ok().put("data", diclist);
    }
    /**
     * 多条件查询
     * @param maps
     * @return
     */
    @GetMapping(value="/get/dictionarycodes")
	@ResponseBody
	public R getStudentByLearninfo(@RequestParam HashMap<String, Object> maps) {
    	String[] dictionary_codes = maps.get("dictionary_code").toString().split(",");
    	HashMap<String,Object> hashMap = new HashMap<String, Object>();
    	for (String dictionary_code : dictionary_codes) {
    		 QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
    		 queryWrapper.eq("dictionary_code", dictionary_code);
    		 List<Dictionary> diclist = service.list(queryWrapper);
    		 hashMap.put(dictionary_code, diclist);
		}
	    return R.ok().put("data", hashMap);
	}
}
