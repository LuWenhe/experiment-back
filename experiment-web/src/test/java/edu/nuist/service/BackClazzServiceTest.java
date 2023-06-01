package edu.nuist.service;

import edu.nuist.entity.Clazz;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class BackClazzServiceTest {

    @Resource
    private BackClazzService backClazzService;

    @Test
    void getClazzListByTeacherId() {
        List<Clazz> clazzList = backClazzService.getClazzListByTeacherId(2);

        for (Clazz clazz : clazzList) {
            System.out.println(clazz);
        }
    }

    @Test
    void testAddClazz() {
        Clazz clazz = new Clazz(null, "班级4", 50, 2, null, null);
        backClazzService.addClazz(clazz);
    }

    @Test
    void deleteClazz() {
        Integer clazzId = 10;
        backClazzService.deleteClazz(clazzId);
    }

}
