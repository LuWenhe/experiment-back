package edu.nuist.service;

import edu.nuist.entity.Permission;
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

        // [/userBack, /back, /clazz, /banner]
        for (Permission permission : permissions) {
            urls.add(permission.getUrl());
        }

        String requestUrl = "/clazz/getClazzList";
        String subRequestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));

        if (urls.contains(subRequestUrl)) {
            System.out.println("contains");
        } else {
            System.out.println("not contains");
        }
    }

}
