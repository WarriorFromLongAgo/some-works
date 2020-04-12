package com.orhonit.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.dao.TaDepartmentMemberDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;
import com.orhonit.modules.sys.service.TaDepartmentMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("taDepartmentMemberService")
public class TaDepartmentMemberServiceImpl extends ServiceImpl<TaDepartmentMemberDao, TaDepartmentMemberEntity> implements TaDepartmentMemberService {

	@Autowired
	private TaDepartmentMemberDao taDepartmentMemberDao;
	@Autowired
	private SysUserDao sysUserDao;
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String lowerName = (String)params.get("lowerName");
    	Integer deptId = (Integer)params.get("deptId");
        Page<TaDepartmentMemberEntity> page = this.selectPage(
                new Query<TaDepartmentMemberEntity>(params).getPage(),
                new EntityWrapper<TaDepartmentMemberEntity>().like(StringUtils.isNotBlank(lowerName),"lower_name", lowerName).and("dept_id", deptId).orderBy("the_sorting asc")
        );
        page.setTotal(this.selectCount(new EntityWrapper<TaDepartmentMemberEntity>().like(StringUtils.isNotBlank(lowerName),"lower_name", lowerName).and("dept_id", deptId)));
        return new PageUtils(page);
    }


	@Override
	public List<TaDepartmentMemberEntity> selecLowerList(Integer userDept) {
		return taDepartmentMemberDao.selecLowerList(userDept);
	}

	@Override
	public PageUtils queryPageDepart(Map<String, Object> params) {
		Page<TaDepartmentMemberEntity> page = this.selectPage(
				new Query<TaDepartmentMemberEntity>(params).getPage(),
				new EntityWrapper<TaDepartmentMemberEntity>()
		);
		page.setTotal(this.selectCount(new EntityWrapper<TaDepartmentMemberEntity>()));
		return new PageUtils(page);
	}

	@Override
	public List<TaDepartmentMemberEntity> selectLowerUserList() {
		List<TaDepartmentMemberEntity> lowerList = taDepartmentMemberDao.selectList(new EntityWrapper<TaDepartmentMemberEntity>());
		if(lowerList.size() > 0){
			for (TaDepartmentMemberEntity taDepartmentMemberEntity:lowerList) {
				List<SysUserEntity> userList = sysUserDao.selectList(new EntityWrapper<SysUserEntity>().and("(is_public = 1 or is_public = 3)").and("lower_id ="+taDepartmentMemberEntity.getLowerId()));
				taDepartmentMemberEntity.setUserList(userList);
			}
		}
		return lowerList;
	}
}