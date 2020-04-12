package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.service.WorkPlanVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/plan")
public class WorkPlanVoController {

    @Autowired
    private WorkPlanVoService workPlanVoService;

    /**
     * 根据主键id查询所有数据
     * @param params
     * @return
     */
    @RequestMapping("/findAllData")
    public R findAllData(@RequestParam Map<String,Object> params){
        return R.ok().put("page", workPlanVoService.findAllData(params));
    }
}
