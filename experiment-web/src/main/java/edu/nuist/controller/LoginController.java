package edu.nuist.controller;

import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.JWTUtils;
import edu.nuist.vo.UserAndRole;
import edu.nuist.vo.UserAndRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private UserService usersService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        Result result = new Result();

        // 将传入的密码进行加密, 然后和数据库中加密后的密码进行比对
        if (usersService.findUserByNameAndPassword(user.getUsername(),
                new EncryptUtil().getEnpPassword(user.getPassword())) > 0) {
            // 获得解密后的密码
            String enpPassword = new EncryptUtil().getEnpPassword(user.getPassword());
            UserAndRoleVo userAndRoleVo = usersService.getUserAndRole(user.getUsername(), enpPassword);

            Map<String, String> payload = new HashMap<>();

            payload.put("userId", String.valueOf(userAndRoleVo.getUserId()));
            payload.put("username", userAndRoleVo.getUsername());
            payload.put("roleId", String.valueOf(userAndRoleVo.getRoleId()));
            payload.put("roleName", userAndRoleVo.getRoleName());

            // 得到用户的token
            String token = JWTUtils.getToken(payload);

            result.setMsg("登录成功");
            result.setCode("200");
            result.setData(userAndRoleVo);
            result.setToken(token);
        } else {
            result.setCode("500");
            result.setMsg("登录失败");
        }

        return result;
    }

    @RequestMapping("/frontLogin")
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
