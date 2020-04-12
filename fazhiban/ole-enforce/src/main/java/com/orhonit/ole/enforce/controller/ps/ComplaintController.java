package com.orhonit.ole.enforce.controller.ps;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.entity.WarnComplainEntity;
import com.orhonit.ole.enforce.service.complain.ComplainService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 公示系统
 * 投诉相关功能控制器
 * @author wuyz
 *
 */
@RestController
@RequestMapping("/ps/complaint")
public class ComplaintController {
	
	@Autowired
	private ComplainService complainService;
	
	/**
	 * ps 公示投诉提交
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/tousu")
	public Result<Object> login(
			@RequestParam(value="name",required = false) String name,
			@RequestParam(value="content", required = false) String content,
			@RequestParam(value="email", required = false) String email,
			@RequestParam(value="address", required = false) String address,
			@RequestParam(value="call", required = false) String tel,
			@RequestParam(value="lang", required = false) String lang) {
		
		User user = UserUtil.getCurrentUser();
		WarnComplainEntity warnComplainEntity = new WarnComplainEntity();
		warnComplainEntity.setAddress(address);
		warnComplainEntity.setContent(content);
		warnComplainEntity.setEmail(email);
		warnComplainEntity.setName(name);
		warnComplainEntity.setTel(tel);
		warnComplainEntity.setCreateDate(new Date());
		warnComplainEntity.setLang(lang);
		if(null != user){
			warnComplainEntity.setCreateBy(user.getUsername());
			warnComplainEntity.setCreateName(user.getNickname());
		}
		if(complainService.save(warnComplainEntity)){
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,"投诉提交成功");
		}else{
			return ResultUtil.toResponseWithData(ResultCode.ERROR,"提交投诉信息失败");
		}
		
	}

}
