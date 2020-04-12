package com.orhon.smartcampus.modules.workflow.service;


import com.orhon.smartcampus.modules.workflow.component.ActivitiPage;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;


/**
 *
 * 工作流引擎process处理引擎
 *
 */


@Service
public class ActivitiProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;


    /**
     * 获取流程定义列表
     */
    public ActivitiPage<ProcessDefinition> getList(ActivitiPage<ProcessDefinition> page,  String category){
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByProcessDefinitionKey().asc();

        //如果需要分类查询
        if (StringUtils.isNotEmpty(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult() , page.getMaxResults());
        page.setCount(processDefinitionQuery.count());
        page.setList(processDefinitionList);
        return page;
    }



    /**
     * 获取流程部署列表
     */

    public ActivitiPage<ProcessInstance> getProcessInstanceList(ActivitiPage<ProcessInstance> page , String processInstanceId,
                                                                String processDefinitionKey){
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotEmpty(processInstanceId))    processInstanceQuery.processInstanceId(processInstanceId);
        if (StringUtils.isNotEmpty(processDefinitionKey)) processInstanceQuery.processDefinitionKey(processDefinitionKey);

        List processInstances = processInstanceQuery.listPage(page.getFirstResult() , page.getMaxResults());
        page.setCount(processInstanceQuery.count());
        page.setList(processInstances);
        return page;
    }


    /**
     * 通过文件部署一个流程
     */
    public String deployFlow(String category, MultipartFile file){
        String message = "";
        String fileName = file.getOriginalFilename();
        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (extension.equals("png")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (fileName.indexOf("bpmn20.xml") != -1) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
            } else {
                message = "不支持的文件类型：" + extension;
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                message += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
            }

            if (list.size() == 0){
                message = "部署失败，没有流程。";
            }
        } catch (Exception e) {
            throw new ActivitiException("部署失败！", e);
        }
        return message;
    }


}
