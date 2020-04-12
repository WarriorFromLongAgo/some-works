package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.dao.ZgMeetFlowDao;
import com.orhonit.modules.generator.entity.ZgMeetFlowEntity;
import com.orhonit.modules.generator.service.ZgMeetFlowService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgMeetFlowService")
public class ZgMeetFlowServiceImpl extends ServiceImpl<ZgMeetFlowDao, ZgMeetFlowEntity> implements ZgMeetFlowService {

    @Autowired
    private ZgMeetFlowDao zgMeetFlowDao;
    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("meetId") != null){
            String meetId = params.get("meetId").toString();
            Page<ZgMeetFlowEntity> page = this.selectPage(
                    new Query<ZgMeetFlowEntity>(params).getPage(),
                    new EntityWrapper<ZgMeetFlowEntity>().where("meet_id = " + "'"+meetId+"'")
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgMeetFlowEntity>().where("meet_id = " + "'"+meetId+"'")));
            return new PageUtils(page);
        }else {
            Page<ZgMeetFlowEntity> page = this.selectPage(
                    new Query<ZgMeetFlowEntity>(params).getPage(),
                    new EntityWrapper<ZgMeetFlowEntity>()
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgMeetFlowEntity>()));
            return new PageUtils(page);
        }

    }

    @Override
    public void save(ZgMeetFlowEntity zgMeetFlow) {
        List<ZgMeetFlowEntity> list = zgMeetFlowDao.findFlow(zgMeetFlow.getMeetId());
        if (list != null && list.size() >0){
            zgMeetFlow.setId(list.get(0).getId());
            SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getCreateUserId());
            zgMeetFlow.setCreateUserName(userInfo.getUserTrueName());
            SysUserEntity userInfo1 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getInspectorWorkable());
            zgMeetFlow.setInspectorWorkableName(userInfo1.getUserTrueName());
            SysUserEntity userInfo2 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getLogistics());
            zgMeetFlow.setLogisticsName(userInfo2.getUserTrueName());
            SysUserEntity userInfo3 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMakeInform());
            zgMeetFlow.setMakeInformName(userInfo3.getUserTrueName());
            SysUserEntity userInfo4 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMakePlan());
            zgMeetFlow.setMakePlanName(userInfo4.getUserTrueName());
            SysUserEntity userInfo5 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetFeedback());
            zgMeetFlow.setMeetFeedbackName(userInfo5.getUserTrueName());
            SysUserEntity userInfo6 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetMinutes());
            zgMeetFlow.setMeetMinutesName(userInfo6.getUserTrueName());
            SysUserEntity userInfo7 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetRecord());
            zgMeetFlow.setMeetRecordName(userInfo7.getUserTrueName());
            SysUserEntity userInfo8 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetReports());
            zgMeetFlow.setMeetReportsName(userInfo8.getUserTrueName());
            SysUserEntity userInfo9 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetRoomDecorate());
            zgMeetFlow.setMeetRoomDecorateName(userInfo9.getUserTrueName());
            SysUserEntity userInfo10 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getMeetServe());
            zgMeetFlow.setMeetServeName(userInfo10.getUserTrueName());
            SysUserEntity userInfo11 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getReadyMaterial());
            zgMeetFlow.setReadyMaterialName(userInfo11.getUserTrueName());
            SysUserEntity userInfo12 = zgDefaultScoreDao.findUserInfo(zgMeetFlow.getSortingData());
            zgMeetFlow.setSortingDataName(userInfo12.getUserTrueName());
            zgMeetFlowDao.updateById(zgMeetFlow);
        }else {
            zgMeetFlow.setCreateTime(new Date());
            zgMeetFlow.setId(UUID.randomUUID().toString().replace("-",""));
            zgMeetFlowDao.save(zgMeetFlow);
        }
    }

}