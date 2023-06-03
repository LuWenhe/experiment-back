package edu.nuist.service;

import edu.nuist.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SysRoleServiceTest {

    @Resource
    private SysRoleService sysRoleService;

    @Test
    void testGetRoleByUserId() {
        Role role = sysRoleService.getRoleByUserId(1);
        System.out.println(role);
    }

}
