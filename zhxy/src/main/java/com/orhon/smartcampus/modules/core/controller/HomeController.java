package com.orhon.smartcampus.modules.core.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String homePage(){
        return "<h2>Orhon SmartCampus Api System</h2>";
    }
}
