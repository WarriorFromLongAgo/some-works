package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.dto.YujDTO;
import com.orhonit.ole.tts.dto.YujPersonCountDTO;
import com.orhonit.ole.tts.dto.YujPersonDTO;

/**
 * 预警
 */
@Mapper
public interface YujDao {

    /**
     * 获取预警列表
     * @param yujId
     * @return
     */
      List<YujDTO> getYujList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
                               @Param("length") Integer length);

    /**
     * 获取预警列表
     * @param yujId
     * @return
     */
    List<YujDTO> getYujBtList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
                            @Param("length") Integer length);
    /**
     * 获取预警列表deptId
     * @param yujId
     * @return
     */
    List<YujDTO> appList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
                            @Param("length") Integer length);

    Integer count(@Param("params") Map<String, Object> params);
    
    Integer YuJcount(@Param("params") Map<String, Object> params);
    
    Integer countBydeptId(@Param("params") Map<String, Object> params);

    Integer countlist(@Param("params") Map<String, Object> params);
    /**
     * 获取预警非常详细的信息
     * @param YujId
     * @return
     */
    YujDTO getWarnInfoByWarnId(@Param("warnId") String warnId);

    /**
     * 预警处理
     * @param YujPersonDTO
     * */
    Integer warnDeal(YujPersonDTO yujPersonDTO);
    
    /**
     * 预警修改
     * @param YujDTO
     * */
    Integer warnUpdate(YujDTO yujDTO);
    /**
     * 查询某个案件的预警信息
     * @param caseId
     * @return
     */
    List<YujDTO> getWarnList(@Param("caseId")String caseId);
    
    /**
     * 查询某个案件的实时预警信息
     * @param caseNum
     * @return
     */
    List<YujDTO> getWarnListByCaseNum(@Param("caseNum")String caseNum);
    
    /**
     * 获取预警人员
     * @param warnId
     * @param params
     * @return
     */
    List<YujPersonDTO> getWarnPerson(@Param("params") Map<String, Object> params);
    
    
    List<YujPersonCountDTO> getWarnPersonCount(@Param("warnId")String warnId);
}
