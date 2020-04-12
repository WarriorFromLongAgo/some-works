package com.orhon.smartcampus.modules.watchlist.service.impl;

import com.orhon.smartcampus.modules.watchlist.entity.Datas;
import com.orhon.smartcampus.modules.watchlist.mapper.DatasMapper;
import com.orhon.smartcampus.modules.watchlist.service.IDatasService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 行政值班数据项 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class DatasServiceImpl extends BaseServiceImpl<DatasMapper, Datas>implements IDatasService {

}
