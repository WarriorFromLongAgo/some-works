package com.orhonit.modules.generator.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.WorkPlanDao;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import com.orhonit.modules.generator.service.WorkPlanService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

@Service
public class WorkPlanServiceImpl extends ServiceImpl<WorkPlanDao, WorkPlanEntity> implements WorkPlanService{

	@Autowired
	private WorkPlanDao workPlanDao;
	
	@Override
	public void savePlan(WorkPlanEntity sysWorkPlanEntity) {
		sysWorkPlanEntity.setCreateTime(new Date());
		workPlanDao.savePlan(sysWorkPlanEntity);
	}

	@Override
	public PageUtils findAllPlan(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         List<WorkPlanEntity> listCount = workPlanDao.findAllPlanCount(params);
         params.put("page", page);
         params.put("limit", limit);       
         Page<WorkPlanEntity> infPage = new Page<>(currPage,limit);
		 List<WorkPlanEntity> list = workPlanDao.findAllPlan(params);
		 infPage.setTotal(listCount.size());
		 infPage.setRecords(list);
		 return new PageUtils(infPage);
		
         
	}

	@Override
	public void changePlan(WorkPlanEntity sysWorkPlanEntity) {
		workPlanDao.changePlan(sysWorkPlanEntity);
		
	}

	@Override
	public void delPlan(String id) {
		workPlanDao.delPlan(id);
		
	}

	@Override
	public PageUtils findByExp(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkPlanEntity> workList = new ArrayList<WorkPlanEntity>();
		List<WorkPlanEntity> list = workPlanDao.findAllPlan(map);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (WorkPlanEntity workPlanEntity : list) {
			Date limitDate = workPlanEntity.getWorkTimeLimit();
			Date newDate = new Date();
			String limitD = simpleDateFormat.format(limitDate);
			String newD = simpleDateFormat.format(newDate);
			try {
				//判断在完成时限内有没有完成任务
				if (simpleDateFormat.parse(limitD).getTime() < simpleDateFormat.parse(newD).getTime()) {
					if (workPlanEntity.getExposure() == 0) {
						workList.add(workPlanEntity);
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         params.put("page", page);
         params.put("limit", limit);
         Page<WorkPlanEntity> infPage = new Page<WorkPlanEntity>(page,limit).setRecords(workList);
         this.selectPage(infPage);
         infPage.setTotal(workList.size());
		return new PageUtils(infPage);
	}

	@Override
	public PageUtils findByChange(Map<String, Object> params) {
		Integer exposure = null;
		String userName = null;
		if (params.get("exposure") != null && params.get("userName") != null) {
			exposure = Integer.parseInt((String)params.get("exposure"));
			userName = params.get("userName").toString();
	        Page<WorkPlanEntity> page = this.selectPage(
	                new Query<WorkPlanEntity>(params).getPage(),
	                new EntityWrapper<WorkPlanEntity>().where("exposure = "+exposure).like(StringUtils.isNotBlank(userName), "user_name", userName)
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<WorkPlanEntity>().where("exposure = "+exposure).like(StringUtils.isNotBlank(userName), "user_name", userName)));
			return new PageUtils(page);
		}else if (params.get("exposure") != null) {
			exposure = Integer.parseInt((String)params.get("exposure"));
	        Page<WorkPlanEntity> page = this.selectPage(
	                new Query<WorkPlanEntity>(params).getPage(),
	                new EntityWrapper<WorkPlanEntity>().where("exposure = "+exposure)
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<WorkPlanEntity>().where("exposure = "+exposure)));
			return new PageUtils(page);
		}else if (params.get("userName") != null) {
			userName = params.get("userName").toString();
	        Page<WorkPlanEntity> page = this.selectPage(
	                new Query<WorkPlanEntity>(params).getPage(),
	                new EntityWrapper<WorkPlanEntity>().like(StringUtils.isNotBlank(userName), "user_name", userName).and("exposure = "+2).or("exposure = "+3)
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<WorkPlanEntity>().like(StringUtils.isNotBlank(userName), "user_name", userName).and("exposure = "+2).or("exposure = "+3)));
			return new PageUtils(page);
		}else {
	        Page<WorkPlanEntity> page = this.selectPage(
	                new Query<WorkPlanEntity>(params).getPage(),
	                new EntityWrapper<WorkPlanEntity>().where("exposure = "+2).or("exposure = "+3)
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<WorkPlanEntity>().where("exposure = "+2).or("exposure = "+3)));
			return new PageUtils(page);
		}

	}

	@Override
	public List<ZgPlanFileEntity> findFile(Map<String, Object> params) {	
		return workPlanDao.findFile(params);
	}

	@Override
	public void changeFinishStatus(String id) {
		WorkPlanEntity entity = new WorkPlanEntity();
		entity.setId(id);
		entity.setFinishTime(new Date());
		entity.setExposure(1);
		workPlanDao.changeFinishStatus(entity);
	}

	@Override
	public Map<String, List<TaDepartmentMemberEntity>> findLowerList(Long userId) {
		Map<String, List<TaDepartmentMemberEntity>> map = new HashMap<String, List<TaDepartmentMemberEntity>>();
		List<TaDepartmentMemberEntity> results = workPlanDao.findLowerList(userId);
		map.put("results", results);
		return map;
	}

	@Override
	public void changeExp(Map<String, Object> params) {
		workPlanDao.changeExp(params);
	}


}
