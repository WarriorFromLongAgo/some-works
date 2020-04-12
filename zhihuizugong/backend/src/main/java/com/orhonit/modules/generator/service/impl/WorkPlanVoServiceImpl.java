package com.orhonit.modules.generator.service.impl;


import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.dao.WorkPlanDao;
import com.orhonit.modules.generator.dao.ZgWorkScheduleDao;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import com.orhonit.modules.generator.entity.ZgWorkScheduleEntity;
import com.orhonit.modules.generator.service.WorkPlanVoService;
import com.orhonit.modules.generator.service.ZgRemarkService;
import com.orhonit.modules.generator.vo.WorkPlanDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkPlanVoServiceImpl implements WorkPlanVoService {

    @Autowired
    private WorkPlanDao workPlanDao;

    @Autowired
    private ZgWorkScheduleDao zgWorkScheduleDao;

    @Autowired
    private ZgRemarkService zgRemarkService;

    @Override
    public Map<String,Object> findAllData(Map<String, Object> params) {

        Integer page = 1;
        Integer limit = 10;
        if (params.get("page") != null){
            page = Integer.parseInt(params.get("page").toString());
        }
        if (params.get("limit") != null){
            limit = Integer.parseInt(params.get("limit").toString());
        }
        PageHelper.startPage(page,limit);
        List<WorkPlanDataVO> list = new ArrayList<>();
        //params.clear();
        List<WorkPlanEntity> allPlan = workPlanDao.findAllPlanCount(params);
        List<WorkPlanEntity> allPlanSize = workPlanDao.findAllPlanCount(params);
        if (allPlan != null && allPlan.size() > 0){
            for (WorkPlanEntity workPlanEntity : allPlan) {
                WorkPlanDataVO workPlanDataVO = new WorkPlanDataVO();
                params.put("id",workPlanEntity.getId());
                params.put("planId",workPlanEntity.getId());
                List<WorkPlanEntity> planList = workPlanDao.findAllPlanCount(params);
                workPlanDataVO.setPlanList(planList);
                List<ZgWorkScheduleEntity> zgWorkScheduleEntities = zgWorkScheduleDao.queryPageCount(params);
                workPlanDataVO.setScheduleList(zgWorkScheduleEntities);
                PageUtils pageUtils = zgRemarkService.queryPage(params);
                List<ZgRemarkEntity> remarkList = (List<ZgRemarkEntity>) pageUtils.getList();
                workPlanDataVO.setRemarkList(remarkList);
                List<ZgPlanFileEntity> fileList = workPlanDao.findFile(params);
                workPlanDataVO.setFileList(fileList);
                list.add(workPlanDataVO);
                params.clear();
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",allPlanSize.size());
        map.put("currpage",page);
        map.put("results",list);
        return map;
    }
}
