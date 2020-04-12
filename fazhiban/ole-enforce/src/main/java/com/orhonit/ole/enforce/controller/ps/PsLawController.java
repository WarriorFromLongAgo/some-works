package com.orhonit.ole.enforce.controller.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.ps.PsLawDTO;
import com.orhonit.ole.enforce.service.law.LawService;

/**
 *公示系统
 *公示法律查询控制器
 * 1.
 * 2. 
 * 3. 
 * @author ebusu
 */
@RestController
@RequestMapping("/ps/law")
public class PsLawController {
	
	@Autowired
	private LawService lawService;
	
	/**
	 * ps按照法律类型分类统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/lawCount")
	public Result<List<PsLawDTO>> lawCount() {
		
		List<PsLawDTO> psLawDTO = this.lawService.lawCount();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psLawDTO);
	}
	/**
	 * ps按照法律类型查询法律名
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/lawByItemType")
	public Result<List<PsLawDTO>> lawByItemType(@RequestParam(value="itemType",required = false) String itemType) {
		Map<String, Object> params = new HashMap<>();
		params.put("itemType", itemType);
		List<PsLawDTO> psLawDTO = this.lawService.lawByItemType(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psLawDTO);
	}
	/**
	 * ps按照法律类型查询全部法律名
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/lawAllByItemType")
	public Result<List<PsLawDTO>> lawAllByItemType(@RequestParam(value="itemType",required = false) String itemType) {
		Map<String, Object> params = new HashMap<>();
		params.put("itemType", itemType);
		List<PsLawDTO> psLawDTO = this.lawService.lawAllByItemType(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psLawDTO);
	}
	/**
	 * ps按照权责类型查询法律
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/lawAllByProType")
	public Result<List<PsLawDTO>> lawAllByProType(@RequestParam(value="proType",required = false) String proType) {
		Map<String, Object> params = new HashMap<>();
		params.put("proType", proType);
		List<PsLawDTO> psLawDTO = this.lawService.lawAllByProType(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psLawDTO);
	}
}
