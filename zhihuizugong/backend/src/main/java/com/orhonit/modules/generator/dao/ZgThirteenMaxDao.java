package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgThirteenMaxEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 个人十三边型画像最高值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 15:30:46
 */
@Mapper
public interface ZgThirteenMaxDao extends BaseMapper<ZgThirteenMaxEntity> {

    void save(ZgThirteenMaxEntity zgThirteenMax);

    List<ZgThirteenMaxEntity> findThMaxList();
}
