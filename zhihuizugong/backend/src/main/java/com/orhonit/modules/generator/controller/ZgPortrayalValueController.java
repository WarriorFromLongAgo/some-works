package com.orhonit.modules.generator.controller;

import com.orhonit.modules.generator.entity.ZgPortrayalValueEntity;
import com.orhonit.modules.generator.service.ZgPortrayalValueService;
import com.orhonit.modules.generator.vo.ZgPortrayalValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zgportrayal")
public class ZgPortrayalValueController {

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
}
