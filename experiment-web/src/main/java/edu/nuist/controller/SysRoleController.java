package edu.nuist.controller;

import edu.nuist.entity.Role;
import edu.nuist.service.SysRoleService;
import edu.nuist.vo.BasicResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation("根据用户id获取用户角色")
    @PostMapping("/getRoles")
    public BasicResultVO<Role> getRolesByUserId(@RequestBody Integer userId) {
        Role role = sysRoleService.getRoleByUserId(userId);
        return BasicResultVO.success(role);
    }

}
