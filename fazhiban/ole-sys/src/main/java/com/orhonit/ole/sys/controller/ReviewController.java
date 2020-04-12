package com.orhonit.ole.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.dto.ReviewRecordItemDTO;
import com.orhonit.ole.sys.model.AreaEntity;
import com.orhonit.ole.sys.model.DeptDTO;
import com.orhonit.ole.sys.model.ReviewItemEntity;
import com.orhonit.ole.sys.model.ReviewRecordItemEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.AreaRepository;
import com.orhonit.ole.sys.repository.ReviewItemRepository;
import com.orhonit.ole.sys.service.ReviewService;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 案卷评查控制类
 * @author liuzhih
 *
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewItemRepository reviewItemRepository;
	
	@Autowired
	SysDeptService sysDeptService;
	
	@Autowired
	AreaRepository areaRepository;

	@LogAnnotation
	@PostMapping("/save")
	@ApiOperation(value = "保存评查项目")
	public void saveReview(@RequestBody ReviewRecordDTO reviewRecordDTO) {
		List<ReviewRecordItemDTO> itemIds=JSONObject.parseArray(reviewRecordDTO.getItemIdsStr(), ReviewRecordItemDTO.class);
		reviewRecordDTO.setItemIds(itemIds);
		reviewService.saveReviewRecord(reviewRecordDTO);
	}
	
	
	@LogAnnotation
	@PostMapping("/saveItem")
	@ApiOperation(value = "保存评查条目")
	public void saveItem(@RequestBody ReviewItemEntity reviewItemEntity) {
		User user = UserUtil.getCurrentUser();
		if(StringUtils.isEmpty(reviewItemEntity.getParentName())
			||reviewItemEntity.getParentId()==null){
			reviewItemEntity.setParentId(0);
		}
		reviewItemEntity.setDelFlag("0");
		reviewItemEntity.setCreateDate(new Date());
		reviewItemEntity.setCreateName(user.getNickname());
		reviewItemEntity.setCreateBy(user.getUsername());
		this.reviewItemRepository.save(reviewItemEntity);
	}
	
	@LogAnnotation
	@PostMapping("/updateItem")
	@ApiOperation(value = "修改评查条目")
	public void updateItem(@RequestBody ReviewItemEntity reviewItemEntity) {
		User user = UserUtil.getCurrentUser();
		if(StringUtils.isEmpty(reviewItemEntity.getParentName())
				||reviewItemEntity.getParentId()==null){
				reviewItemEntity.setParentId(0);
			}
		ReviewItemEntity reviewItemEntitys= reviewItemRepository.findOne(reviewItemEntity.getId());
		reviewItemEntitys.setName(reviewItemEntity.getName());
		reviewItemEntitys.setContent(reviewItemEntity.getContent());
		reviewItemEntitys.setScore(reviewItemEntity.getScore());
		reviewItemEntitys.setSort(reviewItemEntity.getSort());
		reviewItemEntitys.setIfEffect(reviewItemEntity.getIfEffect());
		reviewItemEntitys.setParentId(reviewItemEntity.getParentId());
		reviewItemEntitys.setParentName(reviewItemEntity.getParentName());
		reviewItemEntitys.setUpdateBy(user.getUsername());
		reviewItemEntitys.setUpdateDate(new Date());
		reviewItemEntitys.setUpdateName(user.getNickname());
		
		this.reviewItemRepository.save(reviewItemEntitys);
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "评查内容列表")
	public TableResponse<ReviewItemEntity> list(TableRequest request) {
		return TableRequestHandler.<ReviewItemEntity> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=reviewService.getCount(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<ReviewItemEntity>() {

			@Override
			public List<ReviewItemEntity> list(TableRequest request) {
				List<ReviewItemEntity> lists=reviewService.getList(request.getParams(), request.getStart(), request.getLength());
				return lists;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/parentsTree")
	@ApiOperation(value = "评查类别划分")
	public List<Map<String, Object>> parentsTree() {
		List<ReviewItemEntity> parents = this.reviewService.getAllReviewItem();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(ReviewItemEntity reviewItem:parents){
			map=new HashMap<>();
			map.put("id",reviewItem.getId());		
			map.put("pId",reviewItem.getParentId());
			map.put("name", reviewItem.getName());
			listMap.add(map);
		}
		return listMap;
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除")
	public void delete(@PathVariable Integer id) {
		ReviewItemEntity reviewItemEntity= reviewItemRepository.findOne(id);
		reviewItemEntity.setDelFlag("1");
		reviewItemRepository.save(reviewItemEntity);
	}
	
	@LogAnnotation
	@GetMapping("/{id}")
	@ApiOperation(value = "删除")
	public ReviewItemEntity findOne(@PathVariable Integer id) {
		return reviewItemRepository.findOne(id);	
	}
	
	@GetMapping("/getItemIdByParentId")
	@ApiOperation(value = "查询下级评查项目")
	public List<ReviewItemEntity> getItemIdByParentId(@RequestParam(value="typeid",required = false) Integer typeid,
			@RequestParam(value="caseId",required = false) String caseId) {
		return this.reviewService.getItemIdByParentId(typeid,caseId);	
	}
	
	
	@GetMapping("/getReviewRecordItemIdByCaseId")
	@ApiOperation(value = "根据案件ID查询下级评查项目")
	public Result<Object>  getReviewRecordItemIdByCaseId(
			@RequestParam(value="caseId",required = false) String caseId) {
		 List<ReviewRecordItemEntity> list= this.reviewService.getReviewRecordItemIdByCaseId(caseId);	
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,list);
	}
	
	
	
	
	
	@GetMapping("/tree")
	@ApiOperation(value = "评查分类")
	public List<Map<String, Object>> ltcAll() {
		List<ReviewItemEntity> parents = this.reviewService.getAllReviewItem();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(ReviewItemEntity reviewItem:parents){
			map=new HashMap<>();
			map.put("id",reviewItem.getId());
			map.put("parentId",reviewItem.getParentId());
			map.put("text", reviewItem.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		return retMap;
	}
	
	
	@GetMapping("/recordlist")
	@ApiOperation(value = "评查记录列表")
	public TableResponse<ReviewRecordDTO> recordlist(TableRequest request) {
		Map<String,Object> params=request.getParams();
		User user =UserUtil.getCurrentUser();
		if (!user.isAdmin()) {
			DeptDTO deptDTO = this.sysDeptService.findDeptById(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				// 如果是市本级的法制办则查询所有
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				if (CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					
				} else {
					params.put("areaId", user.getArea_id());
				}
			}else{
				params.put("deptId",user.getDept_id());
			}
		}
		return TableRequestHandler.<ReviewRecordDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=reviewService.getReviewRecordCount(params);
				return result;
			}
		}).listHandler(new ListHandler<ReviewRecordDTO>() {

			@Override
			public List<ReviewRecordDTO> list(TableRequest request) {
				List<ReviewRecordDTO> lists=reviewService.getReviewRecordList(params, request.getStart(), request.getLength());
				return lists;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/recordScore")
	@ApiOperation(value = "取分数")
	public ReviewRecordDTO recordScore(TableRequest request) {
		
		Map<String,Object> params=request.getParams();
		System.out.println("==================================================================================================");
		System.out.println(params);
		System.out.println(params.get("caseId"));
		System.out.println("==================================================================================================");
		ReviewRecordDTO dto = reviewService.getReviewRecordByCaseId(params.get("caseId").toString());
		return dto;
	}
	
	@PostMapping("/recordScores")
	@ApiOperation(value = "取分数s")
	public ReviewRecordDTO recordScores(TableRequest request) {
		
		Map<String,Object> params=request.getParams();
		System.out.println("==================================================================================================");
		System.out.println(params);
		System.out.println(params.get("caseId"));
		System.out.println("==================================================================================================");
		ReviewRecordDTO dto = reviewService.getReviewRecordByCaseId(params.get("caseId").toString());
		return dto;
	}
	
	@PostMapping("/submitReview")
	@ApiOperation(value = "评查记录提交")
	public void submitReview(@RequestBody ReviewRecordDTO reviewRecordDTO) {
		User user = UserUtil.getCurrentUser();
		reviewRecordDTO.setUpdateBy(user.getUsername());
		reviewRecordDTO.setUpdateDate(new Date());
		reviewRecordDTO.setUpdateName(user.getNickname());
		this.reviewService.submitReview(reviewRecordDTO);
	}
}
