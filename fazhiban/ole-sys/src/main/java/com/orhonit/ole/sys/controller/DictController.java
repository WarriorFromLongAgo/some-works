package com.orhonit.ole.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.sys.config.DictCacheManager;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.dto.SysDictDTO;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.service.SysDictService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典表相关接口
 * @author liuzhih
 */
@Slf4j(topic = "dictManage")
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private SysDictService sysDictService;
	
	@Autowired
	private DictCacheManager dictCacheManager;
	

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存字典")
	public void saveSysDict(@RequestBody SysDictEntity sysDict) {
		sysDictService.saveSysDict(sysDict);
		 
		List<SysDictEntity> sysDictEntities = this.dictCacheManager.getDictTypeByValue(sysDict.getTypeValue());
			
		if ( sysDictEntities != null ) {
			
			this.dictCacheManager.deleteDictByTypeValue(sysDict.getTypeValue());
		}
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改字典")
	public void updateSysDict(@RequestBody SysDictEntity sysDict) {
		sysDictService.updateSysDict(sysDict);
		List<SysDictEntity> sysDictEntities = this.dictCacheManager.getDictTypeByValue(sysDict.getTypeValue());
			
		if ( sysDictEntities != null ) {
			
			this.dictCacheManager.deleteDictByTypeValue(sysDict.getTypeValue());
		}
	}

	@GetMapping(value="/list")
	@ApiOperation(value = "字典列表")
	public TableResponse<SysDictEntity> listDicts(TableRequest request) {
		return TableRequestHandler.<SysDictEntity> builder().countHandler(new CountHandler() {
			@Override
			public int count(TableRequest request) {
				
				/*Integer count = dictCacheManager.getSysDictCount("count");
				
				if ( count == null ) {
					count = sysDictService.getSysDictCount(request.getParams());
					
					dictCacheManager.saveDictCount(count, "count");
				}*/
				
				
				
				return sysDictService.getSysDictCount(request.getParams());
			}
		}).listHandler(new ListHandler<SysDictEntity>() {
			@Override
			public List<SysDictEntity> list(TableRequest request) {
				
				return sysDictService.getSysDictList(request.getParams(), request.getStart(), request.getLength());
				
				/*List<SysDictEntity> list = dictCacheManager.getSysDictList("list");
				
				if ( list == null ) {
					list = sysDictService.getSysDictList(request.getParams(), request.getStart(), request.getLength());
					
					List<SysDictEntity> cacheList = sysDictService.getSysDictList(request.getParams(), null, null);
					
					dictCacheManager.saveDictList(cacheList, "list");
				} else if (request.getStart() != null && request.getLength() != null) {
					
					Integer paramStart = request.getStart();
					
					Integer paramLength = request.getLength();
					
					if ( paramStart.intValue() > list.size() ) {
						return new ArrayList<>();
					}
					
					if ( paramLength.intValue() > list.size() ) {
						paramLength = list.size();
					}
					
					if ( paramStart.intValue() + paramLength.intValue() > list.size() ) {
						paramLength = list.size() - paramStart.intValue();
					}
					
					return list.subList(paramStart.intValue(), paramStart.intValue() + paramLength.intValue() );
					
				}
				
				return list;*/
			}
		}).build().handle(request);
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除字典")
	public void delete(@PathVariable String id) {
		
		SysDictEntity entity = this.sysDictService.getById(id);
		
		sysDictService.delSysDict(id);
		
		List<SysDictEntity> sysDictEntities = this.dictCacheManager.getDictTypeByValue(entity.getTypeValue());
			
		if ( sysDictEntities != null ) {
			
			this.dictCacheManager.deleteDictByTypeValue(entity.getTypeValue());
		}
		
	}
	
	@ApiOperation(value = "根据id获取字典详细信息")
	@GetMapping("/{id}")
	@RequiresPermissions("sys:user:query")
	public SysDictDTO dict(@PathVariable String id) {
		SysDictDTO dto = new SysDictDTO();
		BeanUtils.copyProperties(sysDictService.getById(id), dto);
		return dto;
	}
	
	@GetMapping(value="/list/{typeValue}")
	public Result<Object> getDictByTypeValue(@PathVariable String typeValue) {
		
		List<SysDictEntity> sysDictEntities = this.dictCacheManager.getDictTypeByValue(typeValue);
		
		if ( sysDictEntities == null ) {
			sysDictEntities = this.sysDictService.getDictByTypeValue(typeValue);
			
			this.dictCacheManager.saveDictTypeValueData(typeValue, sysDictEntities);
		}
		
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, sysDictEntities);
	}
	
}
