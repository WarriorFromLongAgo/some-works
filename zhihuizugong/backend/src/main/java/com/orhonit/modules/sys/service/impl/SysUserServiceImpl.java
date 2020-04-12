package com.orhonit.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.Constant;
import com.orhonit.common.utils.JiguangPushUtil;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.app.vo.AppTuiSongVo;
import com.orhonit.modules.generator.dao.DocumentDao;
import com.orhonit.modules.generator.dao.ZgMeetInformDao;
import com.orhonit.modules.generator.dao.ZgTaskDao;
import com.orhonit.modules.generator.entity.DocumentEntity;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.entity.ZgTaskEntity;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.dao.UserPhoneNumCardDao;
import com.orhonit.modules.sys.dao.WelcomeNewpDao;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.StudyGradeService;
import com.orhonit.modules.sys.service.SysRoleService;
import com.orhonit.modules.sys.service.SysUserRoleService;
import com.orhonit.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.orhonit.common.utils.ShiroUtils.getUserId;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private WelcomeNewpDao welcomeNewpDao;
	
	@Autowired
	private UserPhoneNumCardDao userPhoneNumCardDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private ZgMeetInformDao zgMeetInformDao;
	@Autowired
	private ZgTaskDao zgTaskDao;
	@Autowired
	private DocumentDao documentDao;
    @Autowired
    private StudyGradeService gradeService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
//		Long createUserId = (Long)params.get("createUserId");
		Integer userDept = (Integer)params.get("userDept");

		Page<SysUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username).and(userDept != 0 && userDept != null, "user_dept", userDept)
		);
		page.setTotal(this.selectCount(new EntityWrapper<SysUserEntity>().like(StringUtils.isNotBlank(username),"username", username).and(userDept != 0 && userDept != null, "user_dept", userDept)));
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPageParty(Map<String, Object> params) {
		Integer isPartyMember = Integer.parseInt((String) params.get("isPartyMember"));
		Page<SysUserEntity> page = this.selectPage(
				new Query<SysUserEntity>(params).getPage(),
				new EntityWrapper<SysUserEntity>().and("is_party_member="+isPartyMember)
		);
		page.setTotal(this.selectCount(new EntityWrapper<SysUserEntity>().where("is_party_member="+isPartyMember)));
		return new PageUtils(page);
	}

	@Override
	public List<SysUserEntity> listdept(Integer lowerId) {
		return baseMapper.listdept(lowerId);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId, Integer menuType) {
		return baseMapper.queryAllMenuId(userId,menuType);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.insert(user);
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		long newpUserId= baseMapper.userIdByUserName(user.getUsername());
		int userDept = 0;
		if (user.getUserDept() != null){
			userDept = user.getUserDept();
		}
		AppTuiSongVo appTuiSongVo = new AppTuiSongVo();
		String newTitle="";
		
		//保存欢迎新同志表数据
		welcomeNewpDao.insertSelect(newpUserId,userDept);
		//推送信息发送
		appTuiSongVo.setTypeCode(0);
		if(user.getUserTrueName()!=null){
			newTitle=user.getUserTrueName()+"  同志加入了组织。";
		}else {
			newTitle="新同志加入了组织。";
		}
		JiguangPushUtil.jiguangPush(userPhoneNumCardDao.selectByDeptId(userDept),newTitle,appTuiSongVo);
		
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.deleteBatchIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	/**
	 * 手机号登录
	 * @param mobile
	 * @return
	 */
	@Override
	public SysUserEntity queryByMobile(String mobile) {
		return baseMapper.queryByMobile(mobile);
	}

	/**
	 * 身份证号登录
	 * @param userNickname
	 * @return
	 */
	@Override
	public SysUserEntity queryByIdCard(String userNickname) {
		return baseMapper.queryByIdCard(userNickname);
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	@Override
	public List<SysUserEntity> getUser() {
		return sysUserDao.getUser();
	}
	/**
	 * 模糊搜索用户列表
	 * @author zy
	 * @serialData 2019年7月19日10:12:32
	 */
	@Override
	public PageUtils searchList(Map<String, Object> params) {
		int currPage = 0;
    	int limit = 0;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         //模糊搜索用户列表总条数
         List<SysUserEntity> listSize= sysUserDao.searchListCount(params);
         params.put("page", page);
         params.put("limit", limit);
         Page<Map<String,Object>> infPage = new Page<>(currPage,limit);
         //模糊搜索用户列表
         List<Map<String,Object>> list= sysUserDao.searchList(params);
		 infPage.setTotal(listSize.size());
		 infPage.setRecords(list);
		return new PageUtils(infPage);
	}

	@Override
	public void updateHeadPortrait(String headPortrait, String fileLocalName) {
		sysUserDao.updateHeadPortrait(headPortrait,fileLocalName);
	}

	@Override
	public Map selectAppInfo() {
		Map<String,Object> map = new HashMap<String,Object>();
        SysUserEntity user = sysUserDao.selectById(getUserId());
        //积分
        Integer grade = 0;
        List<StudyGradeEntity> gradeList = gradeService.selectList(new EntityWrapper<StudyGradeEntity>().and("user_id ="+user.getUserId()));
        if(gradeList.size() > 0){
            for (StudyGradeEntity studyGradeEntity:gradeList) {
                if(StringUtils.isNotBlank(studyGradeEntity.getGrade())){
                    grade = grade + Integer.parseInt(studyGradeEntity.getGrade());
                }
            }
        }
        user.setStudyNumber(grade);
        List<ZgTaskEntity> completelist = zgTaskDao.selectCompleteList(user.getUserId());
        user.setTaskNumber(completelist.size());
		map.put("user",user);
		//督办任务列表
		List<ZgTaskEntity> overseeList = zgTaskDao.selectOversee(user.getUserId());
		map.put("overseeList",overseeList);
		map.put("overseeCount",overseeList.size());
		//公文列表
		List<DocumentEntity> documentList = documentDao.selectList(new EntityWrapper<DocumentEntity>()
				.where(user.getUserId() != null,"(user_id = "+user.getUserId()+" or createby = "+user.getUserId()+" or work_id = "+user.getUserId()+" or lead_id="+user.getUserId()+" or minister_id="+user.getUserId()+")")
				.and("remarks = '0'")
		);
		map.put("documentList",documentList);
		map.put("documentCount",documentList.size());
		//会议列表
		List<ZgMeetPeopleEntity> unreadSize= zgMeetInformDao.readMeetInforTotal(user.getUserId());//未读总数
		List<ZgMeetInformEntity> meetList=new ArrayList<ZgMeetInformEntity>();//未读列表
		for(ZgMeetPeopleEntity inform:unreadSize) {
			ZgMeetInformEntity	 entity= zgMeetInformDao.meetList(inform.getMeetId());
			meetList.add(entity);
		}
		map.put("meetList", meetList);
		map.put("readtotal", unreadSize.size());
		//调度列表
		List<ZgTaskEntity> schedulingList = zgTaskDao.selectScheduling(user.getUserId());
		map.put("schedulingList",schedulingList);
		map.put("schedulingCount",schedulingList.size());
		return map;
	}

	@Override
	public List<SysUserEntity> queryMobile(String mobile) {
		return sysUserDao.queryMobile(mobile);
	}
}
