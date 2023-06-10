package edu.nuist.controller;

import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.SysPermissionService;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.JWTUtils;
import edu.nuist.dto.UserAndRoleDto;
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

    @Resource
    private SysPermissionService sysPermissionService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        Result result = new Result();

        String username = user.getUsername();
        // 获得解密后的密码
        String enpPassword = new EncryptUtil().getEnpPassword(user.getPassword());

        if (usersService.findUserByNameAndPassword(username, enpPassword) > 0) {
            UserAndRoleVo userAndRoleVo = usersService.getUserAndRole(username, enpPassword);
            Map<String, String> payload = new HashMap<>();

            Integer userId = userAndRoleVo.getUserId();

            payload.put("userId", String.valueOf(userId));
            payload.put("username", userAndRoleVo.getUsername());
            payload.put("roleId", String.valueOf(userAndRoleVo.getRoleId()));
            payload.put("roleName", userAndRoleVo.getRoleName());

            // 得到用户的token
            String token = JWTUtils.getToken(payload);

            UserPermissionDto userPermissionDto = sysPermissionService.getMenuOrButtonPermissionByUserId(userId);

            result.setMsg("登录成功");
            result.setCode("200");
            result.setData(userPermissionDto);
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
            UserAndRoleDto userAndRoleDto = usersService.findUserAndRoleInFront(user.getUsername(),
                    new EncryptUtil().getEnpPassword(user.getPassword()));
            log.info(userAndRoleDto.toString());
            Map<String, String> payload1 = new HashMap<>();

            payload1.put("username", user.getUsername());
            // 根据用户名拿到Token
            String token = JWTUtils.getToken(payload1);
            result.setMsg("登录成功");
            result.setCode("200");
            result.setData(userAndRoleDto);
            result.setToken(token);
        } else {
            result.setCode("500");
            result.setMsg("登录失败");
            log.error("error login");
        }
        return result;
    }

}
