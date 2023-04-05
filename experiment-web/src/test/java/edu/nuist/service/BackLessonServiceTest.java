package edu.nuist.service;

import edu.nuist.entity.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackLessonServiceTest {

    @Autowired
    private BackLessonService backLessonService;

    @Test
    void getLessonDetail() {
        Result lessonDetail = backLessonService.getLessonDetail(8);
        System.out.println(lessonDetail);
    }

}