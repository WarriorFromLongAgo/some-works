package com.orhonit.ole.enforce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.enforce.dao.FilingDao;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.RightDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 立案控制器
 * 
 * @author wuyz
 *
 */
@RestController
@RequestMapping("/filing")
public class FilingController {
	@Autowired
	private CaseService caseService;
	@Autowired
	private FilingDao filingdao;
	/**
	 * 获取案件列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/list")
	public TableResponse<CaseListDTO> listCase(TableRequest request) {

		request.getParams().put("typeValue", CommonParameters.DictType.CASE_RES);
		request.getParams().put("caseStatus", CommonParameters.CaseStatus.AJLA);
		User user = UserUtil.getCurrentUser();
		request.getParams().put("deptId", user.getDept_id());
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return caseService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override	
			public List<CaseListDTO> list(TableRequest request) {

				List<CaseListDTO> list = caseService.getCaseList(request.getParams(), request.getStart(),
						request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/right/{dept_id}")
	@ApiOperation(value = "权责")
	public List<Map<String, Object>> rightAll(@PathVariable String dept_id) {
		List<RightDTO> rightAll = filingdao.rightAll(dept_id);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(RightDTO per:rightAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("code",per.getCode());
			map.put("text", per.getName());
			listMap.add(map);
		}
		//List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, null, "id","text");
		
		return listMap;
	}
	
	@GetMapping("/zfyj/{id}")
	@ApiOperation(value = "执法依据")
	public List<Map<String, Object>> zfyj(@PathVariable String id) {
		List<RightDTO> zfyjAll = filingdao.zfyjList(id);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(RightDTO per:zfyjAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("code",per.getWz_content());
			String[] item = per.getWz_item().split("-");
			String[] itemStr = {"篇","章","节","条","款","项","目"};
			String itemOut = "";
			for(int i=0;i<item.length;i++) {
				if(!item[i].equals("0")) {
					itemOut += "第"+item[i]+itemStr[i];
				}
			}
			itemOut += ":";
			map.put("text", per.getWz_catalog_name() + itemOut + per.getWz_content());
			listMap.add(map);
		}
		return listMap;
	}
	@GetMapping("/cfyj/{id}")
	@ApiOperation(value = "处罚依据")
	public List<Map<String, Object>> cfyj(@PathVariable String id) {
		List<RightDTO> zfyjAll = filingdao.cfyjList(id);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(RightDTO per:zfyjAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("code",per.getFz_content());
			String[] item = per.getFz_item().split("-");
			String[] itemStr = {"篇","章","节","条","款","项","目"};
			String itemOut = "";
			for(int i=0;i<item.length;i++) {
				if(!item[i].equals("0")) {
					itemOut += "第"+item[i]+itemStr[i];
				}
			}
			itemOut += ":";
			map.put("text",per.getFz_catalog_name() + itemOut + per.getFz_content());
			map.put("right_duty_id",per.getRight_duty_id());
			listMap.add(map);
		}
		return listMap;
	}
	
}
