package com.orhon.smartcampus.modules.base.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {
    /**
     * 获取字典选项列表
     * @param dictionaryCode
     * @return
     */
    List<HashMap<String, Object>> getDicOptionsByCode(@Param("dictionary_code") String dictionaryCode);

    List<Dictionary> getDictionaryList1(@Param("dictionary_code") String dictionaryCode);
    List<Dictionary> getDictionaryList();
}
