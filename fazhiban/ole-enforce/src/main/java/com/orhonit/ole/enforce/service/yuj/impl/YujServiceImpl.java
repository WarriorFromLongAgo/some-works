package com.orhonit.ole.enforce.service.yuj.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.orhonit.ole.enforce.dao.YujDao;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.YujPersonCountDTO;
import com.orhonit.ole.enforce.dto.YujPersonDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.dto.ps.PsCheckDTO;
import com.orhonit.ole.enforce.entity.WarnInfoEntity;
import com.orhonit.ole.enforce.entity.WarnPersonEntity;
import com.orhonit.ole.enforce.repository.WarnInfoRepository;
import com.orhonit.ole.enforce.repository.WarnPersonRepository;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.sys.model.DeptDTO;
import com.orhonit.ole.sys.service.SysDeptService;

/**
 * 预警
 */
@Service
public class YujServiceImpl implements YujService {


    @Autowired
    private YujDao yujDao;
    
    @Autowired
	private WarnInfoRepository warnInfoRepository;
    
    @Autowired
   	private WarnPersonRepository warnPersonRepository;
    
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public YujDTO getWarnInfoByWarnId(String warnId) {

        return  this.yujDao.getWarnInfoByWarnId(warnId);

    }

    @Override
    public Integer getYujcount(Map<String, Object>  params) {
        return this.yujDao.count(params);
    }

    @Override
    public Integer getYujcountlist(Map<String, Object>  params) {
        return this.yujDao.countlist(params);
    }

    @Override
    public List<YujDTO> getYujList(Map<String, Object> params, Integer start, Integer length) {
        return this.yujDao.getYujList(params, start, length);
    }

    @Override
    public List<YujDTO> getYujBtList(Map<String, Object> params, Integer start, Integer length) {
        return this.yujDao.getYujBtList(params, start, length);
    }
    
    @Override
    public List<YujDTO> appList(Map<String, Object> params, Integer start, Integer length) {
    	params.put("start", start);
    	params.put("length", length);
        return this.yujDao.appList(params, start, length);
    }
    
    @Override
    public PageList<YujDTO> appListWarnInfo(YujDTO yujDTO) {
    	PageList<YujDTO> page = new PageList<YujDTO>();
    	if(null != yujDTO.getDeptId() && !StringUtils.isEmpty(yujDTO.getDeptId()) && !yujDTO.getDeptId().equals("null")) {
    		yujDTO = deptIdByParentId(yujDTO);
		}
    	int pageCount = this.yujDao.appListWarnInfoCount(yujDTO);
        page = initPageYuj(page, pageCount,yujDTO);
        List<YujDTO> message = this.yujDao.appListWarnInfoList(yujDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        
        return page;
//        return this.yujDao.appListWarnInfo(yujDTO);
    }
    
    @Override
    public Integer appCountByType(YujDTO yujDTO) {
    	return 0;
    }
    
    @Override
    public Integer getYujcountMap(Map<String, Object>  params) {
    	
		// 如果传进来部门编号是 监督人员，按部门查询所有
		if(!StringUtils.isEmpty(params.get("deptId").toString())) {
			List<DeptDTO> deptList = sysDeptService.deptListByParentId(params.get("deptId").toString());
			String[] deptIds = new String[deptList.size()+1];
			deptIds[0] = params.get("deptId").toString();
			for (int i = 0; i < deptList.size(); i++) {
				deptIds[i+1] = deptList.get(i).getId();
			}
			params.put("deptIds", deptIds);
		}
		return this.yujDao.count(params);
		
    }
    
    @Override
    public Integer warnDeal(YujPersonDTO yujPersonDTO)
    {
        return this.yujDao.warnDeal(yujPersonDTO);
    }

	@Override
	public List<YujDTO> getWarnList(String caseId) {
		return this.yujDao.getWarnList(caseId);
	}

	@Override
	public List<YujDTO> getWarnListByCaseNum(String caseNum) {
		return this.yujDao.getWarnListByCaseNum(caseNum);
	}

	@Override
	public void saveWarnInfo(WarnInfoEntity warnInfoEntity) {
		this.warnInfoRepository.save(warnInfoEntity);		
	}

	@Override
	public void saveWarnPerson(List<WarnPersonEntity> list) {
		this.warnPersonRepository.save(list);	
	}

	@Override
	public Integer warnUpdate(YujDTO yujDTO) {
		return this.yujDao.warnUpdate(yujDTO);
	}

	@Override
	public List<YujPersonDTO> getWarnPerson(Map<String, Object> params) {
		return this.yujDao.getWarnPerson(params);
	}

	@Override
	public List<YujPersonCountDTO> getWarnPersonCount(String warnId) {	
		return this.yujDao.getWarnPersonCount(warnId);
	}
	
	/**
	 * 一般案件实时预警
	 */
	@Override
	public PageList<PsCaseDTO> appListByRealTime(YujDTO yujDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
//		if(!StringUtils.isEmpty(params.get("deptId")) && !params.get("deptId").equals("null")) {
			//params = deptIdByParentId(params, params.get("deptId").toString());
//		}
//        int pageCount = caseDao.caseAllListNumMap(caseDTO);//得到总条数
		yujDTO = deptIdByParentId(yujDTO);
        int pageCount = yujDao.yujCaseCount(yujDTO);
        page = initPage(page, pageCount,yujDTO);
        List<PsCaseDTO> message= yujDao.yujCaseList(yujDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        
        return page;
	}
	
	/**
	 * 日常检查实时预警
	 */
	@Override
	public PageList<CheckDailyDTO> appListByCheckDaily(YujDTO yujDTO) {
		PageList<CheckDailyDTO> page = new PageList<CheckDailyDTO>();
//      int pageCount = caseDao.caseAllListNumMap(caseDTO);//得到总条数
      int pageCount = yujDao.yujCheckDailyCount(yujDTO);
      page = initPageDaily(page, pageCount,yujDTO);
      List<CheckDailyDTO> message= yujDao.yujCheckDailyList(yujDTO);
      if (!message.isEmpty()) {
          page.setDatas(message);
      }
      
      return page;
	}
	
	@Override
	public PageList<PsCheckDTO> appListByCheck(YujDTO yujDTO) {
		PageList<PsCheckDTO> page = new PageList<PsCheckDTO>();
//      int pageCount = caseDao.caseAllListNumMap(caseDTO);//得到总条数
		int pageCount = yujDao.yujCheckCount(yujDTO);
		page = initPageCheck(page, pageCount, yujDTO);
		List<PsCheckDTO> message = yujDao.yujCheckList(yujDTO);
		if (!message.isEmpty()) {
			page.setDatas(message);
		}

		return page;
	}
	
	/**
	 * 重大案件实时预警
	 */
	@Override
	public PageList<PsCaseDTO> appListByZdaj(YujDTO yujDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
//      int pageCount = caseDao.caseAllListNumMap(caseDTO);//得到总条数
		int pageCount = yujDao.yujCaseCount(yujDTO);
		page = initPage(page, pageCount, yujDTO);
		List<PsCaseDTO> message = yujDao.yujCaseList(yujDTO);
		if (!message.isEmpty()) {
			page.setDatas(message);
		}

		return page;
	}
	
//  PageList<Object> appListByCheckDaily(YujDTO yujDTO);
//  PageList<Object> appListByCheck(YujDTO yujDTO);
	
//	private static PageList<PsCaseDTO> initPage(PageList<PsCaseDTO> page, int pageCount,
//			 PsCaseDTO caseDTO) {
//       page.setTotalRecord(pageCount);
//       page.setCurrentPage(caseDTO.getCurrentPage());
//       page.setPageSize(caseDTO.getPageSize());
//       caseDTO.setStartIndexEndIndex();
//       return page;    
//	}
	
	private static PageList<PsCaseDTO> initPage(PageList<PsCaseDTO> page, int pageCount,
			 YujDTO yujDTO) {
      page.setTotalRecord(pageCount);
      page.setCurrentPage(yujDTO.getCurrentPage());
      page.setPageSize(yujDTO.getPageSize());
      yujDTO.setStartIndexEndIndex();
      return page;    
	}
	
	private static PageList<CheckDailyDTO> initPageDaily(PageList<CheckDailyDTO> page, int pageCount,
			 YujDTO yujDTO) {
		page.setTotalRecord(pageCount);
		page.setCurrentPage(yujDTO.getCurrentPage());
		page.setPageSize(yujDTO.getPageSize());
		yujDTO.setStartIndexEndIndex();
		return page;    
	}
	
	private static PageList<PsCheckDTO> initPageCheck(PageList<PsCheckDTO> page, int pageCount,
			 YujDTO yujDTO) {
		page.setTotalRecord(pageCount);
		page.setCurrentPage(yujDTO.getCurrentPage());
		page.setPageSize(yujDTO.getPageSize());
		yujDTO.setStartIndexEndIndex();
		return page;    
	}
	
	private static PageList<YujDTO> initPageYuj(PageList<YujDTO> page, int pageCount,
			 YujDTO yujDTO) {
     page.setTotalRecord(pageCount);
     page.setCurrentPage(yujDTO.getCurrentPage());
     page.setPageSize(yujDTO.getPageSize());
     yujDTO.setStartIndexEndIndex();
     return page;    
	}
	
	private YujDTO deptIdByParentId(YujDTO yujDTO) {
		if(!StringUtils.isEmpty(yujDTO.getDeptId()) && !yujDTO.getDeptId().equals("null")) {
			String deptIds = sysDeptService.getDeptsApp(yujDTO.getDeptId());
			if(null != deptIds && !StringUtils.isEmpty(deptIds)) {
				yujDTO.setDeptId(deptIds);
			} else {
				yujDTO.setDeptId("");
			}
		}
		return yujDTO;
	}
}
