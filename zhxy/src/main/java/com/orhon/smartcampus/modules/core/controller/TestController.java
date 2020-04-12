package com.orhon.smartcampus.modules.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.utils.R;

@RestController
public class TestController {

    @Autowired
    private InfoService info;


    @RequestMapping("/test")
    public R test(){
        return R.ok().put("userInfo" , info.getCurruentUsresSemster());
    }
}
