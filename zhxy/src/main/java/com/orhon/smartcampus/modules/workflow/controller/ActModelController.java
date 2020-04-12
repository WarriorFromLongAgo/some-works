package com.orhon.smartcampus.modules.workflow.controller;


import com.orhon.smartcampus.modules.workflow.component.ActivitiPage;
import com.orhon.smartcampus.modules.workflow.service.ActivitiModelService;
import com.orhon.smartcampus.utils.R;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActModelController {

    @Autowired
    private ActivitiModelService activitiModelService;

    @RequestMapping("/act/models/list")
    public R getModelList(){
        ActivitiPage<Model> ret = this.activitiModelService.getList(new ActivitiPage<Model>());
        return R.ok().put("data" , ret.getList());
    }


}
