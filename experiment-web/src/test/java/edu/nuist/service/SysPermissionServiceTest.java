package edu.nuist.service;

import edu.nuist.dto.MenuDto;
import edu.nuist.entity.Permission;
import edu.nuist.util.TreeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SysPermissionServiceTest {

    @Resource
    private SysPermissionService sysPermissionService;

    @Test
    void testGetPermissionsByRoleId() {
        List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(1);

        for (Permission permission : permissions) {
            System.out.println(permission);
        }
    }

    @Test
    void testPermissionsAndAspect() {
        List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(2);
        List<String> urls = new ArrayList<>();

        // [/backUser, /back, /clazz, /banner]
        for (Permission permission : permissions) {
            urls.add(permission.getRouterUrl());
        }

        String requestUrl = "/clazz/getClazzList";
        String subRequestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));

        if (urls.contains(subRequestUrl)) {
            System.out.println("contains");
        } else {
            System.out.println("not contains");
        }
    }

    @Test
    void testPermissionsMenu() {
        List<MenuDto> menuDtoList = sysPermissionService.getMenuByUserId(3);
        MenuDto menuDto = TreeUtils.getMenuTrees(menuDtoList);
        System.out.println(menuDto);
    }

}
