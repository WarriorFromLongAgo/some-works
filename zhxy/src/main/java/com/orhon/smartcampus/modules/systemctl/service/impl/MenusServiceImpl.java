package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.Menus;
import com.orhon.smartcampus.modules.systemctl.mapper.MenusMapper;
import com.orhon.smartcampus.modules.systemctl.service.IMenusService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class MenusServiceImpl extends BaseServiceImpl<MenusMapper, Menus>implements IMenusService {

}
