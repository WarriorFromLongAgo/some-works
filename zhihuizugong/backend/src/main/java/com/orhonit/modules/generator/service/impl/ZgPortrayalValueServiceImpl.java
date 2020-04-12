package com.orhonit.modules.generator.service.impl;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.dao.ZgPortrayalDao;
import com.orhonit.modules.generator.dao.ZgPortrayalValueDao;
import com.orhonit.modules.generator.entity.ZgDefaultScoreDeptEntity;
import com.orhonit.modules.generator.entity.ZgDefaultScoreEntity;
import com.orhonit.modules.generator.entity.ZgDefaultThirteenEntity;
import com.orhonit.modules.generator.entity.ZgPortrayalValueEntity;
import com.orhonit.modules.generator.service.ZgDefaultScoreDeptService;
import com.orhonit.modules.generator.service.ZgDefaultScoreService;
import com.orhonit.modules.generator.service.ZgDefaultThirteenService;
import com.orhonit.modules.generator.service.ZgPortrayalValueService;
import com.orhonit.modules.generator.vo.ZgPortrayalValueVo;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZgPortrayalValueServiceImpl implements ZgPortrayalValueService {

    @Autowired
    private ZgPortrayalValueDao zgPortrayalValueDao;

    @Autowired
    private ZgPortrayalDao zgPortrayalDao;

    @Autowired
    private ZgDefaultScoreService zgDefaultScoreService;

    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    @Autowired
    private ZgDefaultScoreDeptService zgDefaultScoreDeptService;

    @Autowired
    private ZgDefaultThirteenService zgDefaultThirteenService;

    @Override
    public Map<String, List<ZgPortrayalValueEntity>> findPortrayal(Long userId) {
        Map<String,Object> params = new HashMap<>();
        List<ZgPortrayalValueEntity> valueList = new ArrayList<>();
        ZgPortrayalValueEntity newPortData = findNewPortData(userId);
        params.put("userId",userId);
        PageUtils page = zgDefaultScoreService.queryPage(params);
        List<ZgDefaultScoreEntity> list = (List<ZgDefaultScoreEntity>) page.getList();
        if (list != null && list.size() > 0){
            ZgPortrayalValueEntity zgPortrayalValueEntity = new ZgPortrayalValueEntity();
            zgPortrayalValueEntity.setUserId(userId);
            SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(userId);
            zgPortrayalValueEntity.setLowerId(userInfo.getLowerId());
            zgPortrayalValueEntity.setLowerName(userInfo.getLowerName());
            zgPortrayalValueEntity.setUserName(userInfo.getUserTrueName());
            Integer innovateRank = list.get(0).getInnovateDefault()+newPortData.getInnovateRank();
            zgPortrayalValueEntity.setInnovateRank(innovateRank);
            Integer executeRank = list.get(0).getExecuteDefault()+newPortData.getExecuteRank();
            zgPortrayalValueEntity.setExecuteRank(executeRank);
            Integer thinkRank = list.get(0).getThinkDefault()+newPortData.getThinkRank();
            zgPortrayalValueEntity.setThinkRank(thinkRank);
            Integer studyRank = list.get(0).getStudyDefault()+newPortData.getStudyRank();
            zgPortrayalValueEntity.setStudyRank(studyRank);
            Integer synergyRank = list.get(0).getSynergyDefault()+newPortData.getSynergyRank();
            zgPortrayalValueEntity.setSynergyRank(synergyRank);
            Integer serveRank = list.get(0).getServeDefault()+newPortData.getServeRank();
            zgPortrayalValueEntity.setServeRank(serveRank);
            valueList.add(zgPortrayalValueEntity);
        }
        Map<String,List<ZgPortrayalValueEntity>> map = new HashMap<>();
        map.put("results",valueList);
        return map;
    }

    @Override
    public Map<String, List<ZgPortrayalValueEntity>> findPortrayalDept(Integer lowerId) {
        ZgPortrayalValueEntity newPortDeptData = findNewPortDeptData(lowerId);
        List<ZgPortrayalValueEntity> valueList = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        params.put("lowerId",lowerId);
        PageUtils pageUtils = zgDefaultScoreDeptService.queryPage(params);
        List<ZgDefaultScoreDeptEntity> list = (List<ZgDefaultScoreDeptEntity>) pageUtils.getList();
        TaDepartmentMemberEntity entity = zgDefaultScoreDeptService.findLowerName(lowerId);
        if (list != null && list.size() > 0){
            ZgPortrayalValueEntity zgPortrayalValueEntity = new ZgPortrayalValueEntity();
            zgPortrayalValueEntity.setLowerId(lowerId);
            zgPortrayalValueEntity.setLowerName(entity.getLowerName());
            zgPortrayalValueEntity.setInnovateRank(list.get(0).getInnovateDefault()+newPortDeptData.getInnovateRank());
            zgPortrayalValueEntity.setExecuteRank(list.get(0).getExecuteDefault()+newPortDeptData.getExecuteRank());
            zgPortrayalValueEntity.setThinkRank(list.get(0).getThinkDefault()+newPortDeptData.getThinkRank());
            zgPortrayalValueEntity.setStudyRank(list.get(0).getStudyDefault()+newPortDeptData.getStudyRank());
            zgPortrayalValueEntity.setSynergyRank(list.get(0).getSynergyDefault()+newPortDeptData.getSynergyRank());
            zgPortrayalValueEntity.setServeRank(list.get(0).getServeDefault()+newPortDeptData.getServeRank());
            valueList.add(zgPortrayalValueEntity);
        }
        Map<String,List<ZgPortrayalValueEntity>> map = new HashMap<>();
        map.put("results",valueList);
        return map;
    }

    @Override
    public Map<String, List<ZgPortrayalValueVo>> findPortrayalSelfThirteen(Long userId) {
        ZgPortrayalValueVo newPortrayalSelfThirteenData = findNewPortrayalSelfThirteenData(userId);
        List<ZgPortrayalValueVo> valueList = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(userId);
        params.put("userId",userId);
        PageUtils pageUtils = zgDefaultThirteenService.queryPage(params);
        List<ZgDefaultThirteenEntity> list = (List<ZgDefaultThirteenEntity>) pageUtils.getList();
        if (list != null && list.size() > 0){
            ZgPortrayalValueVo zgPortrayalValueVo = new ZgPortrayalValueVo();
            zgPortrayalValueVo.setAnswer(list.get(0).getAnswer()+newPortrayalSelfThirteenData.getAnswer());
            zgPortrayalValueVo.setWorkIdea(list.get(0).getWorkIdea()+newPortrayalSelfThirteenData.getWorkIdea());
            zgPortrayalValueVo.setStudy(list.get(0).getStudy()+newPortrayalSelfThirteenData.getStudy());
            zgPortrayalValueVo.setFeature(list.get(0).getFeature()+newPortrayalSelfThirteenData.getFeature());
            zgPortrayalValueVo.setThinkNote(list.get(0).getThinkNote()+newPortrayalSelfThirteenData.getThinkNote());
            zgPortrayalValueVo.setLightspot(list.get(0).getLightspot()+newPortrayalSelfThirteenData.getLightspot());
            zgPortrayalValueVo.setResumption(list.get(0).getResumption()+newPortrayalSelfThirteenData.getResumption());
            zgPortrayalValueVo.setDonate(list.get(0).getDonate()+newPortrayalSelfThirteenData.getDonate());
            zgPortrayalValueVo.setHelp(list.get(0).getHelp()+newPortrayalSelfThirteenData.getHelp());
            zgPortrayalValueVo.setVolunteer(list.get(0).getVolunteer()+newPortrayalSelfThirteenData.getVolunteer());
            zgPortrayalValueVo.setOrgLive(list.get(0).getOrgLive()+newPortrayalSelfThirteenData.getOrgLive());
            zgPortrayalValueVo.setShare(list.get(0).getShare()+newPortrayalSelfThirteenData.getShare());
            zgPortrayalValueVo.setLowerId(userInfo.getLowerId());
            zgPortrayalValueVo.setUserId(userInfo.getUserId());
            zgPortrayalValueVo.setUserName(userInfo.getUserTrueName());
            zgPortrayalValueVo.setLowerName(userInfo.getLowerName());
            valueList.add(zgPortrayalValueVo);
        }
        Map<String,List<ZgPortrayalValueVo>> map = new HashMap<>();
        map.put("results",valueList);
        return map;
    }

    public ZgPortrayalValueEntity findNewPortData(Long userId){
        ZgPortrayalValueEntity zgPortrayalValueEntity = new ZgPortrayalValueEntity();
        //个人创新力(组工之效的创新工作所发条数)
        Integer innovateRank = zgPortrayalDao.findInRank(userId);
        zgPortrayalValueEntity.setInnovateRank(innovateRank);
        //个人执行力(组工之本已完成任务数量)
        Integer executeRank = zgPortrayalDao.findExRank(userId);
        zgPortrayalValueEntity.setExecuteRank(executeRank);
        //个人思考力(悟一悟所发条数)
        Integer thinkRank = zgPortrayalDao.findThRank(userId);
        zgPortrayalValueEntity.setThinkRank(thinkRank);
        //个人学习力(组工讲堂,组工书苑所发条数)
        Integer studyRank = zgPortrayalDao.findStRank(userId);
        zgPortrayalValueEntity.setStudyRank(studyRank);
        //个人协同力(组工交流所发条数)
        Integer synergyRank = zgPortrayalDao.findSyRank(userId);
        zgPortrayalValueEntity.setSynergyRank(synergyRank);
        //个人服务力(党群连心桥里的次数)
        Integer serveRank = zgPortrayalDao.findSeRank(userId);
        zgPortrayalValueEntity.setServeRank(serveRank);
        return zgPortrayalValueEntity;
    }

    public ZgPortrayalValueEntity findNewPortDeptData(Integer lowerId){
        ZgPortrayalValueEntity zgPortrayalValueEntity = new ZgPortrayalValueEntity();
        //科室创新力(组工之效的创新工作所发条数)
        Integer innovateRank = zgPortrayalDao.findInDeptRank(lowerId);
        zgPortrayalValueEntity.setInnovateRank(innovateRank);
        //科室执行力(组工之本已完成任务数量)
        Integer executeRank = zgPortrayalDao.findExDeptRank(lowerId);
        zgPortrayalValueEntity.setExecuteRank(executeRank);
        //科室思考力(悟一悟所发条数)
        Integer thinkRank = zgPortrayalDao.findThDeptRank(lowerId);
        zgPortrayalValueEntity.setThinkRank(thinkRank);
        //科室学习力(组工讲堂,组工书苑所发条数)
        Integer studyRank = zgPortrayalDao.findStDeptRank(lowerId);
        zgPortrayalValueEntity.setStudyRank(studyRank);
        //科室协同力(组工交流所发条数)
        Integer synergyRank = zgPortrayalDao.findSyDeptRank(lowerId);
        zgPortrayalValueEntity.setSynergyRank(synergyRank);
        //科室服务力(党群连心桥里的次数)
        Integer serveRank = zgPortrayalDao.findSeDeptRank(lowerId);
        zgPortrayalValueEntity.setServeRank(serveRank);
        return zgPortrayalValueEntity;
    }
    public ZgPortrayalValueVo findNewPortrayalSelfThirteenData(Long userId){
        ZgPortrayalValueVo zgPortrayalValueVo = new ZgPortrayalValueVo();
        //答题考试
        Integer findAnswer = zgPortrayalValueDao.findAnswer(userId);
        zgPortrayalValueVo.setAnswer(findAnswer);
        //工作点子
        Integer findWorkIdea = zgPortrayalValueDao.findWorkIdea(userId);
        zgPortrayalValueVo.setWorkIdea(findWorkIdea);
        //成果分享
        Integer findShare = zgPortrayalValueDao.findShare(userId);
        zgPortrayalValueVo.setShare(findShare);
        //组织生活
        Integer findOrgLive = zgPortrayalValueDao.findOrgLive(userId);
        zgPortrayalValueVo.setOrgLive(findOrgLive);
        //包联帮扶
        Integer findHelp = zgPortrayalValueDao.findHelp(userId);
        zgPortrayalValueVo.setHelp(findHelp);
        //志愿服务
        Integer findVolunteer = zgPortrayalValueDao.findVolunteer(userId);
        zgPortrayalValueVo.setVolunteer(findVolunteer);
        //爱心捐助
        Integer findDonate = zgPortrayalValueDao.findDonate(userId);
        zgPortrayalValueVo.setDonate(findDonate);
        //履职尽责
        Integer findResumption = zgPortrayalValueDao.findResumption(userId);
        zgPortrayalValueVo.setResumption(findResumption);
        //亮点工作
        Integer findLightspot = zgPortrayalValueDao.findLightspot(userId);
        zgPortrayalValueVo.setLightspot(findLightspot);
        //特色工作
        Integer findFeature = zgPortrayalValueDao.findFeature(userId);
        zgPortrayalValueVo.setFeature(findFeature);
        //理论学习
        Integer findStudy = zgPortrayalValueDao.findStudy(userId);
        zgPortrayalValueVo.setStudy(findStudy);
        //思悟笔记
        Integer findThinkNote = zgPortrayalValueDao.findThinkNote(userId);
        zgPortrayalValueVo.setThinkNote(findThinkNote);
        return zgPortrayalValueVo;
    }

}
