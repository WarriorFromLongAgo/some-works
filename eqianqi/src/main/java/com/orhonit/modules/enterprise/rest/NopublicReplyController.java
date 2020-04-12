package com.orhonit.modules.enterprise.rest;

import java.util.UUID;

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
import com.orhonit.config.ReplyPriperties;
import com.orhonit.modules.enterprise.entity.NopublicReply;
import com.orhonit.modules.enterprise.service.NopublicReplyService;

/**
 * 工商联审核
 * 
 * @author cyf
 * @date 2018/11/05 下午8:56:20
 */
@RestController
@RequestMapping(value = "nopublicreply")
public class NopublicReplyController {

	@Autowired
	private NopublicReplyService replyService;
	
	@Autowired
	private ReplyPriperties replyProperties;
	
	
	/**
	 * 通过发布记录主键查询批复信息
	 * 
	 * @param nopublicReply
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public R selectReply(@RequestParam(value="id") Long id) {
		if (null != id) {
			NopublicReply reply = replyService.selectNopublicReplyById(id);
			return R.ok().put("reply", reply);
		}
		return R.parameterIsNul();
	}
	

	/**
	 * 批复信息
	 * 
	 * @param nopublicReply
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public R insertReply(@RequestBody NopublicReply nopublicReply) {
		boolean flag =true;
		if (nopublicReply != null) {
			try {
				if (null == nopublicReply.getId()) {
					nopublicReply.setAuditUser(ShiroUtils.getUserId());
					nopublicReply.setAuditCreateTime(DateUtils.getNowTime());
					//NopublicReply reply = replyService.selectById(nopublicReply.getId());
					//flag = compareTime(reply.getAuditCreateTime().getTime()+replyProperties.getHour());
				}
				//1000 * 60 * 60*2  这是两小时   也就是批复后两小时允许修改
				if (flag) {
					replyService.insertOrUpdate(nopublicReply);
					return R.ok();
				}
			} catch (Exception e) {
				return R.error();
			}
			return R.timeOut();
		}
		return R.parameterIsNul();
	}

//	/**
//	 * 判断时间戳是否在一定结束时间范围内
//	 * @param timestamp 结束时间
//	 * @return
//	 */
//	private boolean compareTime(Long endtime) {
//		boolean bl = false;
//		Long result = (System.currentTimeMillis() - endtime) / (1000 * 60 * 60);
//		if (result <= 24) {
//			bl = true;
//		}
//		return bl;
//	}

}
