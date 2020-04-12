package com.orhonit.modules.enterprise.rest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.enterprise.entity.NopublicEnterprise;
import com.orhonit.modules.enterprise.service.NopublicEnterpriseService;

import io.swagger.annotations.Api;

/**
 * 非公企业
 * 
 * @author cyf
 * @date 2018/11/05 上午11:27:30
 */
@RestController
@RequestMapping("centerprise")
@Api("非公企业接口")
public class NopublicEnterpriseController {

	@Autowired
	private NopublicEnterpriseService nopublicEnterpriseService;

	/**
	 * 非公企业的插入或更新
	 * 
	 * @param nopublicEnterprise
	 * @return
	 */
	@RequestMapping(value = "/nopublic", method = RequestMethod.POST)
	public R insertOrUpdateNopublicEnterprise(@RequestBody NopublicEnterprise nopublicEnterprise) {
		if (nopublicEnterprise != null) {
			try {
				if (null == nopublicEnterprise.getId()) {
					nopublicEnterprise.setCreateUser(ShiroUtils.getUserId());
					nopublicEnterprise.setCreateTime(DateUtils.getNowTime());
				}
				nopublicEnterpriseService.insertOrUpdate(nopublicEnterprise);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}

	/**
	 * 非公企业的查询带分页
	 * 
	 * @param nopublicEnterprise
	 * @return
	 */
	@RequestMapping(value = "/nopublic", method = RequestMethod.GET)
	public R selectNopublicEnterpriseAll(@RequestParam(value = "page",required=false,defaultValue="1") Integer page,
			@RequestParam(value = "pageSize",required=false,defaultValue="10") Integer pageSize) {
		Page<NopublicEnterprise> list = null;
		try {
			Page<NopublicEnterprise> pageEntity = new Page<>(page, pageSize);
			list = nopublicEnterpriseService.selectPage(pageEntity);
		} catch (Exception e) {
			return R.error();
		}
		return R.ok().put("list", list);
	}



}
