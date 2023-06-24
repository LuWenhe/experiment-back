package edu.nuist.service;

import edu.nuist.entity.Lesson;
import edu.nuist.vo.LessonSubmit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackLessonServiceTest {

    @Autowired
    private BackLessonService backLessonService;

    @Test
    void getLessonDetail() {
        LessonSubmit lessonSubmit = backLessonService.getLessonDetail(8);
        System.out.println(lessonSubmit);
    }

    @Test
    void testGetLessonsByUserId() {
        List<Lesson> lessons = backLessonService.getLessonsByUserId(50, 2);

        for (Lesson lesson : lessons) {
            System.out.println(lesson);
        }
    }

    @Test
    void testGetLessonsByTagName() {
        List<Lesson> lessons = backLessonService.getLessonsByTag("meteo");

        for (Lesson lesson : lessons) {
            System.out.println(lesson);
        }
    }

}