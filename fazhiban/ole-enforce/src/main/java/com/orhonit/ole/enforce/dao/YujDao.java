package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.YujPersonCountDTO;
import com.orhonit.ole.enforce.dto.YujPersonDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.dto.ps.PsCheckDTO;

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
    
	List<YujDTO> appListApp(@Param("params") Map<String, Object> params);
//	List<YujDTO> appListMap(Map<String, Object> params);
  
	Integer appListAppCount(@Param("params") Map<String, Object> params);
    
    Integer appListWarnInfoCount(YujDTO yujDTO);
    
    List<YujDTO> appListWarnInfoList(YujDTO yujDTO);

    Integer count(@Param("params") Map<String, Object> params);
    
    Integer YuJcount(@Param("params") Map<String, Object> params);
    
    Integer YuJcountApp(@Param("params") Map<String, Object> params);
    
    Integer YuJcountMap(@Param("params") Map<String, Object> params);
    
    List<ApiCountDTO> levelCount(@Param("params") Map<String, Object> params);
    
    List<ApiCountDTO> starCount(@Param("params") Map<String, Object> params);
    
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
    
    Integer yujCaseCount(YujDTO yujDTO);
    
    List<PsCaseDTO> yujCaseList(YujDTO yujDTO);
    
    Integer yujCheckDailyCount(YujDTO yujDTO);
    
    List<CheckDailyDTO> yujCheckDailyList(YujDTO yujDTO);
    
	Integer yujCheckCount(YujDTO yujDTO);
    
    List<PsCheckDTO> yujCheckList(YujDTO yujDTO);
}
