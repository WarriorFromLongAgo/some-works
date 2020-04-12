package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgDefaultThirteenEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 个人画像十三边型默认值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 11:25:16
 */
@Mapper
public interface ZgDefaultThirteenDao extends BaseMapper<ZgDefaultThirteenEntity> {

    void save(ZgDefaultThirteenEntity zgDefaultThirteen);

    List<ZgDefaultThirteenEntity> findThList(Long userId);
}
