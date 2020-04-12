package com.orhonit.modules.generator.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.SubmittingEntity;
import com.orhonit.modules.generator.service.JointCompanyService;
import com.orhonit.modules.generator.service.SubmittingService;
import com.orhonit.modules.generator.service.SupervisoryService;
import com.orhonit.modules.generator.vo.JointCompanyVO;
import com.orhonit.modules.generator.vo.SubmittingVO;

@RestController
@RequestMapping("app/subso")
public class AppSubSoController {


	 @Autowired
	 JointCompanyService jointCompanyService;
	 @Autowired
	 SubmittingService submittingService;
	 @Autowired
	 SupervisoryService supervisoryService;
	 
	 /**
	  * 列表
	  * @param params
	  * @return
	  */
	 @GetMapping("/list")
	 public R list(@RequestParam Map<String, Object> params) {
		 PageUtils page = jointCompanyService.jointqueryPage(params);
		  return R.ok().put("page", page);
	 }
	 
	 /**
	  * 联席单位  
	  * 详细
	  * @param id
	  * @return
	  */
	 @RequestMapping("/lxinfo/{id}")
     public R lxinfo(@PathVariable("id") Integer id) {
		JointCompanyVO jointCompanyEntity = jointCompanyService.getOneJoint(id);

        return R.ok().put("JointCompanyEntity", jointCompanyEntity);
	 }
	 
	 /**
	  * 自查自纠
         * 报送单位   列表
     */
    @RequestMapping("/submitList")
    public R list1(@RequestParam Map<String, Object> params){

        return R.ok().put("page", submittingService.submitListPage(params));
    }
	
    
	/**
	 * 自查自纠 
	 * 详情
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id) throws IOException {
		SubmittingVO entity =submittingService.getOneSubmit(id);
		Map<String,Object>map1 = new HashMap<String,Object>();
		map1.put("SubmittingEntity", entity);
		map1.put("geren",submittingService.map(entity.getOrgId()));
		return R.ok().put("map", map1);
	}
	
	
	/**
	 * 监督查询
	 * @param type
	 * @param userId
	 * @return
	 */
	@GetMapping("/query/comprehensive")
	public R comprehesive(String type,Integer page,Integer limit,Integer userId) {
		
		return R.ok().put("jiandu", supervisoryService.getAllList(type,page,limit,userId));
		
	}
	
}
