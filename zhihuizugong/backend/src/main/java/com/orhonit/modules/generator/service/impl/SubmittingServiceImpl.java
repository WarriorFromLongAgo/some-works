package com.orhonit.modules.generator.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.SubmittingDao;
import com.orhonit.modules.generator.dao.SupervisoryDao;
import com.orhonit.modules.generator.entity.SubmittingEntity;
import com.orhonit.modules.generator.entity.SupervisoryEntity;
import com.orhonit.modules.generator.service.SubmittingService;
import com.orhonit.modules.generator.vo.SubmittingVO;


@Service("SubmittingService")
public class SubmittingServiceImpl extends ServiceImpl<SubmittingDao, SubmittingEntity> implements SubmittingService{

	
	@Autowired
	SubmittingDao submittingDao;
	@Autowired
	SupervisoryDao supervisoryDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(params.containsKey("submittedCompany")) {
			Object submittedCompany=params.get("submittedCompany");
			 if(submittedCompany.equals("")) {
				 Page<SubmittingEntity> page = this.selectPage(
			                new Query<SubmittingEntity>(params).getPage(),
			                new EntityWrapper<SubmittingEntity>()
			                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
			                .orderBy("create_time")
			        );
			        page.setTotal(this.selectCount(new EntityWrapper<SubmittingEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
			        return new PageUtils(page);
             }else {
            	 Page<SubmittingEntity> page = this.selectPage(
 		                new Query<SubmittingEntity>(params).getPage(),
 		                new EntityWrapper<SubmittingEntity>()
 		                .eq(StringUtils.isNotBlank("submittedCompany"),"submitted_company", submittedCompany)
 		                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
 		                .orderBy("create_time"));
            	 
            	  page.setTotal(this.selectCount(new EntityWrapper<SubmittingEntity>()
            			  .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
  		        		.eq(StringUtils.isNotBlank("submittedCompany"),"submitted_company", submittedCompany)));
  		        return new PageUtils(page);
             }
			
		}else {
			 Page<SubmittingEntity> page = this.selectPage(
		                new Query<SubmittingEntity>(params).getPage(),
		                new EntityWrapper<SubmittingEntity>()
		                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
		                .orderBy("create_time")
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<SubmittingEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
		        return new PageUtils(page);
		}
	}
	@Override
	public Map<String, Object> map(Integer id) throws IOException {
		Map <String,Object> map = new HashMap<String,Object>(16);
		List<SupervisoryEntity>list =supervisoryDao.getSupList(id);
		for(int i=0;i<list.size();i++) {
			SupervisoryEntity entity=list.get(i);
			String mw=null;
			String pj=null;
			if(entity.getMainWork() !=null) {
				 mw=StringEscapeUtils.unescapeJava(new String(entity.getMainWork().getBytes("ISO-8859-1"),"UTF-8"));
			}
		   if(entity.getProject() !=null) {
			   pj =StringEscapeUtils.unescapeJava(new String(entity.getProject().getBytes("ISO-8859-1"),"utf-8"));
		   }
	    	entity.setMainWork(mw);
	    	entity.setProject(pj);
		}
		map.put("supervisory", list);
		return map;
	}
	@Override
	public void updateSubitAndVisory(Integer id) {
		SubmittingEntity entity = new SubmittingEntity();
		entity.setUpdateTime(new Date());
		entity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
		submittingDao.update(entity, new EntityWrapper<SubmittingEntity>().eq("id", id));
		int count = supervisoryDao.selectCount(new EntityWrapper<SupervisoryEntity>().eq("unit_id", id));
		if(count>0) {
			SupervisoryEntity visory = new SupervisoryEntity();
			visory.setUpdateTime(new Date());
			visory.setIsDel(CommonParameters.isDel.IS_DEL_YES);
			supervisoryDao.update(visory, new EntityWrapper<SupervisoryEntity>().eq("unit_id", id));
		}
		
	}
	@Override
	public PageUtils submitListPage(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<SubmittingVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("beginLimit", beginLimit);
        params.put("limit",limit);
        List<SubmittingVO>list= submittingDao.getSubmittingList(params);
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}
	@Override
	public SubmittingVO getOneSubmit(Integer id) {
		return submittingDao.getOneSubmitt(id);
	}

}
