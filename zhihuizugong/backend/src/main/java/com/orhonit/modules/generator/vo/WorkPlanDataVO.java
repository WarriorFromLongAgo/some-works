package com.orhonit.modules.generator.vo;

import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import com.orhonit.modules.generator.entity.ZgWorkScheduleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkPlanDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<WorkPlanEntity> planList;

    private List<ZgWorkScheduleEntity> scheduleList;

    private List<ZgRemarkEntity> remarkList;

    private List<ZgPlanFileEntity> fileList;
}
