package edu.nuist.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BackClazzServiceTest {

    @Resource
    private BackClazzService backClazzService;

    @Test
    void deleteClazz() {
        Integer clazzId = 10;
        backClazzService.deleteClazz(clazzId);
    }

}
