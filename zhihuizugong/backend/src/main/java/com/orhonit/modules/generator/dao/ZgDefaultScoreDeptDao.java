package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgDefaultScoreDeptEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组工画像科室默认值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-19 14:43:39
 */
@Mapper
public interface ZgDefaultScoreDeptDao extends BaseMapper<ZgDefaultScoreDeptEntity> {

    void save(ZgDefaultScoreDeptEntity zgDefaultScoreDept);

    TaDepartmentMemberEntity findLowerName(Integer lowerId);

    List<ZgDefaultScoreDeptEntity> findDefaultDeptList(Integer lowerId);
}
