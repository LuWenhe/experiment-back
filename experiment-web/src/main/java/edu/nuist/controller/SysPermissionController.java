package edu.nuist.controller;

import edu.nuist.dto.SideMenuDto;
import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Permission;
import edu.nuist.service.SysPermissionService;
import edu.nuist.util.TreeUtils;
import edu.nuist.vo.BasicResultVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/getPermissionsByRoleId")
    public BasicResultVO<List<Permission>> getPermissionsByRoleId(Integer roleId) {
        List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(roleId);
        return BasicResultVO.success(permissions);
    }

    @GetMapping("/getPermissionsByUserId")
    public BasicResultVO<List<Permission>> getPermissionsByUserId(Integer userId) {
        List<Permission> permissions = sysPermissionService.getPermissionsByUserId(userId);
        return BasicResultVO.success(permissions);
    }

    @GetMapping("/getUserPermission")
    public BasicResultVO<UserPermissionDto> getMenuPermissions(Integer userId) {
        try {
            UserPermissionDto userPermissionDto =
                    sysPermissionService.getMenuOrButtonPermissionByUserId(userId);
            return BasicResultVO.success(userPermissionDto);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }


    @GetMapping("/getMenu")
    public BasicResultVO<SideMenuDto> getMenuTree(Integer userId) {
        try {
            List<SideMenuDto> sideMenuDtoList = sysPermissionService.getMenuByUserId(userId);
            SideMenuDto menuTrees = TreeUtils.getMenuTrees(sideMenuDtoList);
            return BasicResultVO.success(menuTrees);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
