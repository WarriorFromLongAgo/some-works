package com.orhonit.ole.tts.service.yuj;
import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.YujDTO;
import com.orhonit.ole.tts.dto.YujPersonCountDTO;
import com.orhonit.ole.tts.dto.YujPersonDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;

/**
 *预警
 */

public interface YujService {

    /**
     * 获取预警总数
     * @param params
     * @return
     */
    Integer getYujcount(Map<String, Object> params);

    /**
     * 获取预警总数
     * @param params
     * @return
     */
    Integer getYujcountlist(Map<String, Object> params);

    /**
     * 获取预警列表
     * @param params
     * @param start
     * @param length
     * @return
     */
    List<YujDTO> getYujList(Map<String, Object> params, Integer start, Integer length);

    /**
     * 获取预警列表
     * @param params
     * @param start
     * @param length
     * @return
     */
    List<YujDTO> getYujBtList(Map<String, Object> params, Integer start, Integer length);
    
    /**
     * 获取预警列表deptId
     * @param params
     * @param start
     * @param length
     * @return
     */
    List<YujDTO> appList(Map<String, Object> params, Integer start, Integer length);


    /**
     * 获取预警详细信息
     * @param warnId
     * @param params
     * @return
     */
    YujDTO getWarnInfoByWarnId(String warnId);
    
    /**
     * 获取预警人员
     * @param warnId
     * @param params
     * @return
     */
    List<YujPersonDTO> getWarnPerson(Map<String, Object> params);

    /**
     * 处理
     * @param YujPersonDTO
     * @return
     */
    Integer warnDeal(YujPersonDTO yujPersonDTO);

    /**
     * 查询某个案件的预警信息
     * @param caseId
     * @return
     */
    List<YujDTO> getWarnList(String caseId);
    
    /**
     * 查询某个案件的实时预警信息
     * @param caseNum
     * @return
     */
    List<YujDTO> getWarnListByCaseNum(String caseNum);
   
    /**
     * 保存预警信息
     * @param WarnInfoEntity
     * */
    void saveWarnInfo(WarnInfoEntity warnInfoEntity);

    /**
     * 保存预警人员信息
     * @param List<WarnPersonEntity>
     * */
    void saveWarnPerson(List<WarnPersonEntity> list);
    
    /**
     * 预警信息修改
     * @param yujDTO
     * */
    Integer warnUpdate(YujDTO yujDTO);
    
    List<YujPersonCountDTO> getWarnPersonCount(String warnId);
}
