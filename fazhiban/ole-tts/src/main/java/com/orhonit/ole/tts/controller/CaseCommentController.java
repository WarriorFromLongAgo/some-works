package com.orhonit.ole.tts.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.entity.CaseCommentEntity;
import com.orhonit.ole.tts.service.caseComment.CaseCommentService;

import io.swagger.annotations.ApiOperation;

/**
 *案卷评查制器
 *1.案卷评查列表
 *2.添加检查类型
 * @author zhangjy
 *
 */

@RestController
@RequestMapping("/caseComment")
public class CaseCommentController {
	
	@Autowired
	private CaseCommentService caseCommentService;
	/**
	 * 1.查询检查列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public Result<Object> list(@RequestParam(value = "caseId" , required = false) String caseId ){
		List<CaseCommentEntity> caseComment = this.caseCommentService.getListByCaseId(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,caseComment);
	}
	
	/**
	 * 2.添加检查列表
	 * */
	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存检查类型")
	public void save(@RequestBody CaseCommentEntity caseCommentEntity){
		User user = UserUtil.getCurrentUser();
		String createName = user.getNickname();
		String createBy = user.getId().toString();
		caseCommentEntity.setCreateBy(createBy);
		caseCommentEntity.setCreateName(createName);
		caseCommentEntity.setCreateDate(new Date());
		caseCommentService.save(caseCommentEntity);
	}
}
