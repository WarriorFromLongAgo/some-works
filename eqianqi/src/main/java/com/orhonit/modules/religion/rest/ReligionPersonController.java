package com.orhonit.modules.religion.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.religion.entity.ReligionPerson;
import com.orhonit.modules.religion.service.ReligionPersonService;

/**
 * 教职人员信息
 * 
 * @author cyf
 * @date 2018/11/13 上午9:18:54
 */
@RestController
@RequestMapping("professoremeritus")
public class ReligionPersonController {

	@Autowired
	private ReligionPersonService personService;
	

	/**
	 * 删除教职人员信息维护
	 * 
	 * @param religionPerson
	 * @return
	 */
	@RequestMapping(value = "/religion", method = RequestMethod.DELETE)
	public R deleteReligionPerson(@RequestParam(value="id") Long id) {
		if (null != id) {
			try {
				personService.deleteById(id);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}

	/**
	 * 查询所有人员信息 支持idcard模糊搜索,以及分页
	 * 
	 * @param religionPerson
	 * @return
	 */
	@RequestMapping(value = "/religion", method = RequestMethod.GET)
	public R selectReligionPersonAll(@RequestParam(value = "idCard", required = false) String idCard,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCard", idCard);
		params.put("page", page);
		params.put("pageSize", pageSize);
		params = personService.selectReligionPersonAll(params);
		return R.ok().put("results", params);
	}

	/**
	 * 插入或更新教职人员信息维护
	 * 
	 * @param religionPerson
	 * @return
	 */
	@RequestMapping(value = "/religion", method = RequestMethod.POST)
	public R insertReligionPerson(@RequestBody ReligionPerson religionPerson) {
		if (null != religionPerson) {
			if (null == religionPerson.getId()) {
				religionPerson.setCreateUser(ShiroUtils.getUserId());
				religionPerson.setCreateTime(DateUtils.getNowTime());
			}
			try {
				personService.insertOrUpdate(religionPerson);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}

}
