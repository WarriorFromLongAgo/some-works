package com.orhon.smartcampus.modules.watchlist.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Datas;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 行政值班数据项 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface DatasMapper extends BaseMapper<Datas> {

    Object getData(Integer duty_id, Integer term_id, Integer data_log_id);

    void inserts(Integer administrativeduty_id, Integer admininspectterm_id, Integer leader, Integer school_id, String time, Integer data_log_id, Integer semester_id,String explains);

    void deletes(Integer data_log_id);
}
