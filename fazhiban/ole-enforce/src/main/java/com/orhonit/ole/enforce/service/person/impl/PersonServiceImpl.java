package com.orhonit.ole.enforce.service.person.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.ps.CheckPersonDTO;
import com.orhonit.ole.enforce.dto.ps.LawPersonDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndArSelectDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndDepDTO;
import com.orhonit.ole.enforce.dto.ps.PssPerDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonByAreaIdDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonById;
import com.orhonit.ole.enforce.entity.SmsEntity;
import com.orhonit.ole.enforce.repository.SmsRepository;
import com.orhonit.ole.enforce.service.person.PersonService;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.enforce.utils.SmsSend;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;

/**
 * 执法人员服务类
 * @author liuzhi
 */
@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private SmsRepository smsRepository; 
	
	@Override
	public List<PersonDTO> getPersonListByParam(Map<String, Object> paramMap) {
		return personDao.getPersonListByParam(paramMap);
	}
	/**
	 * ps 
	 * 执法人员查询
	 * @param certNum 执法证账件号 入参
	 * @param name 执法人员姓名 入参
	 * @param deptId 部门ID 入参
	 * @param lawType 执法类型 入参
	 * 
	 */
	@Override
	public List<PssPerDTO> getPerDTO(Map<String, Object> ping) {
		
		return this.personDao.getPerDTO(ping);
	}
	
	
	/**
	 * ps 
	 * 执法人员详细
	 */
	@Override
	public List<CheckPersonDTO> getSelectByPersonId(Map<String, Object> personIdMp) {
		
		return this.personDao.getSelectByPersonId(personIdMp);
	}
	/**
	 * ps 
	 * 各个区域执法人员执法类型数量统计
	 */
	@Override
	public List<PeAndArSelectDTO> getPeAndArSelect() {
		
		return this.personDao.getPeAndArSelect();
	}
	/**
	 * ps 
	 * PC各个区域执法人员数量统计
	 */
	@Override
	public List<PeAndArSelectDTO> getPeAndAr() {
		
		return this.personDao.getPeAndAr();
	}
	/**
	 * PS
	 * 一个地区下的每个部门的人员统计
	 * @param areaId 区域id
	 * @return
	 */
	@Override
	public List<PeAndDepDTO> getPeAndDepByAre(Map<String, Object> pADBMap) {
		
		return this.personDao.getPeAndDepByAre(pADBMap);
	}
	/**
	 * ps
	 * 执法人员查询（条形图用）
	 * @return
	 */
	@Override
	public List<LawPersonDTO> getSelectLaw() {
		
		return this.personDao. getSelectLaw();
	}
	/**
	 * ps
	 * 监督人员查询(条形图用)
	 * @return
	 */
	@Override
	public List<LawPersonDTO> getSelectSup() {
		
		return this.personDao.getSelectSup();
	}
	/**
	 * ps
	 * 执法人员统计
	 * @return
	 */
	@Override
	public List<LawPersonDTO> getAllLaw() {
		
		return this.personDao.getAllLaw();
	}
	/**
	 * 根据区域id查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	@Override
	public PageList<SelectPersonByAreaIdDTO> getSelectByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO) {
		// TODO Auto-generated method stub
		PageList<SelectPersonByAreaIdDTO> page = new PageList<SelectPersonByAreaIdDTO>();
        int pageCount = personDao.getMessageNum(selectPersonByAreaIdDTO.getAreaName());//得到总条数
        page = initPage(page, pageCount,selectPersonByAreaIdDTO);
        List<SelectPersonByAreaIdDTO> message= personDao.getSelectByAreaId(selectPersonByAreaIdDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	/**
	 * PS PC执法人员列表查询总数
	 * @param areaId 区域ID
	 * @return
	 */
	@Override
	public PageList<SelectPersonByAreaIdDTO> getByAreaId(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO) {
		// TODO Auto-generated method stub
		PageList<SelectPersonByAreaIdDTO> page = new PageList<SelectPersonByAreaIdDTO>();
        int pageCount = personDao.getMessagNum(selectPersonByAreaIdDTO.getAreaName());//得到总条数
        page = initPage(page, pageCount,selectPersonByAreaIdDTO);
        List<SelectPersonByAreaIdDTO> message= personDao.getByAreaId(selectPersonByAreaIdDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	 private PageList<SelectPersonByAreaIdDTO> initPage(PageList<SelectPersonByAreaIdDTO> page, int pageCount,
			 SelectPersonByAreaIdDTO selectPersonByAreaIdDTO) {
	        page.setTotalRecord(pageCount);
	        page.setCurrentPage(selectPersonByAreaIdDTO.getCurrentPage());
	        page.setPageSize(selectPersonByAreaIdDTO.getPageSize());
	        selectPersonByAreaIdDTO.setStartIndexEndIndex();
	        return page;    
	    }   
	/**
	 * 根据deptId查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	@Override
	public PageList<SelectPersonByAreaIdDTO> getSelectBydeptIdPC(SelectPersonByAreaIdDTO selectPersonByAreaIdDTO) {
		PageList<SelectPersonByAreaIdDTO> page = new PageList<SelectPersonByAreaIdDTO>();
        int pageCount = personDao.getMesNum(selectPersonByAreaIdDTO.getDeptId());//得到总条数
        page = initPage(page, pageCount,selectPersonByAreaIdDTO);
        List<SelectPersonByAreaIdDTO> message= personDao.getSelectBydeptIdPC(selectPersonByAreaIdDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public List<SelectPersonByAreaIdDTO> getSelectBydeptId(Map<String, Object> param) {
		return this.personDao.getSelectBydeptId(param);
	}
	/**
	 * ps
	 * 根据人员id查询执法人员详细
	 * @param personId
	 * @return
	 */
	@Override
	public List<SelectPersonById> getSelectPersonById(Map<String, Object> mapGetSelectPersonById) {
		return this.personDao.getSelectPersonById(mapGetSelectPersonById);
	}
	@Override
	public List<PersonAppDTO> getAppPersonListByParam(Map<String, Object> param) {
		return this.personDao.getAppPersonListByParam(param);
	}
	
	
	@Override
		public Map<Object, Object> getSmsCode(PersonDTO  personDTO,User user) {
			String code = String.valueOf(Math.random());
			code = code.substring(2, 8);
			// 添加到短信表
			SmsEntity smsEntity = new SmsEntity();
			smsEntity.setCreateBy(String.valueOf(user.getId()));
			smsEntity.setCreateDate(new Date());
			smsEntity.setCreateName(user.getNickname());
			smsEntity.setTelNum(personDTO.getTel());
			smsEntity.setCertNum(personDTO.getCertNum());
			smsEntity.setCode(code);
			String content = "尊敬的用户您好，验证码：" +code;
			smsEntity.setContent(content);
			boolean falg=false;
			Map<Object, Object> map=new HashMap<>();
			try {
			   falg=SmsSend.sendMsg(smsEntity,"71134");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(falg){
					smsEntity.setStatus("1");
				}else{
					smsEntity.setStatus("0");
				}
				this.smsRepository.save(smsEntity);
				map=new HashMap<>();
				map.put("certNum", personDTO.getCertNum());
				map.put("tel", personDTO.getTel());
				map.put("code", code);
			}
			return map;
		}
}
