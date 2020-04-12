package com.orhonit.modules.enterprise.rest;

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
import com.orhonit.modules.enterprise.entity.NopublicEnterpriseMsg;
import com.orhonit.modules.enterprise.service.NopublicEnterpriseMsgService;

import io.swagger.annotations.Api;

@RestController
@Api("企业发布或民宗局发布接口")
@RequestMapping("centerprisesend")
public class NopublicEnterpriseMsgController {

	@Autowired
	private NopublicEnterpriseMsgService nopublicEnterpriseMsgService;
	
	
	/**
	 * 通过用户id和类型查询企业或民宗局发布信息   支持分页,接收企业模糊查询
	 * @param nopublicEnterpriseMsg
	 * @return
	 */
	@RequestMapping(value="/msg",method=RequestMethod.GET)
	public R selectNopublicEnterpriseById(@RequestParam(value="id") Long id,@RequestParam(value="type") String type,
			@RequestParam(value="page",required=false,defaultValue="1")int page,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize) {
		Map<String, Object> results =null;
		if(id!=null) {
			try {
				results = nopublicEnterpriseMsgService.selectNopublicEnterpriseByUserId(id, type,page,pageSize);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok().put("data", results);
		}
		return R.parameterIsNul();
	}
	

	/**
	 * 通过id删除企业或民宗局发布信息
	 * @param nopublicEnterpriseMsg
	 * @return
	 */
	@RequestMapping(value="/msg/{id}",method=RequestMethod.DELETE)
	public R insertNopublicEnterprise(@PathVariable(value="id") Long id) {
		if(id!=null) {
			try {
				nopublicEnterpriseMsgService.deleteById(id);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}
	
	
	/**
	 * 企业或民宗局发布信息接口
	 * @param nopublicEnterpriseMsg
	 * @return
	 */
	@RequestMapping(value="/msg",method=RequestMethod.POST)
	public R insertNopublicEnterprise(@RequestBody NopublicEnterpriseMsg nopublicEnterpriseMsg) {
		if(nopublicEnterpriseMsg!=null) {
			if(null == nopublicEnterpriseMsg.getId()) {
				nopublicEnterpriseMsg.setIsRead("0");
				nopublicEnterpriseMsg.setCreateUser(ShiroUtils.getUserId());
				nopublicEnterpriseMsg.setCreateTime(DateUtils.getNowTime());
			}
			try {
				nopublicEnterpriseMsgService.insertOrUpdate(nopublicEnterpriseMsg);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}
	
}
