package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgInformationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资讯表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-15 17:40:51
 */
@Mapper
public interface ZgInformationDao extends BaseMapper<ZgInformationEntity> {

    List<ZgInformationEntity> findList();
}
