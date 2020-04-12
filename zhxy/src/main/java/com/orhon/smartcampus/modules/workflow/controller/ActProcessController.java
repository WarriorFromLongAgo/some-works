package com.orhon.smartcampus.modules.workflow.controller;


import com.orhon.smartcampus.modules.workflow.component.ActivitiPage;
import com.orhon.smartcampus.modules.workflow.service.ActivitiProcessService;
import com.orhon.smartcampus.utils.R;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActProcessController {


    @Autowired
    private ActivitiProcessService activitiProcessService;

    @RequestMapping("/act/process/definitions")
    public R getProcessDefinitionList(@RequestParam(value = "category" , defaultValue = "") String category){
        ActivitiPage<ProcessDefinition> list = this.activitiProcessService.getList(new ActivitiPage<ProcessDefinition>() , category);
        return R.ok().put("data" , list.getList());
    }

    @RequestMapping("/act/process/instances")
    public R getProcessInstancesList(
            @RequestParam(value = "instance_id" , defaultValue = "") String processInstanceId,
            @RequestParam(value = "definition_key" , defaultValue = "") String processDefinitionKey
    ){
        //ActivitiPage<ProcessDefinition> list = this.activitiProcessService.getList(new ActivitiPage<ProcessDefinition>() , category);
        ActivitiPage<ProcessInstance> list = this.activitiProcessService.getProcessInstanceList(
                new ActivitiPage<ProcessInstance>(),
                processInstanceId,
                processDefinitionKey
        );
        return R.ok().put("data" , list.getList());
    }


}
