package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.JWTUtils;
import edu.nuist.vo.UserAndRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService usersService;

    @RequestMapping("/user/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        Result result = new Result();

        // 将传入的密码进行加密, 然后和数据库中加密后的密码进行比对
        if (usersService.findUserByNameAndPassword(user.getUsername(),
                new EncryptUtil().getEnpPassword(user.getPassword())) > 0) {
            UserAndRole userAndRole = usersService.findUserAndRole(user.getUsername(),
                    new EncryptUtil().getEnpPassword(user.getPassword()));
            Map<String, String> payload1 = new HashMap<>();

            payload1.put("username", user.getUsername());
            // 得到用户的token
            String token = JWTUtils.getToken(payload1);
            result.setMsg("登录成功");
            result.setCode("200");
            result.setData(userAndRole);
            result.setToken(token);
        } else {
            result.setCode("500");
            result.setMsg("登录失败");
        }

        return result;
    }

    @RequestMapping("/user/frontLogin")
    @ResponseBody
    public Result frontLogin(@RequestBody User user) {
        Result result = new Result();
        System.out.println(user);

        // 将用户名和加密后的密码和数据库中加密的密码比对
        if (usersService.findUserByNameAndPassword(user.getUsername(),
                new EncryptUtil().getEnpPassword(user.getPassword())) > 0) {
            // 获取用户的角色
            UserAndRole userAndRole = usersService.findUserAndRoleInFront(user.getUsername(),
                    new EncryptUtil().getEnpPassword(user.getPassword()));
            log.info(userAndRole.toString());
            Map<String, String> payload1 = new HashMap<>();

            payload1.put("username", user.getUsername());
            // 根据用户名拿到Token
            String token = JWTUtils.getToken(payload1);
            result.setMsg("登录成功");
            result.setCode("200");
            result.setData(userAndRole);
            result.setToken(token);
        } else {
            result.setCode("500");
            result.setMsg("登录失败");
            log.error("error login");
        }

        return result;
    }

}
