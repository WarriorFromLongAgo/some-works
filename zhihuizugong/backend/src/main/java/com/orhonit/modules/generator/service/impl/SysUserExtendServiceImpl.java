package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.dao.SysUserExtendDao;
import com.orhonit.modules.generator.entity.SysUserExtendEntity;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;
import com.orhonit.modules.generator.entity.ZgNearlyThreeYearsEntity;
import com.orhonit.modules.generator.entity.ZgTaskEntity;
import com.orhonit.modules.generator.service.SysUserExtendService;
import com.orhonit.modules.generator.service.ZgTaskService;
import com.orhonit.modules.generator.vo.CadreVO;
import com.orhonit.modules.generator.vo.LeaderPartyDataVo;
import com.orhonit.modules.generator.vo.PartyVO;
import com.orhonit.modules.sys.dao.UserDeptDao;
import com.orhonit.modules.sys.dao.UserOrgDao;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.UserDeptEntity;
import com.orhonit.modules.sys.entity.UserOrgEntity;
import com.orhonit.modules.sys.service.StudyGradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service("sysUserExtendService")
public class SysUserExtendServiceImpl extends ServiceImpl<SysUserExtendDao, SysUserExtendEntity> implements SysUserExtendService {


    @Autowired
    private SysUserExtendDao sysUserExtendDao;

    @Autowired
    private UserOrgDao userOrgDao;

    @Autowired
    private StudyGradeService gradeService;

    @Autowired
    private ZgTaskService zgTaskService;

    @Autowired
    private UserDeptDao userDeptgDao;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String type = ((String)params.get("type"));
        if (params.get("userId") != null){
            Long userId = Long.parseLong((String)params.get("userId"));
            Page<SysUserExtendEntity> page = this.selectPage(
                    new Query<SysUserExtendEntity>(params).getPage(),
                    new EntityWrapper<SysUserExtendEntity>().where("user_id = "+userId).and("type ="+"'"+type+"'")
            );
            page.setTotal(this.selectCount(new EntityWrapper<SysUserExtendEntity>().where("user_id = "+userId).and("type = "+"'"+type+"'")));
            return new PageUtils(page);
        }else {
            Page<SysUserExtendEntity> page = this.selectPage(
                    new Query<SysUserExtendEntity>(params).getPage(),
                    new EntityWrapper<SysUserExtendEntity>().where("type = "+"'"+type+"'")
            );
            page.setTotal(this.selectCount(new EntityWrapper<SysUserExtendEntity>().where("type = "+"'"+type+"'")));
            return new PageUtils(page);
        }
    }

    @Override
    public void save(SysUserExtendEntity sysUserExtendEntity) {
        List<SysUserExtendEntity> exUser = sysUserExtendDao.findExUser(sysUserExtendEntity.getUserId());
        List<SysUserEntity> list = sysUserExtendDao.findUserInfo(sysUserExtendEntity.getUserId());
        if (list != null && list.size() > 0){
            sysUserExtendEntity.setUserNickname(list.get(0).getUserNickname());
        }
        if (exUser != null && exUser.size() > 0){
            sysUserExtendEntity.setId(exUser.get(0).getId());
            sysUserExtendDao.updateById(sysUserExtendEntity);
        }else {
            sysUserExtendEntity.setCreateTime(new Date());
            sysUserExtendDao.insert(sysUserExtendEntity);
        }
    }

    @Override
    public Map<String, Object> findUser(String idCard) {
        Map<String,Object> map = new HashMap<>();
        List<SysUserEntity> user = sysUserExtendDao.findUser(idCard);
        if (user != null && user.size() > 0){
            map.put("user",user.get(0));
            List<SysUserExtendEntity> exUser = sysUserExtendDao.findExUser(user.get(0).getUserId());
            if (exUser !=null && exUser.size() > 0){
                map.put("exUser",exUser.get(0));
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> findLeaderUserList(Map<String, Object> params) {
        Integer orgType = Integer.parseInt((String)params.get("orgType"));
        Integer isPublic = null;
        if (params.get("isPublic") != null){
            isPublic = Integer.parseInt((String)params.get("isPublic"));
        }
        List<CadreVO> voList = new ArrayList<>();
        if (params.get("orgId") != null){
            CadreVO cadreVO = new CadreVO();
            Integer orgId = Integer.parseInt((String)params.get("orgId"));
            UserOrgEntity userOrgEntity = userOrgDao.selectById(orgId);
            if (userOrgEntity != null){
                cadreVO.setOrgContent(userOrgEntity.getOrgContent());
            }
            List<SysUserEntity> leaderList = new ArrayList<>();
            List<SysUserEntity> partyList = new ArrayList<>();
            selectUser(orgId,leaderList,partyList,isPublic);
            cadreVO.setLeaderUserList(leaderList);
            cadreVO.setPartyUserList(partyList);
            List<UserOrgEntity> orgList = new ArrayList<>();
            cadreVO.setOrgList(orgList);
            voList.add(cadreVO);
        }else {
            CadreVO cadreVO = new CadreVO();
            List<SysUserEntity> leaderList = new ArrayList<>();
            List<SysUserEntity> partyList = new ArrayList<>();
            List<UserOrgEntity> orgList = sysUserExtendDao.findOrgList(orgType);
            cadreVO.setOrgList(orgList);
       /*     if (orgList != null && orgList.size() > 0){
                for (UserOrgEntity userOrgEntity : orgList) {
                    selectUser(userOrgEntity.getOrgId(),leaderList,partyList,isPublic);
                }
            }*/
            cadreVO.setLeaderUserList(leaderList);
            cadreVO.setPartyUserList(partyList);
            voList.add(cadreVO);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("results",voList);
        return map;
    }

    @Override
    public Map<String, Object> findPartyUserList(Map<String, Object> params) {
        Integer deptOneId = Integer.parseInt((String)params.get("deptOneId"));
        Integer isPublic = null;
        if (params.get("isPublic") != null){
            isPublic = Integer.parseInt((String)params.get("isPublic"));
        }
        List<PartyVO> voList = new ArrayList<>();
        if (params.get("deptTwoId") != null){
            Integer deptTwoId = Integer.parseInt((String)params.get("deptTwoId"));
            PartyVO partyVO = new PartyVO();
            List<SysUserEntity> leaderList = new ArrayList<>();
            List<SysUserEntity> partyList = new ArrayList<>();
            selectPartyUser(deptTwoId,leaderList,partyList,isPublic);
            partyVO.setPartyUserList(partyList);
            partyVO.setLeaderUserList(leaderList);
            voList.add(partyVO);
        }else {
            PartyVO partyVO = new PartyVO();
            //二级下拉框
            List<UserDeptEntity> deptList = sysUserExtendDao.findDeptList(deptOneId);
            List<SysUserEntity> leaderList = new ArrayList<>();
            List<SysUserEntity> partyList = new ArrayList<>();
            //selectPartyUser(deptOneId,leaderList,partyList,isPublic);
            partyVO.setDeptList(deptList);
            partyVO.setLeaderUserList(leaderList);
            partyVO.setPartyUserList(partyList);
            voList.add(partyVO);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("results",voList);
        return map;
    }

    @Override
    public Map<String, Object> findPartyOneList() {
        List<UserDeptEntity> deptList = sysUserExtendDao.findOneDeptList();
        Map<String,Object> map = new HashMap<>();
        map.put("results",deptList);
        return map;
    }

    @Override
    public Map<String, Object> findAllData(Map<String, Object> param) {
        List<LeaderPartyDataVo> voList = new ArrayList<>();
        LeaderPartyDataVo leaderPartyDataVo = new LeaderPartyDataVo();
        String type = (String)param.get("type");
        Object userId = param.get("userId");
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        SysUserExtendEntity sysUserExtendEntity = new SysUserExtendEntity();
        List<SysUserExtendEntity> extendEntity = sysUserExtendDao.findExtend(params);
        Long userIddd = Long.parseLong((String)params.get("userId"));
        List<SysUserEntity> usEntity = sysUserExtendDao.findUserInfo(userIddd);
        if (extendEntity != null && extendEntity.size() > 0){
            sysUserExtendEntity = extendEntity.get(0);
            BeanUtils.copyProperties(usEntity.get(0),sysUserExtendEntity);
        }else {
            BeanUtils.copyProperties(usEntity.get(0),sysUserExtendEntity);
            sysUserExtendDao.insert(sysUserExtendEntity);
            List<SysUserExtendEntity> exUser = sysUserExtendDao.findExUser(userIddd);
            sysUserExtendEntity = exUser.get(0);
        }
        if (sysUserExtendEntity != null ){
            leaderPartyDataVo.setSysUserExtendEntity(sysUserExtendEntity);
        }
        params.clear();
        Long userIdd = Long.parseLong(userId.toString());
        List<SysUserEntity> userInfo = sysUserExtendDao.findUserInfo(userIdd);
        params.put("userNickname",userInfo.get(0).getUserNickname());
        List<ZgNearlyThreeYearsEntity> list1 = sysUserExtendDao.findNearThree(params);
        leaderPartyDataVo.setYearList(list1);
        params.clear();
        params.put("userId",userIdd);
        params.put("type",type);
        List<ZgTaskEntity> list2 = sysUserExtendDao.findTask(params);
        Map<String,Object> wMap = sysUserExtendDao.findWorkData(params);
        Map<String,Object> workMap  = new HashMap<>();
        if (wMap != null && wMap.size() > 0){
            workMap.put("workCount",wMap.get("workCount"));
            workMap.put("finishCount",wMap.get("finishCount"));
        }else {
            workMap.put("workCount",0);
            workMap.put("finishCount",0);
        }
        leaderPartyDataVo.setWorkStatistics(workMap);
        params.clear();
        List<ZgTaskEntity> zgTaskFinishList = new ArrayList<>();
        if (list2 != null && list2.size() > 0){
            for (ZgTaskEntity zgTaskEntity : list2) {
                params.put("taskId",zgTaskEntity.getId());
                ZgTaskEntity zgTaskEntity1 = zgTaskService.selectTaskInfo(zgTaskEntity.getId(),"");
                zgTaskFinishList.add(zgTaskEntity1);
                params.clear();
            }

        }
        List<ZgFamilyMemberEntity> familyEntity = sysUserExtendDao.findFamilyEntity(userIddd);
        List<Map<String,Object>> joinEntity = sysUserExtendDao.findPunish(userIddd);
        List<Object> punishList = new ArrayList<>();
        if (joinEntity != null && joinEntity.size() > 0){
            punishList.add(joinEntity);
        }else {
            punishList.add("无");
        }
        StudyGradeEntity studyGradeEntity = new StudyGradeEntity();
        studyGradeEntity.setUserId(userIddd);
        studyGradeEntity.setData(new SimpleDateFormat("yyyy").format(new Date()));
        R complete = gradeService.complete(studyGradeEntity);
        Map<Object,Object> data = (Map<Object, Object>) complete.get("data");
        String xianshang = (String) data.get("xianshang");
        String xianxia = (String) data.get("xianxia");
        Map<String,Object> studyMap = new HashMap<>();
        studyMap.put("xianshang",xianshang);
        studyMap.put("xianxia",xianxia);
        leaderPartyDataVo.setStudyData(studyMap);
        leaderPartyDataVo.setPunishData(punishList);
        leaderPartyDataVo.setFamilyEntity(familyEntity);
        leaderPartyDataVo.setTaskEntity(zgTaskFinishList);
        voList.add(leaderPartyDataVo);
        Map<String,Object> dataList = new HashMap<>();
        dataList.put("results",voList);
        return dataList;
    }

    @Override
    public Map<String,Object> findDeptData() {
        List<Map<String,Object>> deptMap = sysUserExtendDao.findDeptData();
        Map<String,Object> map = new HashMap<>();
        map.put("total",deptMap.size());
        map.put("deptData",deptMap);
        return map;
    }

    @Override
    public Map<String, Object> findOrgData() {
        List<Map<String,Object>> orgMap = sysUserExtendDao.findOrgData();
        Map<String,Object> map = new HashMap<>();
        map.put("total",orgMap.size());
        map.put("orgData",orgMap);
        return map;
    }

    @Override
    public Map<String, Object> findCommonSelect(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();

        Integer partyOrLeader = null;
        if (params.get("partyOrLeader") != null && !"".equals(params.get("partyOrLeader"))){
            partyOrLeader = Integer.parseInt((String)params.get("partyOrLeader"));
        }
        if (partyOrLeader != null){
            if (partyOrLeader == 1){
                pageUtil(params);
                List<Map<String,Object>> userList = sysUserExtendDao.findPartyUser(params);
                List<Map<String,Object>> userListCount = sysUserExtendDao.findPartyUser(params);
                List<Map<String,Object>> deptMap = sysUserExtendDao.findCommonSelect(params);
                map.put("userTotal",userListCount.size());
                map.put("userList",userList);
                map.put("deptFuList",deptMap);
            }else if (partyOrLeader == 2){
                pageUtil(params);
                List<Map<String,Object>> userList = sysUserExtendDao.findOrgUser(params);
                List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgUser(params);
                List<Map<String,Object>> orgMap = sysUserExtendDao.findOrg(params);
                map.put("userTotal",userListCount.size());
                map.put("userList",userList);
                map.put("orgFuList",orgMap);
            }
        }
        if (params.get("deptOneId") != null && !"".equals(params.get("deptOneId"))){
            pageUtil(params);
            List<Map<String,Object>> userList = sysUserExtendDao.findDeptOneUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findDeptOneUser(params);
            List<Map<String,Object>> deptMap = sysUserExtendDao.findDeptZiList(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
            map.put("deptZiList",deptMap);
        }
        if (params.get("orgOneId") != null && !"".equals(params.get("orgOneId"))){
            pageUtil(params);
            List<Map<String,Object>> userList = sysUserExtendDao.findOrgOneUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgOneUser(params);
            List<Map<String,Object>> orgMap = sysUserExtendDao.findOrgZi(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
            map.put("orgZiList",orgMap);
        }
        if (params.get("deptTwoId") != null && !"".equals(params.get("deptTwoId"))){
            pageUtil(params);
            List<Map<String,Object>> userList = sysUserExtendDao.findDeptTwoUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findDeptTwoUser(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
        }
        if (params.get("orgTwoId") != null && !"".equals(params.get("orgTwoId"))){
            pageUtil(params);
            List<Map<String,Object>> userList = sysUserExtendDao.findOrgTwoUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgTwoUser(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
        }

        return map;
    }

    @Override
    public Map<String, Object> findCommonSelectNotPage(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();

        Integer partyOrLeader = null;
        if (params.get("partyOrLeader") != null && !"".equals(params.get("partyOrLeader"))){
            partyOrLeader = Integer.parseInt((String)params.get("partyOrLeader"));
        }
        if (partyOrLeader != null){
            if (partyOrLeader == 1){
                List<Map<String,Object>> userList = sysUserExtendDao.findPartyUser(params);
                List<Map<String,Object>> userListCount = sysUserExtendDao.findPartyUser(params);
                List<Map<String,Object>> deptMap = sysUserExtendDao.findCommonSelect(params);
                map.put("userTotal",userListCount.size());
                map.put("userList",userList);
                map.put("deptFuList",deptMap);
            }else if (partyOrLeader == 2){
                List<Map<String,Object>> userList = sysUserExtendDao.findOrgUser(params);
                List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgUser(params);
                List<Map<String,Object>> orgMap = sysUserExtendDao.findOrg(params);
                map.put("userTotal",userListCount.size());
                map.put("userList",userList);
                map.put("orgFuList",orgMap);
            }
        }
        if (params.get("deptOneId") != null && !"".equals(params.get("deptOneId"))){
            List<Map<String,Object>> userList = sysUserExtendDao.findDeptOneUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findDeptOneUser(params);
            List<Map<String,Object>> deptMap = sysUserExtendDao.findDeptZiList(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
            map.put("deptZiList",deptMap);
        }
        if (params.get("orgOneId") != null && !"".equals(params.get("orgOneId"))){
            List<Map<String,Object>> userList = sysUserExtendDao.findOrgOneUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgOneUser(params);
            List<Map<String,Object>> orgMap = sysUserExtendDao.findOrgZi(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
            map.put("orgZiList",orgMap);
        }
        if (params.get("deptTwoId") != null && !"".equals(params.get("deptTwoId"))){
            List<Map<String,Object>> userList = sysUserExtendDao.findDeptTwoUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findDeptTwoUser(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
        }
        if (params.get("orgTwoId") != null && !"".equals(params.get("orgTwoId"))){
            List<Map<String,Object>> userList = sysUserExtendDao.findOrgTwoUser(params);
            List<Map<String,Object>> userListCount = sysUserExtendDao.findOrgTwoUser(params);
            map.put("userTotal",userListCount.size());
            map.put("userList",userList);
        }

        return map;
    }

    public void pageUtil(Map<String, Object> params){
        Integer page = 1;
        Integer limit = 10;
        if (params.get("page") != null){
            page = Integer.parseInt(params.get("page").toString());
        }
        if (params.get("limit") != null){
            limit = Integer.parseInt(params.get("limit").toString());
        }
        PageHelper.startPage(page,limit);
    }

    public void selectUser(Integer orgId,List<SysUserEntity> leaderList,List<SysUserEntity> partyList, Integer isPublic){
        List<SysUserEntity> userList = sysUserExtendDao.findUserList(orgId,isPublic);
        if (userList != null && userList.size() >0){
            for (SysUserEntity sysUserEntity : userList) {
                if (sysUserEntity.getIsLeadership() !=null && sysUserEntity.getIsLeadership() == 1){
                    leaderList.add(sysUserEntity);
                }
                if (sysUserEntity.getIsPartyMember() != null && sysUserEntity.getIsPartyMember() == 1 && sysUserEntity.getIsLeadership() != 1){
                    partyList.add(sysUserEntity);
                }
            }
        }
    }

    public void selectPartyUser(Integer deptId,List<SysUserEntity> leaderList,List<SysUserEntity> partyList, Integer isPublic){
        //String child = userDeptgDao.selectChild(deptId);
        //List<SysUserEntity> userList = sysUserExtendDao.findPartyList(deptId,isPublic);
        List<SysUserEntity> userList = sysUserExtendDao.findPartyList2(deptId,isPublic);
        if (userList != null && userList.size() > 0){
            for (SysUserEntity sysUserEntity : userList) {
                if (sysUserEntity.getIsLeadership() !=null && sysUserEntity.getIsLeadership() == 1){
                    leaderList.add(sysUserEntity);
                }
                if (sysUserEntity.getIsPartyMember() != null && sysUserEntity.getIsPartyMember() == 1 && sysUserEntity.getIsLeadership() != 1){
                    partyList.add(sysUserEntity);
                }
            }
        }
    }

}