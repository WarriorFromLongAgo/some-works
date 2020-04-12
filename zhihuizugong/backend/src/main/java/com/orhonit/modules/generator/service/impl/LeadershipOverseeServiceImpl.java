package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.StringUtil;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;

import com.orhonit.modules.generator.dao.LeadershipOverseeDao;
import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;
import com.orhonit.modules.generator.service.LeadershipOverseeService;


@Service("leadershipOverseeService")
public class LeadershipOverseeServiceImpl extends ServiceImpl<LeadershipOverseeDao, LeadershipOverseeEntity> implements LeadershipOverseeService {

	@Autowired
	LeadershipOverseeDao leadershipOverseeDao;
	 /**
     * 领导督办已发列表
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	Long userId = (Long) params.get("userId");
    	String title = (String) params.get("overseeTitle");
        Page<LeadershipOverseeEntity> page = this.selectPage(
                new Query<LeadershipOverseeEntity>(params).getPage(),
                new EntityWrapper<LeadershipOverseeEntity>().and("user_id="+userId).like(StringUtil.isNotEmpty(title), "oversee_title",title).orderBy("crt_time DESC,complete_time asc")
        );
        page.setTotal(this.selectCount(new EntityWrapper<LeadershipOverseeEntity>().and("user_id="+userId).like(StringUtil.isNotEmpty(title),"oversee_title",title).orderBy("crt_time DESC,complete_time asc")));
        return new PageUtils(page);
    }
    /**
     * 领导督办已接受列表
     */
	@Override
	public PageUtils receptionList(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         List<LeadershipOverseeEntity> listSize= leadershipOverseeDao.receptionCount(params);
         params.put("page", page);
         params.put("limit", limit);
         Page<LeadershipOverseeEntity> infPage = new Page<>(currPage,limit);
         List<LeadershipOverseeEntity> list= leadershipOverseeDao.receptionList(params);
		 infPage.setTotal(listSize.size());
		 infPage.setRecords(list);
		return new PageUtils(infPage);
	}
	/**
     * 保存
     */
	@Override
	public void save(LeadershipOverseeEntity leadershipOversee) {
		leadershipOverseeDao.save(leadershipOversee);
	}
	/**
	 * 删除领导督办(关联删除相关人员和附件表)
	 */
	@Override
	public void deleteLeadershipOversee(String overseeId) {
		leadershipOverseeDao.deleteById(overseeId);
		leadershipOverseeDao.deletePeople(overseeId);
		leadershipOverseeDao.deleteFile(overseeId);
	}

}