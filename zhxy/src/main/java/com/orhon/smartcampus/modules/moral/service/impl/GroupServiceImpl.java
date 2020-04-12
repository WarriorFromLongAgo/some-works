package com.orhon.smartcampus.modules.moral.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.modules.moral.mapper.GroupClassMapper;
import com.orhon.smartcampus.modules.moral.mapper.GroupMapper;
import com.orhon.smartcampus.modules.moral.service.IGroupService;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 班级分组表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper, Group> implements IGroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupClassMapper groupClassMapper;
    @Autowired
    private IGroupService service;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        Group group = JSONObject.parseObject(JSONObject.toJSONString(maps), Group.class);
        boolean save = service.save(group);
        if (save) {
            Integer group_id = group.getId();
            JSONArray class_ids = (JSONArray) maps.get("class_id");
            for (Object class_id : class_ids) {
                groupMapper.inserts(group_id, (Integer) class_id);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R updates(HashMap<String, Object> maps) {
        Group group = JSONObject.parseObject(JSONObject.toJSONString(maps), Group.class);
        boolean save = service.updateById(group);
        if (save) {
            Integer group_id = group.getId();
            groupMapper.deletes(group_id);
            JSONArray class_ids = (JSONArray) maps.get("class_id");
            for (Object class_id : class_ids) {
                groupMapper.inserts(group_id, (Integer) class_id);
            }
            return R.ok();
        }
        return R.error("参数错误！");
    }
}
