package com.orhonit.ole.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dao.SysAreaDao; 
import com.orhonit.ole.sys.dao.SysDeptDao;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.AreaEntity;
import com.orhonit.ole.sys.model.DeptDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.AreaRepository;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 执法主体服务类
 * @author liuzhi
 */
@Service
public class SysDeptServiceImpl  implements SysDeptService{

	@Autowired
	SysDeptDao sysDeptDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	SysAreaDao sysAreaDao;
	
	@Override
	public List<DeptDTO> deptTreeByAreaId(String deptId) {
		return sysDeptDao.deptTreeByAreaId(deptId);
	}

	@Override
	public List<DeptDTO> deptTreeAll() {
		return sysDeptDao.deptTreeAll();
	}

	@Override
	public List<DeptDTO> deptTreeByDeptId(String dept_id) {
		return sysDeptDao.deptTreeByDeptId(dept_id);
	}

	@Override
	public DeptDTO findDeptById(String deptId) {
		return sysDeptDao.findDeptById(deptId);
	}
	
	@Override
	public List<DeptDTO> deptTreeByParentId(String parentId) {
		return sysDeptDao.deptTreeByParentId(parentId);
	}
	
	@Override
	public List<DeptDTO> deptListByParentId(String parentId) {
		return sysDeptDao.deptListByParentId(parentId);
	}
	
	@Override
	public String deptListByParentIdString(@RequestParam("parentId")String parentId) {
		return sysDeptDao.deptListByParentIdString(parentId);
	}
	
	@Override
	public List<DeptDTO> deptTreeByNameLike(String name) {
		return sysDeptDao.deptTreeByNameLikes(name);
	}

	@Override
	public String getDepts() {
		User user =UserUtil.getCurrentUser();
		String deptIds = "";
		if(user == null){
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			user = this.userService.getUserByPersonId(personDTO.getId());
		}
		if (!user.isAdmin()) {
			DeptDTO deptDTO = findDeptById(user.getDept_id());
			if(null == deptDTO) {
				return deptIds;
			}
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				// 如果是市本级的法制办则显示所有
				if (!CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					deptIds = this.sysDeptDao.deptListByAreaId(user.getArea_id());
				}
			}else{
				deptIds = this.sysDeptDao.deptListByDeptId(user.getDept_id());
			}
		}
		return deptIds;
	}
	
	@Override
	public String getDeptsApp(String deptId) {
		User user =UserUtil.getCurrentUser();
		String deptIds = "";
		if(user == null){
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			user = this.userService.getUserByPersonId(personDTO.getId());
		}
		if (!user.isAdmin()) {
//			DeptDTO deptDTO = findDeptById(user.getDept_id());
			DeptDTO deptDTO = findDeptById(deptId);
			if(null == deptDTO || StringUtils.isEmpty(deptDTO.getDeptProperty()) || StringUtils.isEmpty(deptDTO.getLawType())) {
				return deptIds;
			}
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
//				String areaId = sysAreaDao.areaIdByAreaId(user.getArea_id());
				// 如果是市本级的法制办则显示所有areaIdByAreaId
//				if (!CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
//					deptIds = this.sysDeptDao.deptListByAreaId(user.getArea_id());
//					deptIds = this.sysDeptDao.deptIdByAreaId(areaId);
//				}
				if (!CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					deptIds = this.sysDeptDao.deptListByAreaId(user.getArea_id());
				}
			}  else{
				deptIds = this.sysDeptDao.deptListByDeptId(user.getDept_id());
			}
//			else if(CommonParameters.LawType.ZF.equals(deptDTO.getLawType())) {
//				deptIds = deptId;
//			}
		}
		return deptIds;
	}

}
