package com.orhon.smartcampus.modules.user.controller;


import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.ITokenService;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import com.orhon.smartcampus.modules.user.vo.LoginForm;
import com.orhon.smartcampus.utils.R;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/oauth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    @Autowired
    private IUsersService usersService;

    @Autowired
    private ITokenService tokenService;

    @PostMapping("/login")
    public R login(@RequestBody LoginForm form, HttpServletRequest request) {


        Users users = this.usersService.queryByUserName(form.getUsername());

        String password = form.getPassword();
        String php_pass = users.getPassword();
        php_pass = this.convertPhpBcryptPassword(php_pass);
        Boolean checkpw = BCrypt.checkpw(password, php_pass);

        if (users == null || !checkpw) {
            return R.error().put("msg", "账号或密码错误");
        }

        //修改用户上次登录时间

        /*  此段代码错误，暂时注释

        SimpleDateFormat sf_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = sf_.format(new Date());
		users.setLast_login_time(times);
    	String ipAddress = IPUtil.getIpAddress(request);
    	int indexOf2 = ipAddress.indexOf(",");
    	String ip = ipAddress.substring(0, indexOf2);
		users.setLast_login_ip(ip);
		usersService.updateById(users);

		*/

        //R r = this.tokenService.createToken(users.getId());

        R r = this.tokenService.createJwtToken(users.getUsername(), users.getId());

        return r;

    }

    private String convertPhpBcryptPassword(String oldPass) {
        String old_password = oldPass;
        String new_password = "";
        if (old_password.charAt(2) == 'y') {
            StringBuilder strBuilder = new StringBuilder(old_password);
            strBuilder.setCharAt(2, 'a');
            new_password = strBuilder.toString();
            return new_password;
        } else {
            return old_password;
        }
    }

    private String encodeByBCrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
