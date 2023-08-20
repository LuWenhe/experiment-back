package edu.nuist.service;

import edu.nuist.dto.SideMenuDto;
import edu.nuist.dto.UserPermissionDto;
import edu.nuist.entity.Permission;
import edu.nuist.util.TreeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class SysPermissionServiceTest {

    @Resource
    private SysPermissionService sysPermissionService;

    @Test
    void testGetUserPermissions() {
        UserPermissionDto userPermissionDto = sysPermissionService.getMenuOrButtonPermissionByUserId(2);
        System.out.println(userPermissionDto);
    }

    @Test
    void testGetPermissionsByRoleId() {
        List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(1);
        Set<String> urlList = new HashSet<>();

        for (Permission permission : permissions) {
            String requestUrl = permission.getRequestUrl();

            if (!StringUtils.isBlank(requestUrl)) {
                urlList.add(requestUrl);
            }
        }

        System.out.println(urlList);
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
        List<SideMenuDto> sideMenuDtoList = sysPermissionService.getMenuByUserId(3);
        SideMenuDto sideMenuDto = TreeUtils.getMenuTrees(sideMenuDtoList);
        System.out.println(sideMenuDto);
    }

}
