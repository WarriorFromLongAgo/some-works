package com.orhonit.ole.enforce.controller.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.dto.ps.AreaAndPotAndProPotDTO;
import com.orhonit.ole.enforce.dto.ps.AreaAndPotDTO;
import com.orhonit.ole.enforce.dto.ps.PotenceListDTO;
import com.orhonit.ole.enforce.dto.ps.RigAndPotListDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;


/**
 * 公示系统
 * 权责相关控制器
 * 1. 权责查询
 * 2. 权责列表查询
 * 3. 各个地区权责清单统计 
 * 4.一个地区下的一个部门或全部部门的权责分类统计
 * 5.权责清单情况
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/ps/potence")
public class PotenceController {
	
	@Autowired
	private CaseService caseService;

	@GetMapping("/find/{caseId}")
	public Result<CaseInfoDTO> getCaseInfoByCaseId(@PathVariable String caseId) {
		caseId = "80cd75c9-32b5-4921-a21a-e5e00b7e8272";
		CaseInfoDTO caseInfoDTO = this.caseService.findOne(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseInfoDTO);
	}
	/**
	 * ps 
	 * 权责列表查询
	 */
	@GetMapping("/getPotenceListDTO")
	public Result<List<PotenceListDTO>> getPotenceListDTO(
			@RequestParam(value="areaId",defaultValue="",required=false) String areaId,
			@RequestParam(value="deptId",defaultValue="",required=false) String deptId,
			@RequestParam(value="proType",defaultValue="",required=false) String proType,
			@RequestParam(value="deptProperty",defaultValue="",required=false) String deptProperty
			){
		Map<String, Object> plMap=new HashMap<>();
		plMap.put("areaId", areaId);
		plMap.put("deptId", deptId);
		plMap.put("proType", proType);
		plMap.put("deptProperty", deptProperty);
		
		List<PotenceListDTO> selectPotenceList = this.caseService.selectPotenceList(plMap);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, selectPotenceList);
		
	}
	/**
	 * ps 
	 * 权责列表查询
	 */
	@GetMapping("/selectPotenceListBydeptId")
	public Result<Map<String,Object>> selectPotenceListBydeptId(
			@RequestParam(value="deptId",defaultValue="",required=false) String deptId,
			@RequestParam(value="proType",defaultValue="",required=false) String proType,
			@RequestParam(value="title",defaultValue="",required=false) String title,
			@RequestParam(value="start",defaultValue="",required=false) int start,
			@RequestParam(value="length",defaultValue="",required=false) int length
			){
		start -= 1;
		start *= length;

		Map<String, Object> plMap=new HashMap<>();
		plMap.put("deptId", deptId);
		plMap.put("proType", proType);
		plMap.put("title", title);
		plMap.put("start", start);
		plMap.put("length", length);
		List<PotenceListDTO> selectPotenceList = this.caseService.selectPotenceListBydeptId(plMap);

		Map<String,Object> res = new HashMap<>();
		res.put("data",selectPotenceList);
		int count = this.caseService.selectPotenceListBydeptIdCount(plMap);
		res.put("count",count);
		res.put("pages",count*1.0/length == count/length?count/length:count/length+1);
		res.put("page",start/length + 1);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, res);
		
	}
	/**
	 * ps
	 * 各个地区权责清单统计（地区）
	 * @return
	 */
	@GetMapping("/getAreaAndPot")
	public Result<AreaAndPotDTO> getAreaAndPot(){
		List<AreaAndPotDTO> AreaAndPot=this.caseService.AreaAndPotSelect();
		return (Result) ResultUtil.toResponseWithData(ResultCode.SUCCESS, AreaAndPot);
		
	}
	/**
	 * ps
	 * 一个地区下的一个部门或全部部门的权责分类统计
	 * @param areaId 区域ID
	 * @param deptId 部门ID
	 * @return
	 */
	@GetMapping("/getAreaAndPotAndProPot")
	public Result<List<AreaAndPotAndProPotDTO>> getAreaAndPotAndProPot(
			@RequestParam(value="areaId",defaultValue="",required=false) String areaId,
			@RequestParam(value="deptId",defaultValue="",required=false) String deptId
			){
		Map<String, Object> AreaAndPotAndProPotMap=new HashMap<>();
		AreaAndPotAndProPotMap.put("areaId", areaId);
		AreaAndPotAndProPotMap.put("deptId", deptId);
		List<AreaAndPotAndProPotDTO> AreaAndPotAndProPotList=this.caseService.getAreaAndPotAndProPot(AreaAndPotAndProPotMap);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, AreaAndPotAndProPotList);
		
	}
	/**
	 * ps
	 * 权责清单（执法类型）（条形图）
	 * @return
	 */
	@GetMapping("/getRigAndPotList")
	public Result<List<RigAndPotListDTO>> getRigAndPotList(){
		
		List<RigAndPotListDTO>RigAndPotListt=this.caseService.RigAndPotList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, RigAndPotListt);
		
	}
	
	/**
	 * ps
	 * 权责清单（执法类型）（条形图）
	 * @return
	 */
	@GetMapping("/potenceDetail")
	public Result<AreaAndPotDTO> potenceDetail(@RequestParam(value="potenceId",defaultValue="",required=false) String potenceId){
		Map<String, Object> map=new HashMap<>();
		map.put("potenceId", potenceId);
		AreaAndPotDTO areaAndPotDTO=this.caseService.potenceDetail(map);
		String wzItemStr = "";
		if(areaAndPotDTO!=null){
			String[] itemStr = {"篇","章","节","条","款","项","目"};
			    if( StrUtil.isNotEmpty(areaAndPotDTO.getWzItem())){
			    	String[] wzitem = areaAndPotDTO.getWzItem().split("-");
					for(int i=0;i<wzitem.length;i++) {
						if(!wzitem[i].equals("0")) {
							wzItemStr += "第"+wzitem[i]+itemStr[i];
						}
					}
			    }
			areaAndPotDTO.setLawName(areaAndPotDTO.getLawName()+wzItemStr);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, areaAndPotDTO);
		
	}
}
