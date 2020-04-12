package com.orhonit.modules.generator.service.impl;

import java.util.HashMap;
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
import com.orhonit.modules.generator.app.vo.AppWjwfVO;
import com.orhonit.modules.generator.dao.SupervisoryDao;
import com.orhonit.modules.generator.entity.SupervisoryEntity;
import com.orhonit.modules.generator.service.SupervisoryService;
import com.orhonit.modules.generator.vo.SubmittingVO;
@Service("SupervisoryService")
public class SupervisoryServiceImpl extends ServiceImpl<SupervisoryDao, SupervisoryEntity> implements SupervisoryService{

	@Autowired
	SupervisoryDao supervisoryDao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(params.containsKey("id")) {
			String id=String.valueOf(String.valueOf(params.get("id")));
			String name=String.valueOf(params.get("name"));
			if(name.equals("null")) {
				Page<SupervisoryEntity> page = this.selectPage(
		                new Query<SupervisoryEntity>(params).getPage(),
		                new EntityWrapper<SupervisoryEntity>()
		                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
		                .eq("unit_id",id)
		                .orderBy("create_time")
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<SupervisoryEntity>()
		        		.eq("is_del", CommonParameters.isDel.IS_DEL_NO)
		        		.eq("unit_id",id)));
		        return new PageUtils(page);
			}else if(name.equals("")){
				Page<SupervisoryEntity> page = this.selectPage(
		                new Query<SupervisoryEntity>(params).getPage(),
		                new EntityWrapper<SupervisoryEntity>()
		                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
		                .eq(true,"unit_id",id)
		                .orderBy("create_time")
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<SupervisoryEntity>()
		        		.eq("is_del", CommonParameters.isDel.IS_DEL_NO)
		        		.eq(true,"unit_id",id)));
		        return new PageUtils(page);
			}
			Page<SupervisoryEntity> page = this.selectPage(
	                new Query<SupervisoryEntity>(params).getPage(),
	                new EntityWrapper<SupervisoryEntity>()
	                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
	                .eq(true,"unit_id",id)
	                .and()
	                .like(true,"name", name)
	                .orderBy("create_time")
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<SupervisoryEntity>()
	        		.eq("is_del", CommonParameters.isDel.IS_DEL_NO)
	        		.eq(true,"unit_id",id)
	        		.eq(true,"name",name)));
	        return new PageUtils(page);

		}
		Page<SupervisoryEntity> page = this.selectPage(
                new Query<SupervisoryEntity>(params).getPage(),
                new EntityWrapper<SupervisoryEntity>()
                .eq("is_del", CommonParameters.isDel.IS_DEL_NO)
                .orderBy("create_time")
        );
        page.setTotal(this.selectCount(new EntityWrapper<SupervisoryEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
        return new PageUtils(page);
	}

	@Override
	public PageUtils getListPage(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<SupervisoryEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("beginLimit", beginLimit);
        params.put("limit",limit);
        List<SupervisoryEntity>list=supervisoryDao.getSuperviList(params);
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}

	@Override
	public SupervisoryEntity getOneSuper(Integer id) {
		// TODO Auto-generated method stub
		return supervisoryDao.getOneSup(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAllList(String type,Integer page,Integer limit, Integer userId) {
		String resultInfo = null;
		@SuppressWarnings("rawtypes")
		Map zh = new HashMap<>();
		Map<String,Object>map = new HashMap<String,Object>();
		//List<AppWjwfVO>list=supervisoryDao.getWjwfList(userId);//违纪违法
		SupervisoryEntity entity=supervisoryDao.countAttendance(userId);//考勤，请销假，培训出差统计
		switch(type) {
		case "1":
			map.put("userId", userId);
			map.put("limit", limit);
			map.put("page", page);
			zh.put("wjwf",this.wjwf(map));
			break;
		case "2":
			map.put("late", entity.getLate());
			map.put("earlyRetirement", entity.getEarlyRetirement());
			map.put("miner", entity.getMiner());
			zh.putAll(map);
			break;
		case "3":
			map.put("sickLeave", entity.getSickLeave());
			map.put("compassionateLeave", entity.getCompassionateLeave());
			map.put("vacation", entity.getVacation());
			zh.putAll(map);
			break;
		case "4":
			int goOutCount=supervisoryDao.selectCount(new EntityWrapper<SupervisoryEntity>().isNotNull("go_out").eq("user_id", userId));//出差次数
			int trainCount=supervisoryDao.selectCount(new EntityWrapper<SupervisoryEntity>().isNotNull("train").eq("user_id", userId));//培训次数
			map.put("goOut", entity.getGoOut());
			map.put("train", entity.getTrain());
			map.put("goOutCount", goOutCount);
			map.put("trainCount", trainCount);
			zh.putAll(map);
			break;
		default:
			resultInfo="没有数据";
			zh.put("resultInfo", resultInfo);
		}
		return zh;
	}
	
	protected PageUtils wjwf(Map<String, Object> params) {
		 int currPage = 1, limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt(params.get("page").toString());
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt(params.get("limit").toString());
         }
        Page<AppWjwfVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("page", beginLimit);
        params.put("limit",limit);
        List<AppWjwfVO>list=supervisoryDao.getWjwfList(params);//违纪违法
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}

}
