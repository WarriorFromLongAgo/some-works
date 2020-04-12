package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.entity.SupervisoryEntity;
import com.orhonit.modules.generator.service.SupervisoryService;


/**
 * 
 * Title: SupervisoryController.java
 * @Description:干部职工个人监督信息
 * @author YaoSC
 * @date 2019年7月20日
 */
@RestController
@RequestMapping(value="supervisory",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SupervisoryController {
	
	@Autowired
	SupervisoryService service;
	
	 /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody HashMap<String, Object> maps){
    	SupervisoryEntity entity = JSONObject.parseObject(JSONObject.toJSONString(maps), SupervisoryEntity.class);
    	entity.setCreateTime(new Date());
    	entity.setUpdateTime(new Date());
    	entity.setIsDel(CommonParameters.isDel.IS_DEL_NO);
    	service.insert(entity);
		return R.ok();
    	
    }
    
    /**
     * 干部职工个人监督详细信息
     * @param id
     * @return
     * @throws IOException 
     */
    @GetMapping("/info/{id}")
	
	/*
	 * @FastJsonView
	 * 
	 * @JsonFormat({
	 * 
	 * @JsonFormatCmd(cmd = "raw" , okey = "mainWork"),
	 * 
	 * @JsonFormatCmd(cmd = "raw" , okey = "project") })
	 */
	 
    @ResponseBody
	public String info(@PathVariable("id") Integer id) throws IOException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	SupervisoryEntity entity =service.getOneSuper(id);
    	String mainWork=null;
    	String project=null;
    	if(null!=entity.getMainWork() ){
    		 mainWork=StringEscapeUtils.unescapeJava(new String(entity.getMainWork().getBytes("ISO-8859-1"),"UTF-8"));
    	}
    	if(null!=entity.getProject()) {
    		project =StringEscapeUtils.unescapeJava(new String(entity.getProject().getBytes("ISO-8859-1"),"utf-8"));
    	}
    	entity.setProject(project);
    	entity.setMainWork(mainWork);
    	//String teststringstu=objectMapper.writeValueAsString(entity);
    	//JsonNode node= objectMapper.readTree(teststringstu);  
		//return R.ok().put("SupervisoryEntity", entity);
    	return JSON.toJSONString(entity);
	}
    
    
    /**
         * 删除  更新is_del=1 
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
    	SupervisoryEntity entity = new SupervisoryEntity();
    	entity.setUpdateTime(new Date());
    	entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
    	service.update(entity, new EntityWrapper<SupervisoryEntity>().eq("id", id));
    	//service.deleteById(id);
    	return R.ok().put("delete","删除成功");
    }
	
    /**
     * 更新
     * @param t
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody SupervisoryEntity t){
    	t.setUpdateTime(new Date());
		service.updateById(t);

      return R.ok().put("up", "更新成功");
  }
	/**
	 * 列表
	 * @param params
	 * @return
	 */
    @GetMapping("/superverList")
    public R superverList(@RequestParam Map<String, Object> params) {
    	
    	return R.ok().put("page", service.getListPage(params));
    }
    
    
    /**
         * 删除附件
     * @param path 附件路径
     * @param id   个人信息ID
     * @param type 1 病假假条，2 事假假条，3 休假假条，4 外出文件，5  培训文件
     * @return
     */
    @PostMapping("/deleteFile")
    public R delFile(@RequestParam("path") String path,@RequestParam("id")Integer id,@RequestParam("type")String type) {
    	String resultInfo = null;
		int lastIndexOf = path.lastIndexOf("/");
		String sb = path.substring(lastIndexOf + 1, path.length());
		File file = new File(sb);
		if (file.exists()) {
			if (file.delete()) {
				resultInfo =  "1-删除成功";
				if(id>0 && !type.equals(null)||!"".equals(type)) {
					SupervisoryEntity en = new SupervisoryEntity();
					switch(type) {
					case "1":
						en.setUpdateTime(new Date());
						en.setSickUrl(" ");
						service.update(en, new EntityWrapper<SupervisoryEntity>().eq("id", id));
						break;
					case "2":
						en.setUpdateTime(new Date());
						en.setCompassionateUrl(" ");
						service.update(en, new EntityWrapper<SupervisoryEntity>().eq("id", id));
						break;
					case "3":
						en.setUpdateTime(new Date());
						en.setVacationUrl(" ");
						service.update(en, new EntityWrapper<SupervisoryEntity>().eq("id", id));
						break;
					case "4":
						en.setUpdateTime(new Date());
						en.setGoOutUrl(" ");
						service.update(en, new EntityWrapper<SupervisoryEntity>().eq("id", id));
						break;
					case "5":
						en.setUpdateTime(new Date());
						en.setTrainUrl(" ");
						service.update(en, new EntityWrapper<SupervisoryEntity>().eq("id", id));
						break;
					default:
						resultInfo ="没有存储文件！";
					}
				}
			} else {
				resultInfo =  "0-删除失败";
			}
		} else {
			resultInfo = "文件不存在！";
		}
 
		return R.ok().put("resultInfo", resultInfo);
    	
    }

}
