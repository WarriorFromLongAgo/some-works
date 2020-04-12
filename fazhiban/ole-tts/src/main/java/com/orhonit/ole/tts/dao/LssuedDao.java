package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.tts.dto.CheckDocDTO;
import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.dto.DeptPersonDTO;
import com.orhonit.ole.tts.dto.LssuedDTO;
import com.orhonit.ole.tts.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.tts.dto.PersonDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.entity.CheckRecordEntity;
import com.orhonit.ole.tts.entity.DeptPersonEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.entity.LssuedPersonEntity;

@Mapper
public interface LssuedDao  {
	String[] DELETE = null;
	@Insert("insert into ole_ef_check(id,check_title, check_object, start_date, end_date, status, check_basis, check_content, check_way, create_name, create_by, create_date,check_num,is_relate,case_accept) values(#{id},#{checkTitle}, #{checkObject}, #{startDate}, #{endDate}, #{status}, #{checkBasis}, #{checkContent}, #{checkWay}, #{createName}, #{createBy}, #{createDate},#{checkNum},#{isRelate},#{caseAccept})")
	int save(LssuedEntity lssuedEntity);

	@Insert("insert into ole_ef_check_person(id,check_id,dept_id,person_id,create_name,create_by,create_date) values(#{id},#{check_id},#{dept_id},#{person_id},#{createName},#{createBy},#{createDate})")
	void savePerson(LssuedPersonEntity lssuedPersonEntity);

	List<DeptPersonDTO> personAll(String dept_id);

	@Select("SELECT * FROM ole_base_dept WHERE FIND_IN_SET(id,getBaseDept(#{id}))")
	List<DeptDTO> deptAll(String id);

	Integer count(@Param("params") Map<String, Object> params);

	List<LssuedEntity> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	/**
	 *app日常检查列表接口 
	 */
	List<ApiCaseListDTO> findLssuedList(@Param("params") Map<String, Object> params);
	
	/**
	 *app日常检查详情接口 
	 */
	LssuedDetailInfoDTO findCaseInfo(@Param("params") Map<String, Object> params);
	
	/**
	 * 修改状态
	 * @param id 案件编号
	 * @param valueOf 案件状态
	 */
	@Update("update ole_ef_check t set t.status = #{status} where t.check_num = #{businessKey}")
	void updateCaseStatusByCaseNum(@Param("businessKey")String businessKey, @Param("status")Integer status);

	DeptPersonEntity getByName(String id);
	
	DeptPersonDTO getById(String id);
	
	DeptPersonDTO getByCaseId(String id);
	
	@Select("SELECT name FROM ole_base_dept_person WHERE  id=#{id} ")
	DeptPersonEntity getUserName(String id);
	
	LssuedEntity findOne(String id);
	
	LssuedDetailInfoDTO getLssuedDetailInfo (@Param("checkId") String checkId, @Param("params") Map<String, Object> params);

	
	@Update("UPDATE ole_ef_check ck SET check_title=#{checkTitle},check_object=#{checkObject},start_date=#{startDate},end_date=#{endDate},ck.STATUS=#{status},check_basis=#{checkBasis},check_content=#{checkContent},check_way=#{checkWay} WHERE ck.id = #{id}")
	void update(LssuedEntity lssuedEntity);

	@Delete ("DELETE FROM ole_ef_check_person WHERE check_id=#{id}")
	void del(String id);

	@Insert("insert into ole_ef_check(id,check_title, check_object, start_date, end_date, status, check_basis, check_content, check_way, create_name, create_by, create_date,check_num, is_relate, case_accept) values(#{id},#{checkTitle}, #{checkObject}, #{startDate}, #{endDate}, #{status}, #{checkBasis}, #{checkContent}, #{checkWay}, #{createName}, #{createBy}, #{createDate},#{checkNum},#{isRelate},#{caseAccept})")
	void temsave(LssuedEntity lssuedEntitycopy);

	/**
	 * 获取check文书
	 * @param caseId
	 * @return
	 */
	List<CheckDocDTO> findCheckDoc(String checkId);
	
	@Select("SELECT * FROM ole_sys_user WHERE person_id =#{personId}")
	PersonDTO  findUserId(String personId);
	
	@Update("UPDATE ole_ef_check ck SET ck.is_relate=#{isRelate},ck.case_accept=#{caseAccept} WHERE check_num=#{checkNum}")
	void updateCheck(LssuedEntity lssuedEntity);

	List<LssuedDTO> getCaseSourceCheck(@Param("deptId") String deptId, @Param("isRelate") Integer isRelate, @Param("caseAccept") Integer caseAccept);
	
	@Update("UPDATE ole_ef_check t SET t.case_accept = #{effect} where t.check_num = #{businessKey}")
	void updateCaseAcceptByCheckNum(@Param("businessKey") String businessKey, @Param("effect") Integer effect);

	@Update("UPDATE ole_ef_check t SET t.is_relate = #{effect} where t.check_num = #{businessKey}")
	void updateisRelateByCheckNum(String businessKey, Integer effect);
	
	@Update("UPDATE ole_ef_check ck SET ck.is_relate='1' WHERE  id=#{casekId}")
	void updateNewIsRelateLssued(String casekId);

	@Update("UPDATE ole_ef_check ck SET ck.is_relate ='0' WHERE id=( SELECT ce.check_id FROM ole_ef_case  ce WHERE ce.id =#{caseId})")	
	void updateOldIsRelateLssued(String caseId);

	LssuedEntity findByCheckNum(String checkNum);
	
	@Select("SELECT * FROM ole_ef_check_record rd WHERE rd.check_id=#{id}")
	CheckRecordEntity findRecordById(String id);
	
	Integer caseCountByStatus(@Param("status") Integer status);
	
	Integer needDealCount(@Param("id") String id);
}
