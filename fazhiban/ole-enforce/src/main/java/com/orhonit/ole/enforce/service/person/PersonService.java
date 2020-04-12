package com.orhonit.ole.enforce.service.person;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.ps.CheckPersonDTO;
import com.orhonit.ole.enforce.dto.ps.LawPersonDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndArSelectDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndDepDTO;
import com.orhonit.ole.enforce.dto.ps.PssPerDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonByAreaIdDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonById;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;

/**
 * 执法主体服务类
 * @author liuzhi
 *
 */
public interface PersonService {

	/**
	 * 根据部门ID获取部门下的人员信息
	 * @param deptId  部门ID
	 * @return
	 */
	List<PersonDTO> getPersonListByParam(Map<String, Object> paramMap);

	/**
	 * ps 
	 * 执法人员查询
	 * @param certNum 执法证账件号 入参
	 * @param name 执法人员姓名 入参
	 * @param deptId 部门ID 入参
	 * @param lawType 执法类型 入参
	 * 
	 */
	List<PssPerDTO> getPerDTO(Map<String, Object> ping);
	/**
	 * ps 
	 * 执法人员详细
	 * @param personIdMp
	 * @return
	 */
	List<CheckPersonDTO> getSelectByPersonId(Map<String, Object> personIdMp);
//	String getSelectByPersonId(String personId);
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
	 * @param areaId 区域ID
	 * @return
	 */
	List<PeAndDepDTO> getPeAndDepByAre(Map<String, Object> pADBMap);
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
	 * 根据区域id查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	PageList<SelectPersonByAreaIdDTO> getSelectByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * PS PC执法人员列表查询总数
	 * @param areaId 区域ID
	 * @return
	 */
	PageList<SelectPersonByAreaIdDTO> getByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * 根据deptId查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	PageList<SelectPersonByAreaIdDTO> getSelectBydeptIdPC(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO);
	/**
	 * 根据deptId查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	List<SelectPersonByAreaIdDTO> getSelectBydeptId(Map<String, Object> param);
	/**
	 * ps
	 * 根据人员id查询执法人员详细
	 * @param personId
	 * @return
	 */
	List<SelectPersonById> getSelectPersonById(Map<String, Object> mapGetSelectPersonById);

	/**
	 * app接口,获取协办人员
	 * @param param
	 * @return
	 */
	List<PersonAppDTO> getAppPersonListByParam(Map<String, Object> param);

	/**
	 * 发送短信验证码
	 * @param personDTO 执法人员信息
	 * @param user  用户信息
	 * @return 返回执法证号、电话号码、验证码
	 */
	Map<Object, Object> getSmsCode(PersonDTO personDTO, User user);
}
