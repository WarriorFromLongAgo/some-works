package com.orhon.smartcampus.modules.moral.service;

import com.orhon.smartcampus.modules.moral.entity.DataValues;
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
public interface IDataValuesService extends BaseService<DataValues> {
    R studentStatics(HashMap<String, Object> maps);

    List<HashMap<String, Object>> getDateValue(HashMap<String, Object> maps, PageDto dto);

    List getDateValue(HashMap<String, Object> maps);

    Object getOneDateValue(Integer id);

    R inserts(HashMap<String, Object> maps);

    R updates(HashMap<String, Object> maps);
}
