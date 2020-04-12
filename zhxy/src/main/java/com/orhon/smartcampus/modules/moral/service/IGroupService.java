package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.R;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 班级分组表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IGroupService extends BaseService<Group> {
    R inserts(HashMap<String, Object> maps);
    R updates(HashMap<String, Object> maps);

}
