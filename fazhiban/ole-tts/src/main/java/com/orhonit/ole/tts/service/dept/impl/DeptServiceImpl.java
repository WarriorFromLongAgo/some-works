package com.orhonit.ole.tts.service.dept.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.DeptDao;
import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.tts.dto.ps.PsDeptListDTO;
import com.orhonit.ole.tts.service.dept.DeptService;

/**
 * 执法主体服务类
 * @author liuzhi
 */
@Service
public class DeptServiceImpl  implements DeptService{
	
	@Autowired
	private DeptDao deptDao;

	@Override
	public List<PsDeptListDTO> getDeptList(Map<String, Object> paramMap) {
		return this.deptDao.getDeptList(paramMap);
	}
	
	@Override
	public List<PsAreaDeptDTO> areaDeptList() {
		return this.deptDao.areaDeptList();
	}
	
	@Override
	public List<PsAreaDeptDTO> areaDeptProList(Map<String, Object> paramMap) {
		return this.deptDao.areaDeptProList(paramMap);
	}
	
	@Override
	public List<PsAreaDeptDTO> getDeptAllList(Map<String, Object> paramMap) {
		return this.deptDao.getDeptAllList(paramMap);
	}
	@Override
	public List<PsDeptListDTO> getDeptListByDeptId(Map<String, Object> paramMap) {
		return this.deptDao.getDeptListByDeptId(paramMap);
	}
	@Override
	public PsDeptListDTO getCountByDeptId(Map<String, Object> paramMap) {
		String personCount =deptDao.getPersonCountByDeptId(paramMap);
		String lawCount = deptDao.getLawCountByDeptId(paramMap);
		String deptAgentCount = deptDao.getDeptAgentCountByDeptId(paramMap);
		String potenceCount = deptDao.getPotenceCountByDeptId(paramMap);
		String caseCount = deptDao.getcaseCountByDeptId(paramMap);
		PsDeptListDTO psDeptListDTO = new PsDeptListDTO();
		psDeptListDTO.setPersonCount(personCount);
		psDeptListDTO.setLawCount(lawCount);
		psDeptListDTO.setDeptAgentCount(deptAgentCount);
		psDeptListDTO.setPotenceCount(potenceCount);
		psDeptListDTO.setCaseCount(caseCount);
		return psDeptListDTO;
	}
	@Override
	public List<PsDeptListDTO> selDeptByDeptId(Map<String, Object> paramMap) {
		return this.deptDao.selDeptByDeptId(paramMap);
	}

	@Override
	public DeptDTO findDeptInfoById(String deptId) {
		return deptDao.findDeptInfoById(deptId);
	}
	
	@Override
	public List<DeptDTO> deptTreeByAreaId(String deptId) {
		return deptDao.deptTreeByAreaId(deptId);
	}

	@Override
	public List<DeptDTO> deptTreeAll() {
		return deptDao.deptTreeAll();
	}
	
	@Override
	public List<DeptDTO> deptTreeByDeptId(String deptId) {
		return deptDao.deptTreeByDeptId(deptId);
	}
}
