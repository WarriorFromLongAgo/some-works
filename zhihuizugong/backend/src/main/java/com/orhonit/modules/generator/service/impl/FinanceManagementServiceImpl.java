package com.orhonit.modules.generator.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;

import com.orhonit.modules.generator.dao.FinanceManagementDao;
import com.orhonit.modules.generator.entity.FinanceManagementEntity;
import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;
import com.orhonit.modules.generator.service.FinanceManagementService;
import com.orhonit.modules.sys.service.TaDepartmentMemberService;


@Service("financeManagementService")
public class FinanceManagementServiceImpl extends ServiceImpl<FinanceManagementDao, FinanceManagementEntity> implements FinanceManagementService {

	@Autowired
	FinanceManagementDao financeManagementDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	//创建时间模糊
    	String createTime = (String) params.get("createTime");
    	//文章类型
    	Integer type = Integer.parseInt(params.get("type").toString());
    	//标题模糊
    	String title = (String) params.get("title");
    	//接收人列表
    	String receiveId = (String) params.get("receiveId");
    	Integer receive = null;
    	if(StringUtils.isNotBlank(receiveId)) {
    		receive = Integer.parseInt(receiveId);
    	}
    	//发送人列表
    	String sendId = (String) params.get("sendId");
    	Integer send = null;
    	if(StringUtils.isNotBlank(sendId)) {
    		send = Integer.parseInt(sendId);
    	}
		Page<FinanceManagementEntity> page = this.selectPage(
                new Query<FinanceManagementEntity>(params).getPage(),
                new EntityWrapper<FinanceManagementEntity>().like(StringUtils.isNotBlank(title), "title", title).and(StringUtils.isNotBlank(createTime), "DATE_FORMAT(create_time,'%y-%m-%d')="+"'"+createTime+"'").and(StringUtils.isNotBlank(sendId),"create_by="+send).and(StringUtils.isNotBlank(receiveId),"id in (select finance_id from tb_finance_people where user_id ="+receive+" group by finance_id)").and("type="+type).orderBy("create_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<FinanceManagementEntity>().like(StringUtils.isNotBlank(title), "title", title).and(StringUtils.isNotBlank(createTime), "DATE_FORMAT(create_time,'%y-%m-%d')="+"'"+createTime+"'").and(StringUtils.isNotBlank(sendId),"create_by="+send).and(StringUtils.isNotBlank(receiveId),"id in (select finance_id from tb_finance_people where user_id ="+receive+" group by finance_id)").and("type="+type).orderBy("create_time DESC")));
        return new PageUtils(page);
    }

	@Override
	public FinanceManagementEntity selectFinanceById(String id) {
		return financeManagementDao.selectFinanceById(id);
	}

	@Override
	public void save(FinanceManagementEntity financeManagement) {
		financeManagementDao.save(financeManagement);
	}

}