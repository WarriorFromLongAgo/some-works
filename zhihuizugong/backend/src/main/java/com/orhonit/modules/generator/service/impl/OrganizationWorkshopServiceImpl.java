package com.orhonit.modules.generator.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.OrganizationWorkshopDao;
import com.orhonit.modules.generator.entity.OrganizationWorkshopEntity;
import com.orhonit.modules.generator.service.OrganizationWorkshopService;
import com.orhonit.modules.generator.vo.OrganizationWorkshopVO;


/**
  * 组工讲堂
 * @author YaoSC
 *
 */
@Service("OrganizationWorkshopService")
public class OrganizationWorkshopServiceImpl extends ServiceImpl<OrganizationWorkshopDao, OrganizationWorkshopEntity>
                                                           implements OrganizationWorkshopService{
	
	@Autowired
	OrganizationWorkshopDao organizationWorkshopDao;

	@Override
	public PageUtils queryPage(Map<String,Object>params) {
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<OrganizationWorkshopEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("beginLimit", beginLimit);
        params.put("limit",limit);
		List<OrganizationWorkshopEntity>list=organizationWorkshopDao.shipinList(params);
		page.setRecords(list);
	    page.setTotal(this.selectCount(new EntityWrapper<OrganizationWorkshopEntity>()));
		return new PageUtils(page);
	}
	

	@Override
	public void deleteWorkShop(Integer courseId) {
		organizationWorkshopDao.deleteworkShop(courseId);
		
	}

	@Override
	public void updateShop(OrganizationWorkshopEntity entity) {
		organizationWorkshopDao.updateShop(entity);
		
	}

	@Override
	public PageUtils appqueryPage(Map<String, Object> params) {

		 Page<OrganizationWorkshopEntity> page = this.selectPage(
	                new Query<OrganizationWorkshopEntity>(params).getPage(),
	                new EntityWrapper<OrganizationWorkshopEntity>()
	                .like(StringUtils.isNotBlank((String)params.get("commendType")), "commend_type", (String)params.get("commendType"))
	                .like(StringUtils.isNotBlank((String)params.get("courseType")), "course_type", (String)params.get("courseType"))
	                //.eq((String)params.get("course_type") !=null,"course_type", (String)params.get("courseType"))
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<OrganizationWorkshopEntity>()
	        		.like(StringUtils.isNotBlank((String)params.get("commendType")), "commend_type", (String)params.get("commendType"))
	        		.like(StringUtils.isNotBlank((String)params.get("courseType")), "course_type", (String)params.get("courseType"))));
	                //.eq(params.get("course_type") !=null,"course_type", (String)params.get("courseType"))));
	        return new PageUtils(page);
	}

	@Override
	public OrganizationWorkshopEntity selectWorkShop(Integer courseId) {
	
		return organizationWorkshopDao.selectWorkShop(courseId);
	}

	@Override
	public PageUtils AppqueryPage(Map<String, Object> params) {
		String courseType=String.valueOf(params.get("courseType"));
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<OrganizationWorkshopVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("limit", limit);
        params.put("beginLimit", beginLimit);
        params.remove("page",page);
        List<OrganizationWorkshopVO> list =null;
        if("null".equals(courseType)) {
        	list=organizationWorkshopDao.selectList1(beginLimit,limit);
        	page.setRecords(list);
            page.setTotal(list.size());
        }else {
        	list = organizationWorkshopDao.list(courseType ,beginLimit,limit);
        	page.setRecords(list);
        	if(null==list || list.size()==0) {
        		 page.setTotal(list.size());
        	}else {
        		 page.setTotal(this.selectCount(new EntityWrapper<OrganizationWorkshopEntity>()));
        	}
        }
		return new PageUtils(page);
	}
}
