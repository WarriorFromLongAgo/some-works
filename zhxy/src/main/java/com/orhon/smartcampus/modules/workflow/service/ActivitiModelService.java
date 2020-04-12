package com.orhon.smartcampus.modules.workflow.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orhon.smartcampus.modules.workflow.component.ActivitiPage;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class ActivitiModelService {

    @Autowired
    private RepositoryService repositoryService;

    protected ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取模型首页
     */
    public ActivitiPage<Model> getList(ActivitiPage<Model> page ){
        ModelQuery modelQuery = this.repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
        List list = modelQuery.listPage(page.getFirstResult() ,  page.getMaxResults());
        page.setCount(modelQuery.count());
        page.setList(list);
        return page;
    }


    /**
     * 建立一个模型
     * @param name
     * @param key
     * @param description
     * @param category
     * @throws UnsupportedEncodingException
     */
    public void create(String name, String key, String description, String category) throws UnsupportedEncodingException {
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode properties = objectMapper.createObjectNode();
        properties.put("process_author", "numberone");
        editorNode.put("properties", properties);
        ObjectNode stencilset = objectMapper.createObjectNode();
        stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilset);


        Model modelData = repositoryService.newModel();
        description = StringUtils.defaultString(description);
        modelData.setKey(StringUtils.defaultString(key));
        modelData.setName(name);
        modelData.setCategory(category);
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put("modelId", name);
        modelObjectNode.put("revision", modelData.getVersion());
        modelObjectNode.put("description", description);
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
    }

}
