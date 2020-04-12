package com.orhonit.ole.enforce.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.FlowDealDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 结案控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/closed")
@Slf4j
public class ClosedController {
	
	@Autowired
	private CaseService caseService;
	
	
	@Value("${files.punishBill}")
	private String filesPath;
	
	@Value("${upload.serverUrl}")
	private String serverUrl;
	
	/**
	 * 案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseListDTO> listCase(TableRequest request) {
		request.getParams().put("typeValue",CommonParameters.DictType.CASE_RES);
		request.getParams().put("caseStatus", CommonParameters.CaseStatus.ANJA);
		User user = UserUtil.getCurrentUser();
		request.getParams().put("zzfryId", user.getPerson_id());
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {
			@Override 
			public int count(TableRequest request) {
				System.out.println(request.getParams().toString());
				return caseService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				List<CaseListDTO> list = caseService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/*
	 * 结案
	 */
	@PostMapping(value="/update")
	public Result<String> caseClosed(@RequestBody FlowDealDTO flowDealDTO) {
		this.caseService.caseClosed(flowDealDTO);
		return ResultUtil.success();
	}
	
	/**
	 * 保存缴费凭证
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@LogAnnotation
	@PostMapping("/punishImg")
	@ApiOperation(value = "文件上传")
	public Map<String, String> uploadFile(MultipartFile file) throws IOException {
		Map<String,String> res = new HashMap<String,String>();
		String fileOrigName = file.getOriginalFilename();
		fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
		String md5 = FileUtil.fileMd5(file.getInputStream());
		String pathname = "/"+md5+System.currentTimeMillis()+fileOrigName;
		res.put("punishBill", pathname);
		String fullPath = filesPath + pathname;
		res.put("url", serverUrl+"file/"+filesPath.split("/")[filesPath.split("/").length-1]+pathname);
		FileUtil.saveFile(file, fullPath);
		return res;
	}
	
}