package edu.nuist.controller;

import edu.nuist.dto.MenuDto;
import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Permission;
import edu.nuist.entity.Result;
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
    public Result getPermissionsByRoleId(Integer roleId) {
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

    @GetMapping("/getPermissionsByUserId")
    public Result getPermissionsByUserId(Integer userId) {
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
    public BasicResultVO<MenuDto> getMenuTree(Integer userId) {
        try {
            List<MenuDto> menuDtoList = sysPermissionService.getMenuByUserId(userId);
            MenuDto menuTrees = TreeUtils.getMenuTrees(menuDtoList);
            return BasicResultVO.success(menuTrees);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

}
