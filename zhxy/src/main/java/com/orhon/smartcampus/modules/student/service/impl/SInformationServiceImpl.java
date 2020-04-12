package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.mapper.DictionaryMapper;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.mapper.SInformationMapper;
import com.orhon.smartcampus.modules.student.service.SIInformationService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生基础信息表 服务实现类
 * </p>
 *
 * @author bao
 */
@Service
public class SInformationServiceImpl extends BaseServiceImpl<SInformationMapper, SInformation>implements SIInformationService {

	@Autowired
	private SInformationMapper mapper;

	@Autowired
	private IDictionaryService dictionaryService;

	@Override
	public List<Map<String, Object>> getManagementTj(String school_id) {
		return mapper.getManagementTj(school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementjTj(String school_id) {
		return mapper.getManagementjTj(school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementrxTj(String string,String school_id) {
		return mapper.getManagementrxTj(string,school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementnvTj(String school_id) {
		return mapper.getManagementnvTj(school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementnldTj(String school_id) {
		return mapper.getManagementnldTj(school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementmzTj(String school_id) {
		return mapper.getManagementmzTj(school_id);
	}

	@Override
	public List<Map<String, Object>> getManagementmgznvTj(String string,String school_id) {
		return mapper.getManagementmgznvTj(string,school_id);
	}

	@Override
	public List<SInformation> getStudentByLearninfo(HashMap<String, Object> maps,Map student_name) {
		if(maps.get("student_name")!=null ||maps.get("idcard")!=null ||maps.get("student_number")!=null ||maps.get("arrive_id")!=null || maps.get("at_school")!=null || maps.get("way_to_study")!=null || maps.get("transaction_type")!=null || maps.get("pinyin_name")!=null) {
			List<SInformation> studentByLearninfo = mapper.getStudentByLearninfo(maps,student_name);
			HashMap<String,Object> dictionaryList = dictionaryService.getDictionaryList();
			List<SInformation> list = new ArrayList<SInformation>();
			for (SInformation sInformation : studentByLearninfo) {
				if(dictionaryList.get("nationality"+sInformation.getCountry())!=null) {
					sInformation.setCountry(dictionaryList.get("nationality"+sInformation.getCountry()).toString());
				}
				if(dictionaryList.get("gender"+sInformation.getSex())!=null) {
					sInformation.setSex(dictionaryList.get("gender"+sInformation.getSex()).toString());
				}
				if (dictionaryList.get("nation"+sInformation.getNation())!=null) {
					sInformation.setNation(dictionaryList.get("nation"+sInformation.getNation()).toString());
				}
				if (dictionaryList.get("politicaltype"+sInformation.getPolitical())!=null) {
					sInformation.setPolitical(dictionaryList.get("politicaltype"+sInformation.getPolitical()).toString());
				}
				if (dictionaryList.get("household_register_type"+sInformation.getHousehold_register_type())!=null) {
					sInformation.setHousehold_register_type(dictionaryList.get("household_register_type"+sInformation.getHousehold_register_type()).toString());
				}
				list.add(sInformation);
			}
			return list;
		}
		return null;
	}

	@Override
	public List<SInformation> getStudentByLearninfo(HashMap<String, Object> maps, PageDto dto,Map student_name) {
		// TODO Auto-generated method stub
		return mapper.getStudentByLearninfopage(maps,dto,student_name);
	}

	@Override
	public List<SInformation> getEclassByStudent(HashMap<Object, Object> maps,Map mapTypes) {
		HashMap<String,Object> dictionaryList = dictionaryService.getDictionaryList();
		List<SInformation> list = new ArrayList<SInformation>();
		List<SInformation> eclassByStudent = mapper.getEclassByStudent(maps,mapTypes);
		for (SInformation sInformation : eclassByStudent) {
			if(dictionaryList.get("nationality"+sInformation.getCountry())!=null) {
				sInformation.setCountry(dictionaryList.get("nationality"+sInformation.getCountry()).toString());
			}
			if(dictionaryList.get("gender"+sInformation.getSex())!=null) {
				sInformation.setSex(dictionaryList.get("gender"+sInformation.getSex()).toString());
			}
			if (dictionaryList.get("nation"+sInformation.getNation())!=null) {
				sInformation.setNation(dictionaryList.get("nation"+sInformation.getNation()).toString());
			}
			if (dictionaryList.get("politicaltype"+sInformation.getPolitical())!=null) {
				sInformation.setPolitical(dictionaryList.get("politicaltype"+sInformation.getPolitical()).toString());
			}
			if (dictionaryList.get("household_register_type"+sInformation.getHousehold_register_type())!=null) {
				sInformation.setHousehold_register_type(dictionaryList.get("household_register_type"+sInformation.getHousehold_register_type()).toString());
			}
			list.add(sInformation);
		}
		return list;
	}

	@Override
	public List<HashMap<String, Object>> PageListStudent(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.PageListStudent(map);
	}

	@Override
	public Integer StudentCount(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.StudentCount(map);
	}



}
