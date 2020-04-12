package com.orhon.smartcampus.controllers;


import com.orhon.smartcampus.modules.base.controller.DictionaryRestController;
import com.orhon.smartcampus.modules.core.controller.HomeController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = DictionaryRestController.class)
public class BaseDictionaryTester extends RootTester {


    @Test
    public void testIndex() throws Exception {
        String content = this.mock_http_get_method_content("/");
    }
    
}
