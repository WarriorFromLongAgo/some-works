package com.orhon.smartcampus.modules.core.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.orhon.smartcampus.auth.JwtUtils;
import com.orhon.smartcampus.modules.baseinfo.service.ISemesterService;
import com.orhon.smartcampus.modules.systemctl.service.ISchoolsettingsService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.mapper.TInformationMapper;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import com.orhon.smartcampus.utils.R;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

//@// TODO: 2019-07-10 万一token在瞬间过期怎么办？

@Service
public class InfoService {

    @Autowired
    private IUsersService userService;

    @Autowired
    private TIInformationService teacherService;

    @Autowired
    private ISemesterService semesterService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private ISchoolsettingsService schoolsettingsService;

    //获取当前用户ID
    public Long getCurrentLoginUserId() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (!token.equals(null)) {
            return JwtUtils.getUserId(token);
        }
        return -1L;
    }


    //获取当前用户名
    public String getCurruntLoginUserName() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (!token.equals(null)) {
            return JwtUtils.getUsername(token);
        }
        return "";
    }


    //获取当前用户信息
    public HashMap getCurrentUser() {
        Long currentId = this.getCurrentLoginUserId();
        QueryWrapper<Users> q = new QueryWrapper();
        q.eq("id", currentId);
        Users one = userService.getOne(q);
        String userType = userService.getOne(q).getUser_type();


        if (userType.equals("teacher")) {
            HashMap<String, Object> ret = this.teacherService.selectTeacherInfoById(currentId);
            ret.put("userType", userType);
            ret.put("user", one);
            return ret;
        }

        // TODO: 2019-07-11 增加家长，学生....

        return null;
    }

    //获取当前用户学校ID
    public Integer getCurrentUserSchoolID() {
        Long currentId = this.getCurrentLoginUserId();
        HashMap<String, Object> userinfo = this.userService.getByID(currentId);
        return (Integer) userinfo.get("school_id");
    }

    public HashMap getCurrentSchoolSetting() {
        Integer SchoolID = this.getCurrentUserSchoolID();
        HashMap<String, Object> ret = this.schoolsettingsService.selectSchoolSettingBySchoolID(SchoolID);
        return ret;
    }

    //获取当前学期
    public HashMap getCurruentUsresSemster() {
        Long currentId = this.getCurrentLoginUserId();
        HashMap<String, Object> ret = this.teacherService.selectTeacherInfoById(currentId);
        if (!ret.equals(null)) {
            Integer schoold_id = (Integer) ret.get("school_id");
            if (schoold_id != null) {
                HashMap<String, Object> map = this.semesterService.getSemesterViaStartAndEnd(new Date(), new Date());
                return map;
            }
        }
        return null;
    }

    //Todo 编写获取当前学年的方法

}
