package com.orhonit.modules.religion.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.religion.entity.NationCode;
import com.orhonit.modules.religion.service.NationCodeService;

@RestController
@RequestMapping("nation")
public class NationCodeController {
	
	@Autowired
	private NationCodeService codeService;

	/**
	 * 查询所有民族代码
	 * @return
	 */
	@RequestMapping(value="/code",method=RequestMethod.GET)
	public R selectNationAll() {
		List<NationCode> list = codeService.selectNationCodeAll();
		return R.ok().put("results", list);
	}
	
}
