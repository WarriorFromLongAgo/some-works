package com.orhon.smartcampus.modules.document.service;

import com.orhon.smartcampus.modules.document.entity.Examine;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IExamineService extends BaseService<Examine> {

    List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps, PageDto dto);

    List getDocument(HashMap<String, Object> maps);

    R examine(HashMap<String, Object> maps);
}
