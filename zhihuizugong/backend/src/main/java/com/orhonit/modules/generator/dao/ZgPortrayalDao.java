package com.orhonit.modules.generator.dao;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgPortrayalEntity;

import java.util.List;

/**
 * 组工画像
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-10 10:34:45
 */
@Mapper
public interface ZgPortrayalDao extends BaseMapper<ZgPortrayalEntity> {
    //个人创新力
    Integer findInRank(Long userId);
    //个人执行力
    Integer findExRank(Long userId);
    //个人思考力
    Integer findThRank(Long userId);
    //个人学习力
    Integer findStRank(Long userId);
    //个人协同力
    Integer findSyRank(Long userId);
    //个人服务力
    Integer findSeRank(Long userId);

    void save(ZgPortrayalEntity zgPortrayal);

    List<ZgPortrayalEntity> findList(Integer type);

    ZgPortrayalEntity findListCount();
    //科室创新力
    Integer findInDeptRank(Integer lowerId);
    //科室执行力
    Integer findExDeptRank(Integer lowerId);
    //科室思考力
    Integer findThDeptRank(Integer lowerId);
    //科室学习力
    Integer findStDeptRank(Integer lowerId);
    //科室协同力
    Integer findSyDeptRank(Integer lowerId);
    //科室服务力
    Integer findSeDeptRank(Integer lowerId);
}
