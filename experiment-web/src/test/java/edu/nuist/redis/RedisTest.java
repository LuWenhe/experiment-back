package edu.nuist.redis;

import edu.nuist.util.RedisUtil;
import edu.nuist.vo.UserAndRoleVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    void testRedis() {
//        UserAndRoleVo userAndRoleVo = new UserAndRoleVo(1, "admin", 1, "管理员");
//        redisUtil.set("user", userAndRoleVo);
        UserAndRoleVo userAndRoleVo = (UserAndRoleVo) redisUtil.get("user");
        System.out.println(userAndRoleVo);
    }

}
