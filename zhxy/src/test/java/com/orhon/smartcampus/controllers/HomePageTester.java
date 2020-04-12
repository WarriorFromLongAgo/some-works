package com.orhon.smartcampus.controllers;


import com.orhon.smartcampus.modules.core.controller.HomeController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeController.class)
@WebAppConfiguration
public class HomePageTester extends RootTester{


    /**
     * 测试首页
     * @throws Exception
     */
    @Test
    public void getHomePage() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200,status);
        Assert.assertEquals("<h2>Orhon SmartCampus Api System</h2>",content);

    }

}
