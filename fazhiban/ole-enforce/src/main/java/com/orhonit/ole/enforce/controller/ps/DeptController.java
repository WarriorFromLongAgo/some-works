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
import com.orhonit.ole.enforce.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.enforce.dto.ps.PsDeptListDTO;
import com.orhonit.ole.enforce.service.dept.DeptService;
 
/**
 * 公示系统
 * 主体相关控制器
 * 1. 主体列表查询
 * 2. 
 * 3. 
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/ps/dept")
public class DeptController {
	
	@Autowired
	private DeptService deptService;

	/**
	 * 根据区域ID和主体名称查询主体列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/deptList")
	public Result<List<PsDeptListDTO>> getDeptList(@RequestParam(value="areaId",required = false) String areaId, 
			@RequestParam(value="name", required = false) String name,
			@RequestParam(value="deptProperty", required = false) String deptProperty ) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		params.put("name", name);
		params.put("deptProperty", deptProperty);
		List<PsDeptListDTO> deptListDTO = this.deptService.getDeptList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, deptListDTO);
	}
	/**
	 * 各辖区主体数量统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/areadeptList")
	public Result<List<PsAreaDeptDTO>> getDeptList() {
		
		List<PsAreaDeptDTO> psAreaDeptDTO = this.deptService.areaDeptList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psAreaDeptDTO);
	}
	
	/**
	 * 各辖区主体数量和性质统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/areadeptProList")
	public Result<List<PsAreaDeptDTO>> areaDeptProList(@RequestParam(value="areaId",required = false) String areaId) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		
		List<PsAreaDeptDTO> psAreaDeptDTO = this.deptService.areaDeptProList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psAreaDeptDTO);
	}
	
	/**
	 * 根据区域ID和主体名称查询主体列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/deptAllList")
	public Result<List<PsAreaDeptDTO>> getDeptAllList(@RequestParam(value="areaId",required = false) String areaId, 
			@RequestParam(value="deptId", required = false) String deptId ) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		params.put("deptId", deptId);
		
		List<PsAreaDeptDTO> psAreaDeptDTO = this.deptService.getDeptAllList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psAreaDeptDTO);
	}
	
	/**
	 * 根据主体Id查询本主体及下级主体列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/getDeptListByDeptId")
	public Result<List<PsDeptListDTO>> getDeptListByDeptId(@RequestParam(value="deptId", required = true) String deptId ) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		
		List<PsDeptListDTO> psDeptListDTO = this.deptService.getDeptListByDeptId(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psDeptListDTO);
	}
	
	/**
	 * 执法主体下执法人员、法律、权责、委托部门、案件信息统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/getCountByDeptId")
	public Result<PsDeptListDTO> getCountByDeptId(@RequestParam(value="deptId", required = false) String deptId ) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		
		PsDeptListDTO psDeptListDTO = this.deptService.getCountByDeptId(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psDeptListDTO);
	}
	
	/**
	 * 根据区域Id和区域类型查询主体
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/selDeptByDeptId")
	public Result<List<PsDeptListDTO>> selDeptByDeptId(@RequestParam(value="areaId", required = true) String areaId,
											@RequestParam(value="deptProperty", required = false) String deptProperty ) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		params.put("deptProperty", deptProperty);
		List<PsDeptListDTO> psDeptListDTO = this.deptService.selDeptByDeptId(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psDeptListDTO);
	}
	
}
