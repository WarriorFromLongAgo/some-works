package com.orhonit.modules.generator.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.dao.OverseePeopleDao;
import com.orhonit.modules.generator.entity.OverseePeopleEntity;
import com.orhonit.modules.generator.service.OverseePeopleService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;


@Service("overseePeopleService")
public class OverseePeopleServiceImpl extends ServiceImpl<OverseePeopleDao, OverseePeopleEntity> implements OverseePeopleService {

	@Autowired
	OverseePeopleDao overseePeopleDao;
	@Autowired
	SysUserDao sysUserDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OverseePeopleEntity> page = this.selectPage(
                new Query<OverseePeopleEntity>(params).getPage(),
                new EntityWrapper<OverseePeopleEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void deletePeople(Integer userId, String overseeId) {
		overseePeopleDao.deletePeople(userId,overseeId);
	}

	@Override
	public R insertAllPeople(Long userId,String overseeId) {
		List<OverseePeopleEntity> people =overseePeopleDao.selectPeople(userId,overseeId);
		if(people.size() > 0) {
			return R.error("已添加该用户，请勿重复添加");
		}else {
			OverseePeopleEntity OverseePeopleEntity = new OverseePeopleEntity();
			SysUserEntity user=sysUserDao.selectById(userId);
			OverseePeopleEntity.setLowerId(user.getLowerId());
			OverseePeopleEntity.setPeopleName(user.getUserTrueName());
			OverseePeopleEntity.setDeptId(user.getUserDept());
			OverseePeopleEntity.setCrtTime(new Date());
			OverseePeopleEntity.setUserId(user.getUserId());
			OverseePeopleEntity.setOverseeId(overseeId);
			OverseePeopleEntity.setMobilePhone(user.getMobile());
			overseePeopleDao.insert(OverseePeopleEntity);
			return R.ok();
		}
		
	}

	@Override
	public List<OverseePeopleEntity> allList(String overseeId) {
		return overseePeopleDao.allList(overseeId);
	}

}