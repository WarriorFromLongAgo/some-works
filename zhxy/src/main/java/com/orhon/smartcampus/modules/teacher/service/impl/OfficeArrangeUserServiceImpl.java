package com.orhon.smartcampus.modules.teacher.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.modules.moral.service.IGroupService;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.modules.teacher.mapper.OfficeArrangeMapper;
import com.orhon.smartcampus.modules.teacher.mapper.OfficeArrangeUserMapper;
import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeUserService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class OfficeArrangeUserServiceImpl extends BaseServiceImpl<OfficeArrangeUserMapper, OfficeArrangeUser>implements IOfficeArrangeUserService {
    @Autowired
    private OfficeArrangeUserMapper mapper;
    @Autowired
    private IOfficeArrangeUserService service;
    public List<HashMap<String, Object>> getOfficeArrangeTeacher(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> officeArrange = mapper.getOfficeArrangeTeacher(maps);
        return officeArrange;
    }
    @Override
    public List<HashMap<String, Object>> getOfficeArrangeTeacher(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return mapper.getOfficeArrangeTeacherpage(maps,dto);
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public R inserts(HashMap<String, Object> maps) {
        JSONArray user_ids = (JSONArray) maps.get("user_id");
        Integer room_id = (Integer) maps.get("room_id");
        for (Object user_id : user_ids) {
            //判断是否已经分配，
//            JsonObject select = mapper.selects(room_id, (Integer) user_id);
//            if(!select){
                mapper.inserts(room_id, (Integer) user_id);
//            }
        }
        return R.error("分配成功！");
    }
}
