package com.orhon.smartcampus.modules.student.service;

import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.student.mapper.SInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 届 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IArrivesService extends BaseService<Arrives> {
    List<HashMap<String, Object>> PageListArrives(HashMap<String, Object> map);
}
