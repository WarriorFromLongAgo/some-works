package com.orhon.smartcampus.modules.document.service.impl;

import com.orhon.smartcampus.modules.document.entity.Appendix;
import com.orhon.smartcampus.modules.document.mapper.AppendixMapper;
import com.orhon.smartcampus.modules.document.service.IAppendixService;
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
public class AppendixServiceImpl extends BaseServiceImpl<AppendixMapper, Appendix>implements IAppendixService {

}
