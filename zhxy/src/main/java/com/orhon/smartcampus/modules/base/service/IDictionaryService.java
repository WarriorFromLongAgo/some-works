package com.orhon.smartcampus.modules.base.service;

import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.R;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IDictionaryService extends BaseService<Dictionary> {

    //这是获取。。。
    // HashMap<String , Object> getSome1();

    List<HashMap<String, Object>> getDicOptionsByCode(String dictionaryCode);

	HashMap<String, Object> getDictionaryList();

	HashMap<String, Object> getDictionaryList(String dictionaryCode);
}
