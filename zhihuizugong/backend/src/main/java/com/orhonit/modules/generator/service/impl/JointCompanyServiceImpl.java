package com.orhonit.modules.generator.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.JointCompanyDao;
import com.orhonit.modules.generator.entity.JointCompanyEntity;
import com.orhonit.modules.generator.service.JointCompanyService;
import com.orhonit.modules.generator.vo.JointCompanyVO;
import com.orhonit.modules.generator.vo.UserAndOrgVO;
import com.orhonit.modules.sys.dao.SysUserDao;

@Service("JointCompanyService")
public class JointCompanyServiceImpl extends ServiceImpl<JointCompanyDao, JointCompanyEntity> implements JointCompanyService {
	
	@Autowired
	JointCompanyDao Dao;
	@Autowired
	SysUserDao sysUserDao;
	
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		boolean contains=params.containsKey("sharingUnit");
		if(contains) {
			Object object=(String)params.get("sharingUnit");
			//String sharingUnit = object.toString();
			int sharingUnit = object.hashCode();
			 Page<JointCompanyEntity> page = this.selectPage(
		                new Query<JointCompanyEntity>(params).getPage(),
		                new EntityWrapper<JointCompanyEntity>()
		                .where("1=1")
		                .eq(sharingUnit>=0,"sharing_unit",sharingUnit)
		                .orderBy("create_time")
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<JointCompanyEntity>()
		        		.eq("sharing_unit",sharingUnit)));
		        return new PageUtils(page);
		}else {
			 Page<JointCompanyEntity> page = this.selectPage(
		                new Query<JointCompanyEntity>(params).getPage(),
		                new EntityWrapper<JointCompanyEntity>().orderBy("create_time")
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<JointCompanyEntity>()));
		        return new PageUtils(page);
		}
	}

	@Override
	public PageUtils jointqueryPage(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<JointCompanyVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("beginLimit", beginLimit);
        params.put("limit",limit);
        List<JointCompanyVO>list=Dao.joinList(params);
    	page.setRecords(list);
    	boolean contains=params.containsKey("sharingUnit");
    	if(contains) {
    		Object object=(String)params.get("sharingUnit");
    		if(!object.equals("")) {
    			page.setTotal(list.size());
    		}else {
    			page.setTotal(this.selectCount(new EntityWrapper<JointCompanyEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
    		}
    	}
    	boolean orgNamecontains=params.containsKey("orgName");
    	if(orgNamecontains) {
    		Object object=(String)params.get("orgName");
    		if(!object.equals("")) {
    			page.setTotal(list.size());
    		}else {
    			page.setTotal(this.selectCount(new EntityWrapper<JointCompanyEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
    		}
    	}
    	page.setTotal(list.size());
		/*
		 * boolean Economics = params.containsKey("Economics"); if(Economics) { Object
		 * Economics1=(String)params.get("Economics"); if(!Economics1.equals("")) {
		 * page.setTotal(list.size()); }else { page.setTotal(this.selectCount(new
		 * EntityWrapper<JointCompanyEntity>().eq("is_del",
		 * CommonParameters.isDel.IS_DEL_NO))); } } boolean Illegal =
		 * params.containsKey("Illegal"); if(Illegal) { Object Illegal1=
		 * (String)params.get("Illegal"); if(!(Illegal1).equals("")) {
		 * page.setTotal(list.size()); }else { page.setTotal(this.selectCount(new
		 * EntityWrapper<JointCompanyEntity>().eq("is_del",
		 * CommonParameters.isDel.IS_DEL_NO))); } }
		 */
		return new PageUtils(page);
	}

	@Override
	public PageUtils userList(Map<String,Object>params) {
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<UserAndOrgVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("page", beginLimit);
        params.put("limit",limit);
        List<UserAndOrgVO>list=sysUserDao.getUserSelectList(params);
        page.setRecords(list);
        page.setTotal(list.size());
		return new PageUtils(page);
		
	}

	@Override
	public JointCompanyVO getOneJoint(Integer id) {
		
		return Dao.getOneJoint(id);
	}
}
