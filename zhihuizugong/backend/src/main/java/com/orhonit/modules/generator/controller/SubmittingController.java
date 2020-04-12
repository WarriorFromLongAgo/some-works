package com.orhonit.modules.generator.controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.entity.SubmittingEntity;
import com.orhonit.modules.generator.service.SubmittingService;
import com.orhonit.modules.generator.service.SupervisoryService;
import com.orhonit.modules.generator.vo.SubmittingVO;
import com.orhonit.modules.sys.controller.AbstractController;
import com.orhonit.modules.sys.entity.UserOrgEntity;
import com.orhonit.modules.sys.service.UserOrgService;


/**
 * 
 * Title: SubmittingController.java
 * @Description:  自查自纠 报送单位
 * @author YaoSC 
 * @date 2019年7月20日
 */
@RestController
@RequestMapping("subtting")
public class SubmittingController extends AbstractController{
	
	@Autowired
	SubmittingService  service;
	@Autowired
	SupervisoryService supervisoryService;
	@Autowired
	UserOrgService userOrgService;
	//报送单位
	static final Integer BAOSONG_DANWEI=1;
	
	/**
	 * 删除
	 * @param shareId
	 * @return
	 */
	@DeleteMapping("/delete")
	public R delete( Integer id ) {
		if(id>0) {
			service.updateSubitAndVisory(id);
			return R.ok().put("delete", "删除成功");
		}
		return R.parameterIsNul();
	}
	
	/**
	 * 详情
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id) throws IOException {
		SubmittingVO entity =service.getOneSubmit(id);
		Map<String,Object>map = new HashMap<String,Object>(16);
		map.put("Submitting", entity);
		map.put("Supervisory",service.map(id));
		return R.ok().put("map", map);
	}
	
	/**
     * 保存
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public R save(@RequestBody SubmittingEntity t){
    	t.setUserId(getUserId().intValue());
    	t.setCreateTime(new Date());
    	t.setUpdateTime(new Date());
    	t.setIsDel(CommonParameters.isDel.IS_DEL_NO);
		service.insert(t);

        return R.ok();
    }
    
    /**
         * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public R update(@RequestBody SubmittingEntity t){
    	t.setUpdateTime(new Date());
		service.updateById(t);

        return R.ok();
    }
    
    /**
         * 报送单位   列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return R.ok().put("page", service.submitListPage(params));
    }
    
    /**
         * 所有报送单位
     * @return
     */
    @GetMapping("/getOrgList")
    public R getOrgList() {
    	List<UserOrgEntity>list=userOrgService.selectList(new EntityWrapper<UserOrgEntity>().eq("org_is_b", SubmittingController.BAOSONG_DANWEI));
    	return R.ok().put("list", list);
    }
    
    
}
