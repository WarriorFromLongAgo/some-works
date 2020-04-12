package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgDefaultScoreEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组工画像人员默认值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-18 15:58:12
 */
@Mapper
public interface ZgDefaultScoreDao extends BaseMapper<ZgDefaultScoreEntity> {

    List<ZgDefaultScoreEntity> findList(Long userId);

    SysUserEntity findUserInfo(Long userId);

    void save(ZgDefaultScoreEntity zgDefaultScore);
}
