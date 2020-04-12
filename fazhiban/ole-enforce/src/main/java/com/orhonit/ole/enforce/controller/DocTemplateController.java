package com.orhonit.ole.enforce.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.enforce.dto.DocTemplateDTO;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 模板控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/docTemplate")
public class DocTemplateController {
	
	@Autowired
	private DocTemplateService docTemplateService;
	
	/**
	 * 新增模板
	 * @param docTemplateDTO
	 */
	@PostMapping("/save")
	public void saveDocTemplate(@Valid DocTemplateDTO docTemplateDTO) {
		DocTemplateEntity docTemplateEntity = new DocTemplateEntity();
		BeanUtils.copyProperties(docTemplateDTO, docTemplateEntity);
		docTemplateService.saveDocTemplate(docTemplateEntity);
	}
	
	/**
	 * 修改模板
	 * @param docTemplateDTO
	 */
	@PostMapping("/update")
	public void updateDocTemplate(@Valid DocTemplateDTO docTemplateDTO) {
		if ( docTemplateDTO == null || StringUtils.isEmpty(docTemplateDTO.getId())) {
			throw new EnforceException(ResultCode.FIELD_ERROR);
		}
		DocTemplateEntity docTemplateEntity = this.docTemplateService.findOne(docTemplateDTO.getId());
		docTemplateEntity.setContent(docTemplateDTO.getContent());
		docTemplateEntity.setName(docTemplateDTO.getName());
		docTemplateEntity.setCode(docTemplateDTO.getCode());
		User user = UserUtil.getCurrentUser();
		docTemplateEntity.setUpdateBy(user.getUsername());
		docTemplateEntity.setUpdateDate(new Date());
		docTemplateEntity.setUpdateName(user.getUsername());
		docTemplateService.updateDocTemplate(docTemplateEntity);
	}
	
	/**
	 * 获取模板内容
	 * @param id
	 * @return
	 */
	@GetMapping("/getContent/{id}")
	@ResponseBody
	public DocTemplateEntity getContent(@PathVariable String id) {
		DocTemplateEntity docTemplateEntity = this.docTemplateService.findOne(id);
		return docTemplateEntity;
	}
	
	/**
	 * 获取模板列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<DocTemplateDTO> listDocTemplate(TableRequest request) {
		
		Page<DocTemplateEntity> page = this.docTemplateService.getDocTemplateList(request.getParams(), request.getStart(), request.getLength());
		
		return TableRequestHandler.<DocTemplateDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return Long.valueOf(page.getTotalElements()).intValue();
			}
		}).listHandler(new ListHandler<DocTemplateDTO>() {

			@Override
			public List<DocTemplateDTO> list(TableRequest request) {
				return page.getContent().stream().map(
						e -> {
							DocTemplateDTO docTemplateDTO = new DocTemplateDTO();
							BeanUtils.copyProperties(e, docTemplateDTO);
							return docTemplateDTO;
						}
				).collect(Collectors.toList());
			}
		}).build().handle(request);
	}
	
	
	/**
	 * 获取模版编号列表
	 * @param request
	 * @return 
	 * @return
	 */
	@GetMapping(value="/getTemplateCode")
	public Map<String, String> getTemplateCode() {
		return this.docTemplateService.getTemplateCode();
	}
}
