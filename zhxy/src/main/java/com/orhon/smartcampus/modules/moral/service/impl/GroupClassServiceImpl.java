package com.orhon.smartcampus.modules.moral.service.impl;

import com.orhon.smartcampus.modules.moral.entity.GroupClass;
import com.orhon.smartcampus.modules.moral.mapper.GroupClassMapper;
import com.orhon.smartcampus.modules.moral.service.IGroupClassService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 分组班级关系表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class GroupClassServiceImpl extends BaseServiceImpl<GroupClassMapper, GroupClass>implements IGroupClassService {

}
