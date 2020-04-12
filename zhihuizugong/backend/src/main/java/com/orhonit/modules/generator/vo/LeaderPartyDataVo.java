package com.orhonit.modules.generator.vo;

import com.orhonit.modules.generator.entity.SysUserExtendEntity;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;
import com.orhonit.modules.generator.entity.ZgNearlyThreeYearsEntity;
import com.orhonit.modules.generator.entity.ZgTaskEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class LeaderPartyDataVo implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户扩展表情况
    private SysUserExtendEntity sysUserExtendEntity;
    //近三年考核,奖惩
    private List<ZgNearlyThreeYearsEntity> yearList;
    //任务列表
    private List<ZgTaskEntity> taskEntity;
    //用户主要家庭成员
    private List<ZgFamilyMemberEntity> familyEntity;
    //承诺践诺/工作任务统计
    private  Map<String,Object> workStatistics;
    //处罚情况(从联席单位获取)
    private List<Object> punishData;

    private Map<String,Object> studyData;

}
