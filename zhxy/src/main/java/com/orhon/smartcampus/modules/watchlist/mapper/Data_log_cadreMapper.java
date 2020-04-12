package com.orhon.smartcampus.modules.watchlist.mapper;

import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Data_log_cadre;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface Data_log_cadreMapper extends BaseMapper<Data_log_cadre> {
    List getDataLogCadre(Integer data_log_id);

    void inserts(Integer data_log_id, Object cadre);

    void deletes(Integer data_log_id);
}
