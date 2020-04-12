package com.orhonit.ole.tts.service.ruleinfo.impl;

import com.orhonit.ole.tts.dao.RuleDao;
import com.orhonit.ole.tts.dto.RuleDTO;
import com.orhonit.ole.tts.service.ruleinfo.RuleService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-8
 * CreateTime : 下午12:52
 */
@Service
public class RuleServiceImpl implements RuleService{

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private Scheduler scheduler;


    @Override
    public Integer getRuleCount(Map<String, Object> params) {
        return this.ruleDao.getRuleCount(params);
    }

    @Override
    public List<RuleDTO> getRuleList(Map<String, Object> params, Integer start, Integer length) {
        return this.ruleDao.getRuleList(params, start, length);
    }

    @Override
    public void save(RuleDTO ruleDTO) {
        List<String> roleIds =ruleDTO.getRoleIds();
        String roleId ="";
        if(roleIds.size() > 1){
            for (String ignored : roleIds) {
                roleId+=ignored+',';
            }
            roleId = roleId.substring(0,roleId.length()-1);
        }else{
            roleId = roleIds.get(0);
        }
        ruleDTO.setRoleId(roleId);
        ruleDTO.setCreateDate(new Date());
        if(ruleDTO.getRuleId() != null){
            ruleDao.deleteRuleById(ruleDTO.getRuleId());
        }
        this.ruleDao.save(ruleDTO);
    }

    @Override
    public void run(String ruleId) {

    }

    @Override
    public RuleDTO getRuleById(Integer ruleId) {
        return ruleDao.getRuleById(ruleId);
    }

    @Override
    public void deleteRuleById(Integer ruleId) {
        ruleDao.deleteRuleById(ruleId);
    }

	@Override
	public void update(RuleDTO ruleDTO) {
		List<String> roleIds =ruleDTO.getRoleIds();
        String roleId ="";
        if(roleIds.size() > 1){
            for (String ignored : roleIds) {
                roleId+=ignored+',';
            }
            roleId = roleId.substring(0,roleId.length()-1);
        }else{
            roleId = roleIds.get(0);
        }
        ruleDTO.setRoleId(roleId);
        ruleDTO.setCreateDate(new Date());
        this.ruleDao.update(ruleDTO);
		
	}


}
