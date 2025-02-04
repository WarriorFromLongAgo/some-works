package com.orhonit.ole.server.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.ReportIndex;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 *首页统计信息控制器
 * @author liuzh
 *
 */
@Slf4j(topic = "indexControlLogger")
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private LtcAttDao ltcAttDao;

    @RequestMapping("/test")
    public Map<String , Object> json(){
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("zfzt", ltcAttDao.countzfzt());
        data.put("zfry", ltcAttDao.countzfry());
        data.put("qzsl", ltcAttDao.countqzsl());
        data.put("flts", ltcAttDao.countflts());
        return data;
    }
    //执法主体
    @RequestMapping("/zfztfbqk")
    public List<Map<String,Object>>  zfztfbqk()
    {
        List<Map<String,Object>> listMap = new ArrayList<>();
        List<ReportIndex> list = ltcAttDao.getZfzt();
        Map<String,Object> map;
        for (ReportIndex reportIndex: list) {
            map = new HashMap<>();
            map.put("name",reportIndex.getName());
            map.put("mgl_name",reportIndex.getMgl_name());
            map.put("cnumber",reportIndex.getCnumber());
            listMap.add(map);
        }
        return listMap;
    }

    //执法人员
    @RequestMapping("/zfryfbqk")
    public List<Map<String,Object>>  zfryfbqk()
    {
        List<Map<String,Object>> listMap = new ArrayList<>();
        List<ReportIndex> list = ltcAttDao.getZfry();
        Map<String,Object> map;
        for (ReportIndex reportIndex: list) {
            map = new HashMap<>();
            map.put("name",reportIndex.getName());
            map.put("mgl_name",reportIndex.getMgl_name());
            map.put("cnumber",reportIndex.getCnumber());
            listMap.add(map);
        }
        return listMap;
    }
}
