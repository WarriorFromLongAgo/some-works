package com.orhonit.ole.enforce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;
import com.orhonit.ole.enforce.service.checktype.CheckTypeService;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 *检查类型制器
 *1.查询检查类型列表
 *2.添加检查类型
 *3.修改检查类型
 *4.删除检查类型
 *5.根据ID查询检查类型信息
 * @author zhangjy
 *
 */

@RestController
@RequestMapping("/checkType")
public class CheckTypeController {
	
	@Autowired
	private CheckTypeService checkTypeService;
	
	/**
	 * 1.查询检查列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CheckTypeEntity> listCase(TableRequest request) {
		
		request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
		
		return TableRequestHandler.<CheckTypeEntity> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return checkTypeService.getCheckTypeCount(request.getParams());
			}
		}).listHandler(new ListHandler<CheckTypeEntity>() {

			@Override
			public List<CheckTypeEntity> list(TableRequest request) {
				List<CheckTypeEntity> list = checkTypeService.getCheckTypeList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 2.添加检查列表
	 * */
	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存检查类型")
	public void save(@RequestBody CheckTypeEntity checkTypeEntity){
		Long createBy = UserUtil.getCurrentUser().getId();
		String createName = UserUtil.getCurrentUser().getNickname();
		checkTypeEntity.setDelFlag("0");
		checkTypeEntity.setCreateBy(createBy.toString());
		checkTypeEntity.setCreateName(createName);
		checkTypeEntity.setCreateDate(new Date());
		checkTypeService.save(checkTypeEntity);
	}
	
	/**
	 * 3.修改检查列表
	 * */
	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改检查类型")
	public void update(@RequestBody CheckTypeEntity checkTypeEntity){
		Long updateBy = UserUtil.getCurrentUser().getId();
		String updateName = UserUtil.getCurrentUser().getNickname();
		checkTypeEntity.setUpdateBy(updateBy.toString());
		checkTypeEntity.setUpdateName(updateName);
		checkTypeEntity.setUpdateDate(new Date());
		checkTypeService.update(checkTypeEntity);
	}
	
	/**
	 * 4.删除检查列表
	 * */
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除检查类型")
	public void delete(@PathVariable Long id){
		Long updateBy = UserUtil.getCurrentUser().getId();
		String updateName = UserUtil.getCurrentUser().getNickname();
		CheckTypeEntity checkTypeEntity = new CheckTypeEntity();
		checkTypeEntity.setId(Integer.valueOf(Long.toString(id)));
		checkTypeEntity.setUpdateBy(updateBy.toString());
		checkTypeEntity.setUpdateName(updateName);
		checkTypeEntity.setUpdateDate(new Date());
		checkTypeService.delete(checkTypeEntity);
	}
	
	/**
	 * 4.根据ID查询检查类型信息
	 * */
	@GetMapping("/{id}")
	public CheckTypeEntity finCheckTypeById(@PathVariable Long id){
		int num = Integer.valueOf(Long.toString(id));
		CheckTypeEntity checkTypeEntity = checkTypeService.finCheckTypeById(num);
		return checkTypeEntity;
		
	}
	/**
	 * 根据部门ID查找title
	 * @param deptID 部门ID
	 * @return
	 */
	@GetMapping("/deptId/{deptId}")
	public List<Map<String, Object>> getCheckTypeByDeotIdEntity( @PathVariable String deptId ){
		
		List<CheckTypeEntity> checkTypeByDeptId = this.checkTypeService.checkTypeByDeotId(deptId);
		
		List<Map<String,Object>> list = new ArrayList<>();	
		
		Map<String,Object> map;
		for(CheckTypeEntity per:checkTypeByDeptId){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId","");
			map.put("text", per.getTitle());
			list.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(list, "parentId", "id",null);
			
		return retMap;
	}
}
