package edu.nuist.service;

import edu.nuist.vo.UserAndRoleVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testGetUserAndRole() {
        UserAndRoleVo userAndRole = userService.getUserAndRole("admin", "b084fbd93e31cd32436e791be42072df");
        System.out.println(userAndRole);
    }

}
