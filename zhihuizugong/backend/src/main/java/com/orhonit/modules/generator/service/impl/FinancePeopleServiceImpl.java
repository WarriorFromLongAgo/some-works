package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;

import com.orhonit.modules.generator.dao.FinancePeopleDao;
import com.orhonit.modules.generator.entity.FinancePeopleEntity;
import com.orhonit.modules.generator.entity.OverseePeopleEntity;
import com.orhonit.modules.generator.service.FinanceManagementService;
import com.orhonit.modules.generator.service.FinancePeopleService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;


@Service("financePeopleService")
public class FinancePeopleServiceImpl extends ServiceImpl<FinancePeopleDao, FinancePeopleEntity> implements FinancePeopleService {

	@Autowired
	FinancePeopleDao financePeopleDao;
	@Autowired
	SysUserDao sysUserDao;
	@Autowired
    private FinanceManagementService financeManagementService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FinancePeopleEntity> page = this.selectPage(
                new Query<FinancePeopleEntity>(params).getPage(),
                new EntityWrapper<FinancePeopleEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void insertAllPeople(Integer[] userId,String financeId) {
		List<FinancePeopleEntity> peopleList = financePeopleDao.allList(financeId);
		if(peopleList.size() > 0) {
			financePeopleDao.deletePeople(financeId);
		}
		if(userId.length > 0) {
			for (Integer integer : userId) {
				FinancePeopleEntity financePeopleEntity = new FinancePeopleEntity();
				SysUserEntity user=sysUserDao.selectById(integer);
				financePeopleEntity.setLowerId(user.getLowerId());
				financePeopleEntity.setLowerName(user.getLowerName());
				financePeopleEntity.setPeopleName(user.getUserTrueName());
				financePeopleEntity.setUserDept(user.getUserDept());
				financePeopleEntity.setCreateTime(new Date());
				financePeopleEntity.setUserId(user.getUserId());
				financePeopleEntity.setFinanceId(financeId);
				financePeopleDao.insert(financePeopleEntity);
			}
		}
	}

	@Override
	public void deletePeople(Integer userId, String financeId) {
		financePeopleDao.deletePeople(userId,financeId);
	}

	@Override
	public List<FinancePeopleEntity> queryList(Map<String, Object> params) {
		String financeId = (String) params.get("financeId");
        List<FinancePeopleEntity> page = this.selectList(
                new EntityWrapper<FinancePeopleEntity>().and("finance_id="+financeId)
        );

        return page;
	}

	@Override
	public List<FinancePeopleEntity> allList(String financeId) {
		return financePeopleDao.allList(financeId);
	}

	@Override
	public List<FinancePeopleEntity> lowerList(Integer lowerId) {
		return financePeopleDao.lowerList(lowerId);
	}

}