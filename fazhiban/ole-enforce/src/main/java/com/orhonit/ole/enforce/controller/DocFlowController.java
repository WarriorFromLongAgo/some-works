package com.orhonit.ole.enforce.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.dto.DocFlowListDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocFlowEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.service.docflow.DocFlowService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 文书显示控制器
 * @author
 *
 */
@RestController
@RequestMapping("/docFlow")
public class DocFlowController {

	@Autowired
	private DocFlowService docFlowService;
	
	/**
	 * 新增文书配置
	 * @param docFlowDTO
	 */
	@PostMapping("/save")
	public void saveDocFlow(@Valid DocFlowEntity docFlowEntity) {
		//DocTemplateEntity docTemplateEntity = new DocTemplateEntity();
		//BeanUtils.copyProperties(docTemplateDTO, docTemplateEntity);
		docFlowService.saveDocFlow(docFlowEntity);
	}
	
	/**
	 * 修改文书配置
	 * @param docFlowEntity
	 */
	@PostMapping("/update")
	public void updateDocTemplate(@Valid DocFlowEntity docFlowDTO) {
		if ( docFlowDTO == null || StringUtils.isEmpty(docFlowDTO.getId())) {
			throw new EnforceException(ResultCode.FIELD_ERROR);
		}
		DocFlowEntity docFlowEntity = this.docFlowService.findOne(docFlowDTO.getId());
		docFlowEntity.setCode(docFlowDTO.getCode());
		docFlowEntity.setFlowType(docFlowDTO.getFlowType());
		docFlowEntity.setFlowNode(docFlowDTO.getFlowNode());
		docFlowEntity.setDealType(docFlowDTO.getDealType());
		docFlowEntity.setNeedAdd(docFlowDTO.getNeedAdd());
		docFlowEntity.setIsEffect(docFlowDTO.getIsEffect());
		User user = UserUtil.getCurrentUser();
		docFlowEntity.setUpdateBy(user.getUsername());
		docFlowEntity.setUpdateDate(new Date());
		docFlowEntity.setUpdateName(user.getNickname());
		docFlowService.updateDocFlow(docFlowEntity);
	}
	
	/**
	 * 删除文书配置
	 * @param request
	 * @return
	 */
	@GetMapping("/delDocFlow/{id}")
	public void delDocFlow(@PathVariable String id) {
		docFlowService.delDocFlow(id);
	}
	
	/**
	 * 获取流程节点文书
	 * @param flowNode
	 * @return
	 */
	@GetMapping("/docFlows")
	@ResponseBody
	public Result<Object> getDocFlows(
			@RequestParam(value = "flowNode" , required = false) String flowNode, 
			@RequestParam(value = "flowType", required = false) String flowType) {
		Map<String, Object> params = new HashMap<>();
		params.put("flowNode", flowNode);
		params.put("flowType", flowType);
		List<DocFlowDTO> docFlowDTOs = docFlowService.getDocFlows(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, docFlowDTOs);
	}
	
	
	/**
	 * 获取文书配置列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<DocFlowListDTO> listDocFlow(TableRequest request) {
		
		return TableRequestHandler.<DocFlowListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return docFlowService.getDocFlowCount(request.getParams());
			}
		}).listHandler(new ListHandler<DocFlowListDTO>() {

			@Override
			public List<DocFlowListDTO> list(TableRequest request) {

				List<DocFlowListDTO> list = docFlowService.getDocFlowList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 获取模板code,name列表
	 * @param request
	 * @return
	 */
	@GetMapping("/listTemplate")
	@ResponseBody
	public List<Map<String,String>> getListTemplate() {
		return docFlowService.getListTemplate();
	}
	
	/**
	 * 回显文书配置
	 * @param request
	 * @return
	 */
	@GetMapping("/getDocFlow/{id}")
	public DocFlowEntity getDocFlow(@PathVariable String id) {
		return docFlowService.findOne(id);
	}
	
	/**
	 * 获取文书是否已经填写
	 * @return
	 */
	@GetMapping("/needAdd")
	@ResponseBody
	public Result<Object> getNeedAdd(@RequestParam String templateId, @RequestParam String caseId) {
		// 获取文书内容
		List<DocContentEntity> docContentEntity = this.docFlowService.getNeedAdd(templateId, caseId);
		if(StringUtils.isEmpty(docContentEntity) || docContentEntity.size()<=0){
			return ResultUtil.toResponse(ResultCode.ERROR);
		}
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
}
