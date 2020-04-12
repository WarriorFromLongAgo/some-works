package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.dto.PersonAppDTO;
import com.orhonit.ole.tts.dto.PersonDTO;
import com.orhonit.ole.tts.dto.ZfrDTO;
import com.orhonit.ole.tts.dto.ps.CheckPersonDTO;
import com.orhonit.ole.tts.dto.ps.LawPersonDTO;
import com.orhonit.ole.tts.dto.ps.PeAndArSelectDTO;
import com.orhonit.ole.tts.dto.ps.PeAndDepDTO;
import com.orhonit.ole.tts.dto.ps.PssPerDTO;
import com.orhonit.ole.tts.dto.ps.SelectPersonByAreaIdDTO;
import com.orhonit.ole.tts.dto.ps.SelectPersonById;

@Mapper
public interface PersonDao {
	
	/**
	 * 根据参数获取执法人员列表信息
	 * @param params
	 * @return
	 */
	List<PersonDTO> getPersonListByParam(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询案件主执法人员
	 * @param id
	 * @return
	 */
	ZfrDTO getZzfrInfo(String id);
	
	/**
	 * 查询人员信息
	 * @param id
	 * @return
	 */
	PersonDTO findPersonInfo(String id);
	
	/**
	 * 查询用户信息
	 * @param id
	 * @return
	 */
	PersonDTO findUserInfo(String id);
	
	/**
	 * 根据执法人员ID 查询用户ID
	 * @param id
	 * @return
	 */
	PersonDTO findPersonId(String id);
	
	
	/**
	 * 查询按键主执法人员
	 * @param id
	 * @return
	 */
	ZfrDTO getFzfrInfo(String id);
	
	/**
	 *ps  
	 *执法人员查询
	 * @param certNum 执法证账件号 入参
	 * @param name 执法人员姓名 入参
	 * @param deptId 部门ID 入参
	 * @param lawType 执法类型 入参
	 * 
	 */
	List<PssPerDTO> getPerDTO(@Param("ping") Map<String, Object> ping);
	/**
	 * ps 
	 * 执法人员详情
	 * @param personIdMp
	 * @return
	 */
	List<CheckPersonDTO> getSelectByPersonId(@Param("personIdMp") Map<String, Object> personIdMp);
	/**
	 * ps 
	 * 各个区域执法人员执法类型数量统计
	 * @return
	 */
	List<PeAndArSelectDTO> getPeAndArSelect();
	/**
	 * ps 
	 * PC各个区域执法人员数量统计
	 * @return
	 */
	List<PeAndArSelectDTO> getPeAndAr();
	/**
	 * PS
	 * 一个地区下的每个部门的人员统计
	 * @param areaId
	 * @return
	 */
	List<PeAndDepDTO> getPeAndDepByAre(@Param("pADBMap") Map<String, Object> pADBMap);

	/**
	 * ps
	 * 执法人员查询（条形图用）
	 * @return
	 */
	List<LawPersonDTO> getSelectLaw();
	/**
	 * ps
	 * 监督人员查询(条形图用)
	 * @return
	 */
	List<LawPersonDTO> getSelectSup();
	/**
	 * ps
	 * 执法人员统计
	 * @return
	 */
	List<LawPersonDTO> getAllLaw();
	
	/**
	 * 查询审核审批负责人
	 * */
	PersonDTO findPersonName(@Param("params") Map<String, Object> params);
	
	/**
	 * 根据参数获取执法人员列表信息
	 * @param deptId
	 * @return
	 */
	List<PersonAppDTO> getPersonListByDeptId(String deptId);
	/**
	 * 根据区域id查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	List<SelectPersonByAreaIdDTO> getSelectByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * PS PC根据区域id查询执法人员信息
	 * @param areaId 区域ID
	 * @return
	 */
	List<SelectPersonByAreaIdDTO> getByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * 查询执法人员列表总条数
	 * @param areaId 区域name
	 * @return
	 */
    public int getMessageNum(String areaName);
    /**
	 * PS PC执法人员列表查询总数
	 * @param areaId 区域name
	 * @return
	 */
    public int getMessagNum(String areaName);
    /**
	 * 查询执法人员列表总条数
	 * @param areaId 区域name
	 * @return
	 */
    public int getMesNum(String deptId);
	/**
	 * 根据deptId查询执法人员详细信息
	 * @param 
	 * @return
	 */
	List<SelectPersonByAreaIdDTO> getSelectBydeptIdPC(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * 根据deptId查询执法人员详细信息
	 * @param 
	 * @return
	 */
	List<SelectPersonByAreaIdDTO> getSelectBydeptId(@Param("param") Map<String, Object> param);
	/**
	 * ps
	 * 根据人员id查询执法人员详细
	 * @param personId
	 * @return
	 */
	List<SelectPersonById> getSelectPersonById(@Param("mapGetSelectPersonById") Map<String, Object> mapGetSelectPersonById);

	List<PersonAppDTO> getAppPersonListByParam(@Param("param") Map<String, Object> param);

	List<PersonAppDTO> getPersonRole(String id);
}
