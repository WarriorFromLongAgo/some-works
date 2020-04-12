package com.orhonit.ole.tts.service.yuj.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.YujDao;
import com.orhonit.ole.tts.dto.YujDTO;
import com.orhonit.ole.tts.dto.YujPersonCountDTO;
import com.orhonit.ole.tts.dto.YujPersonDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.repository.WarnInfoRepository;
import com.orhonit.ole.tts.repository.WarnPersonRepository;
import com.orhonit.ole.tts.service.yuj.YujService;

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
        return this.yujDao.appList(params, start, length);
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

}
