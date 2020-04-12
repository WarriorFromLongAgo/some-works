package com.orhonit.modules.generator.app.controller;

import com.orhonit.modules.generator.service.WorkPlanVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/plan")
public class AppWorkPlanVoController {

    @Autowired
    private WorkPlanVoService workPlanVoService;

    /**
     * 根据主键id查询所有数据
     * @param params
     * @return
     */
    @RequestMapping("/findAllData")
    public Map<String,Object> findAllData(@RequestParam Map<String,Object> params){
        return workPlanVoService.findAllData(params);
    }
}
