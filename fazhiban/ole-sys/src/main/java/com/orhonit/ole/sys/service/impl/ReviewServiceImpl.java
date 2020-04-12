package com.orhonit.ole.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dao.ReviewDao;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.dto.ReviewRecordItemDTO;
import com.orhonit.ole.sys.model.ReviewItemEntity;
import com.orhonit.ole.sys.model.ReviewRecordEntity;
import com.orhonit.ole.sys.model.ReviewRecordItemEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.ReviewRecordItemRepository;
import com.orhonit.ole.sys.repository.ReviewRecordRepository;
import com.orhonit.ole.sys.service.ReviewService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;


/**
 * 案卷评查
 * @author liuzhih
 *
 */

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRecordItemRepository reviewRecordItemRepository;
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ReviewRecordRepository reviewRecordRepository;
	
	@Autowired
	private ReviewDao reviewDao;

	@Override
	public void saveReviewRecord(ReviewRecordDTO reviewRecordDTO){
		
		this.reviewDao.deleteRecordItemByCaseId(reviewRecordDTO.getCaseId());
		this.reviewDao.deleteRecordByCaseId(reviewRecordDTO.getCaseId());
		ReviewRecordEntity reviewEntity=new ReviewRecordEntity();
		BeanUtils.copyProperties(reviewRecordDTO, reviewEntity);
		if(StrUtil.isNotEmpty(reviewRecordDTO.getScore())){
			reviewEntity.setScore(100-reviewRecordDTO.getScore());
		}
		User user = UserUtil.getCurrentUser();
		reviewEntity.setCreateBy(user.getUsername());
		reviewEntity.setCreateDate(new Date());
		reviewEntity.setCreateName(user.getNickname());
		this.reviewRecordRepository.save(reviewEntity);
		List<ReviewRecordItemEntity> recordItems=new ArrayList<ReviewRecordItemEntity>(); 
		ReviewRecordItemEntity reviewTemp;
		for(ReviewRecordItemDTO recordItemDTO:reviewRecordDTO.getItemIds()){
			reviewTemp=new ReviewRecordItemEntity();
			reviewTemp.setItemId(recordItemDTO.getItemId());
			reviewTemp.setIsTop(recordItemDTO.getIsTop());
			reviewTemp.setRecordId(reviewEntity.getId());
			reviewTemp.setCreateBy(user.getUsername());
			reviewTemp.setCreateDate(new Date());
			reviewTemp.setCreateName(user.getNickname());
			recordItems.add(reviewTemp);
		}
		this.reviewRecordItemRepository.save(recordItems);

	}
	
	@Override
	public void saveReviewRecordByapp(ReviewRecordDTO reviewRecordDTO){

		List<ReviewRecordEntity> recordEntities=this.reviewDao.getReviewRecordByCaseId(reviewRecordDTO.getCaseId());
		ReviewRecordEntity reviewEntity=new ReviewRecordEntity();
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		if(recordEntities!=null&&recordEntities.size()>0){
			reviewEntity=this.reviewRecordRepository.getOne(recordEntities.get(0).getId());
			if(StrUtil.isNotEmpty(reviewRecordDTO.getRemark())){
				reviewEntity.setRemark(reviewRecordDTO.getRemark());
			}
			if(StrUtil.isNotEmpty(reviewRecordDTO.getStatus())){
				reviewEntity.setStatus(reviewRecordDTO.getStatus());
			}
			if(StrUtil.isNotEmpty(reviewRecordDTO.getScore())){
				reviewEntity.setScore(reviewRecordDTO.getScore());
			}
			if(StrUtil.isNotEmpty(reviewRecordDTO.getCaseId())){
				reviewEntity.setCaseId(reviewRecordDTO.getCaseId());
			}
			reviewEntity.setUpdateBy(user.getUsername());
			reviewEntity.setUpdateDate(new Date());
			reviewEntity.setUpdateName(user.getNickname());
		}else
		{
			reviewEntity.setCaseId(reviewRecordDTO.getCaseId());
			reviewEntity.setStatus(reviewRecordDTO.getStatus());
			reviewEntity.setRemark(reviewRecordDTO.getRemark());
			reviewEntity.setScore(reviewRecordDTO.getScore());
			reviewEntity.setCreateBy(user.getUsername());
			reviewEntity.setCreateDate(new Date());
			reviewEntity.setCreateName(user.getNickname());
		}
		this.reviewRecordRepository.save(reviewEntity);
		//保存评查项目
		if(reviewRecordDTO.getItemIds()!=null&&reviewRecordDTO.getItemIds().size()>0){
			List<ReviewRecordItemEntity> recordItems=new ArrayList<ReviewRecordItemEntity>(); 
			ReviewRecordItemEntity reviewTemp;
			for(ReviewRecordItemDTO recordItemDTO:reviewRecordDTO.getItemIds()){
				reviewTemp=new ReviewRecordItemEntity();
				reviewTemp.setItemId(recordItemDTO.getItemId());
				reviewTemp.setIsTop(recordItemDTO.getIsTop());
				reviewTemp.setRecordId(reviewEntity.getId());
				reviewTemp.setCreateBy(user.getUsername());
				reviewTemp.setCreateDate(new Date());
				reviewTemp.setCreateName(user.getNickname());
				recordItems.add(reviewTemp);
			}
			this.reviewRecordItemRepository.save(recordItems);
		}
	}

	@Override
	public Integer getCount(Map<String, Object> params) {
		return this.reviewDao.getCount(params);
	}

	@Override
	public List<ReviewItemEntity> getList(Map<String, Object> params, Integer start, Integer length) {
		return this.reviewDao.getList(params,start,length);
	}

	@Override
	public List<ReviewItemEntity> getAllReviewItem() {
		return this.reviewDao.getAllReviewItem();
	}

	@Override
	public List<ReviewItemEntity> getItemIdByParentId(Integer id,String caseId) {
			List<ReviewItemEntity>  itemEntities=this.reviewDao.getItemIdByParentId(id);
			List<ReviewRecordItemEntity>  recordItems= this.reviewDao.getRrcordItemIdByCaseId(caseId);
			if(itemEntities!=null&&itemEntities.size()>0){
				if(recordItems!=null&&recordItems.size()>0){
					itemEntities.get(0).setScoreSum(recordItems.get(0).getScore());
					itemEntities.get(0).setRemark(recordItems.get(0).getRemark());
					itemEntities.get(0).setStatus(recordItems.get(0).getStatus());
					itemEntities.get(0).setCreateBy(recordItems.get(0).getCreateBy());
				}
			}
			List<Integer> keys=new ArrayList<>();
			for(ReviewRecordItemEntity recordItem:recordItems){
				keys.add(recordItem.getItemId());
			}
			for(ReviewItemEntity item:itemEntities){
				if(keys.contains(item.getId())){
					item.setIsSelected(1);
				}
			}
			return itemEntities;
		}

	@Override
	public int getReviewRecordCount(Map<String, Object> params) {
		return this.reviewDao.getReviewRecordCount(params);
	}

	@Override
	public List<ReviewRecordDTO> getReviewRecordList(Map<String, Object> params, Integer start, Integer length) {
		return this.reviewDao.getReviewRecordList(params,start,length);
	}
	
	@Override
	public ReviewRecordDTO getReviewRecordByCaseId(String caseId) {
		List<ReviewRecordEntity> list = this.reviewDao.getReviewRecordByCaseId(caseId);
		if(null == list || list.size() < 1) {
			return null;
		}
		ReviewRecordDTO dto = new ReviewRecordDTO();
		BeanUtils.copyProperties(list.get(0), dto);
		return dto;
	}

	@Override
	public void submitReview(ReviewRecordDTO reviewRecordDTO) {
		 ReviewRecordEntity entity=	this.reviewRecordRepository.findOne(reviewRecordDTO.getId());
		 entity.setUpdateDate(reviewRecordDTO.getUpdateDate());
		 entity.setUpdateName(reviewRecordDTO.getUpdateName());
		 entity.setUpdateBy(reviewRecordDTO.getUpdateBy());
		 //entity.setScore(reviewRecordDTO.getScore());
		 entity.setStatus(reviewRecordDTO.getStatus());
		 if(StrUtil.isNotEmpty(reviewRecordDTO.getOneComment())){
			 entity.setOneComment(reviewRecordDTO.getOneComment());	
		 }
		if(StrUtil.isNotEmpty(reviewRecordDTO.getTwoComment())){
			 entity.setTwoComment(reviewRecordDTO.getTwoComment());	
		}
		this.reviewRecordRepository.save(entity);
	}

	@Override
	public  List<ReviewRecordItemEntity> getReviewRecordItemIdByCaseId(String caseId) {
		List<ReviewRecordItemEntity>  recordItems= this.reviewDao.getRrcordItemIdByCaseId(caseId);
		return recordItems;
	}

	@Override
	public List<ReviewRecordItemEntity> getAppReviewRecordItemIdByCaseId(String caseId) {
		List<ReviewRecordItemEntity>  recordItems= this.reviewDao.getAppReviewRecordItemIdByCaseId(caseId);
		return recordItems;
	}

	@Override
	public void deleteReviewRecordItemIdById(String id) {
		this.reviewRecordItemRepository.delete(Integer.valueOf(id));
	}
}
