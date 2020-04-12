package com.orhon.smartcampus.modules.workflow.controller;


import com.orhon.smartcampus.utils.R;
import lombok.extern.slf4j.Slf4j;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class ActTestController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    //部署一个流程
    @GetMapping("/test2/deploy")
    public R deploy(){
        Deployment deployment = this.repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("workflow/test1.bpmn")
                .deploy();
        return R.ok().put("部署Id" , deployment.getId())
                .put("部署名称" , deployment.getName());
    }

    /**
     * {
     * 	"msg": "success",
     * 	"code": 200,
     * 	"部署Id": "2501",
     * 	"部署名称": "请假流程"
     * }
     */


    //启动一个流程
    @GetMapping("/test2/start")
    public R start(
            @RequestParam(value = "userkey" , defaultValue = "") String userKey,
            @RequestParam(value = "day" , defaultValue = "") Integer day,
            @RequestParam(value = "users" , defaultValue = "") String users
    ){
        //String userKey="PTM";//脑补一下这个是从前台传过来的数据
        String processDefinitionKey ="myProcess";//每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的
        HashMap<String, Object> variables=new HashMap<>();
        variables.put("userKey", userKey);//userKey在上文的流程变量中指定了
        variables.put("day", day);
        //variables.put("users", users);

        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey,variables);

        return R.ok()
                .put("流程实例id" , instance.getId() )
                .put("流程定义id" , instance.getDeploymentId());
    }

    /**
     * {
     * 	"msg": "success",
     * 	"code": 200,
     * 	"流程实例id": "5001",
     * 	"流程定义id": {}
     * }
     */


    @GetMapping("/test2/query_person")
    public R query(@RequestParam(value = "pi" , defaultValue = "") String pi){
        List<HashMap<String , Object>> ret = new ArrayList<HashMap<String, Object>>();

        List<Task> list = taskService.createTaskQuery().processInstanceId(pi).list();
        list.forEach(item -> {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("id" , item.getId());
            temp.put("assignee" , item.getAssignee());
            temp.put("createtime" , item.getCreateTime());
            ret.add(temp);
        });

        //Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(“132501”).active().singleResult();


        return R.ok().put("data" , ret);
    }
    /**
     * {
     * 	"msg": "success",
     * 	"code": 200,
     * 	"data": [
     *                {
     * 			"id": "5008",
     * 			"assignee": "PTM",
     * 			"vars": {}
     *        }
     * 	]
     * }
     */

    @GetMapping("/test2/query_group")
    public R queryGroup(@RequestParam(value = "group" , defaultValue = "") String group){
        List<HashMap<String , Object>> ret = new ArrayList<HashMap<String, Object>>();

        List<Task> list = taskService.createTaskQuery().taskCandidateGroup("HR").list();
        list.forEach(item -> {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("id" , item.getId());
            temp.put("assignee" , item.getAssignee());
            temp.put("createtime" , item.getCreateTime());
            ret.add(temp);
        });
        return R.ok().put("data" , ret);
    }

    @GetMapping("/test2/complete")
    public R completeMission(@RequestParam(value = "taskid" , defaultValue = "") String taskid){
        HashMap<String, Object> variables=new HashMap<>();
        variables.put("days", 1);//userKey在上文的流程变量中指定了
        taskService.complete(taskid,variables);
        return R.ok().put("data" , "工作流完成了");

    }


    @GetMapping("/test2/log")
    public R log(){
        log.warn("hahahahaha");
        return R.ok();
    }
    
    
    @Autowired
    private ProcessEngine processEngine;
    //查询流程定义列表
    @GetMapping("/test2/querylist")
    public R querylist() {	
    	ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
    	query.orderByProcessDefinitionVersion().desc();
    	query.listPage(0, 10);
    	List<ProcessDefinition> list = query.list();
    	for (ProcessDefinition pd : list) {
			System.out.println(pd.getName()+"========"+pd.getId());
		}
    	return null;
		
	}
    
    @GetMapping("/test2/delLiucheng")
    public R del() {	
    	// 获取仓库服务对象
    	 RepositoryService repositoryService =processEngine.getRepositoryService();
    	 // 删除发布信息
    	 String deploymentId = "1";
    	 // 普通删除，如果当前规则下有正在执行的流程，则抛异常
    	 repositoryService.deleteDeployment(deploymentId);
    	 // 级联删除,会删除和当前规则相关的所有信息，包括历史
    	 repositoryService.deleteDeployment(deploymentId, true);
		return null;
		
	}




    //15001 yongsheng
    //17501 huihui
}
