package edu.nuist.controller;

import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.User;
import edu.nuist.service.SysPermissionService;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.JWTUtils;
import edu.nuist.util.RedisUtil;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.UserAndRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Resource
    private RedisUtil redisUtil;

    private static final long time = 24 * 60 * 60;

    @PostMapping("/backLogin")
    public BasicResultVO<UserPermissionDto> login(@RequestBody User user) {
        String username = user.getUsername();
        // 获得解密后的密码
        String enpPassword = new EncryptUtil().getEnpPassword(user.getPassword());

        if (usersService.findUserByNameAndPassword(username, enpPassword) > 0) {
            UserAndRoleVo userAndRoleVo = usersService.getUserAndRole(username, enpPassword);
            Map<String, String> payload = new HashMap<>();

            Integer userId = userAndRoleVo.getUserId();
            payload.put("userId", String.valueOf(userId));
            // 得到用户的token
            String token = JWTUtils.getToken(payload);

            UserPermissionDto userPermissionDto = sysPermissionService.getMenuOrButtonPermissionByUserId(userId);
            redisUtil.set("userId:" + userId, userPermissionDto, time);
            return BasicResultVO.success("登录成功", userPermissionDto, token);
        } else {
            return BasicResultVO.fail("登录失败");
        }
    }

    @PostMapping("/frontLogin")
    public BasicResultVO<UserAndRoleVo> frontLogin(@RequestBody User user) {
        // 将用户名和加密后的密码和数据库中加密的密码比对
        if (usersService.findUserByNameAndPassword(user.getUsername(),
                new EncryptUtil().getEnpPassword(user.getPassword())) > 0) {
            // 获取用户的角色
            UserAndRoleVo userAndRoleVo = usersService.getUserAndRole(user.getUsername(),
                    new EncryptUtil().getEnpPassword(user.getPassword()));
            Map<String, String> payload = new HashMap<>();

            payload.put("username", user.getUsername());
            Integer userId = userAndRoleVo.getUserId();
            payload.put("userId", String.valueOf(userId));
            // 根据用户名拿到Token
            String token = JWTUtils.getToken(payload);
            return BasicResultVO.success("登录成功", userAndRoleVo, token);
        } else {
            return BasicResultVO.fail("登录失败");
        }
    }

    @PostMapping("/backLogout")
    public BasicResultVO<Void> backLoginOut(@RequestBody Integer userId) {
        String redisKey = "userId:" + userId;
        UserPermissionDto userPermissionDto = (UserPermissionDto) redisUtil.get(redisKey);

        if (userPermissionDto != null && userPermissionDto.getUserId().equals(userId)) {
            redisUtil.del(redisKey);
        }

        return BasicResultVO.success();
    }

}
