package edu.nuist.utils;

import edu.nuist.dto.UserPermissionDto;
import edu.nuist.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    void testRedis() {
        UserPermissionDto userPermissionDto = (UserPermissionDto) redisUtil.get("userPermission");
        redisUtil.del("userPermission");
    }

}
