package com.orhon.smartcampus.modules.user.controller;

import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.ITokenService;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import com.orhon.smartcampus.framework.controller.ApiController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TokenRestController extends ApiController {

    @Autowired
    public ITokenService tokenService;

    @Autowired
    public IUsersService usersService;

    @GetMapping("/test")
    public R test(){
        Token token = this.tokenService.queryByToken("aa");
        System.out.println(token);
        return R.ok().put("token" , token);
    }

    @GetMapping("/test2")
    public R test2(){
        Users user = this.usersService.getById(1);
        return R.ok().put("user" , user.getId());
    }

}
