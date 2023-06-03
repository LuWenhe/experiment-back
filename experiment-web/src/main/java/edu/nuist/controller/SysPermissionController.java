package edu.nuist.controller;

import edu.nuist.entity.Permission;
import edu.nuist.entity.Result;
import edu.nuist.service.SysPermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("权限管理")
@Slf4j
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Resource
    private SysPermissionService sysPermissionService;

    @PostMapping("/getPermissionsByRoleId")
    public Result getPermissionsByRoleId(@RequestBody Integer roleId) {
        Result result = new Result();

        try {
            List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(roleId);
            result.setData(permissions);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/getPermissionsByUserId")
    public Result getPermissionsByUserId(@RequestBody Integer userId) {
        Result result = new Result();

        try {
            List<Permission> permissions = sysPermissionService.getPermissionsByUserId(userId);
            result.setData(permissions);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

}
