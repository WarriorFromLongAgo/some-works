package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.ZgPortrayalValueEntity;
import com.orhonit.modules.generator.service.ZgPortrayalValueService;
import com.orhonit.modules.generator.vo.ZgPortrayalValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Title: AppZgPortrayalValueController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
/**
 * Title: AppZgPortrayalValueController.java
 * @Description:
 * @author YaoSC
 * @date 2019年7月1日 
 */
@RestController
@RequestMapping("/app/zgportrayal")
public class AppZgPortrayalValueController {

    @Autowired
    private ZgPortrayalValueService zgPortrayalValueService;

    /**
     * 个人六边形画像
     * @param userId
     * @return
     */
    @RequestMapping("/findPortrayalSelf")
    public Map<String, List<ZgPortrayalValueEntity>> findPortrayal(Long userId){
        return zgPortrayalValueService.findPortrayal(userId);
    }

    /**
     * 科室六边形画像
     * @param lowerId
     * @return
     */
    @RequestMapping("/findPortrayalDept")
    public Map<String,List<ZgPortrayalValueEntity>> findPortrayalDept(Integer lowerId){
        return zgPortrayalValueService.findPortrayalDept(lowerId);
    }

    /**
     * 个人十二边型画像
     * @param userId
     * @return
     */
    @RequestMapping("/findPortrayalSelfThirteen")
    public Map<String,List<ZgPortrayalValueVo>> findPortrayalSelfThirteen(Long userId){
        return zgPortrayalValueService.findPortrayalSelfThirteen(userId);
    }
    
    
    
    /**
     * TODO    【我的】模块
     * 我的积分
     * @param userId
     * @return
     */
    @GetMapping("/TotalIntegral")
    public R TotalIntegral(Long userId) {
    	int count=0;
    	Map<String, List<ZgPortrayalValueEntity>>total=zgPortrayalValueService.findPortrayal(userId);
    	Set<String> keySet = total.keySet();
    	for(Iterator<String> it = keySet.iterator();it.hasNext();){
    		String key = it.next();
			System.out.println(key);
			List<ZgPortrayalValueEntity> list = total.get(key);
			for(Iterator<ZgPortrayalValueEntity> it2 = list.iterator();it2.hasNext();) {
				ZgPortrayalValueEntity pd = it2.next();
				count=pd.getStudyRank()+pd.getThinkRank()+pd.getExecuteRank()+pd.getInnovateRank()+pd.getSynergyRank()+pd.getServeRank();
			}
    	}
    	return R.ok().put("TotalIntegral", count);
    }
}
