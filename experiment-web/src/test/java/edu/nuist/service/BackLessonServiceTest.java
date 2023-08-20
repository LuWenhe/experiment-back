package edu.nuist.service;

import edu.nuist.dto.LessonTreeDto;
import edu.nuist.entity.JupyterFile;
import edu.nuist.entity.Lesson;
import edu.nuist.util.TreeUtils;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.dto.SonChapterDto;
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
        LessonSubmit lessonSubmit = backLessonService.getLessonDetail(1);
        System.out.println(lessonSubmit);
    }

    @Test
    void getSonChapter() {
        SonChapterDto sonChapter = backLessonService.getSonChapter(120);
        System.out.println(sonChapter);
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

    @Test
    void testGetLessonFileMenu() {
        List<LessonTreeDto> lessonFileMenu = backLessonService.getLessonTree(5);
        LessonTreeDto lessonFileMenuTrees = TreeUtils.getLessonFileMenuTrees(lessonFileMenu);
        System.out.println(lessonFileMenuTrees);
    }

    @Test
    void addLesson() {
        LessonSubmit lessonSubmit = new LessonSubmit();
        lessonSubmit.setLesson_name("hello");
        lessonSubmit.setLearn_credit("65");
        lessonSubmit.setLearn_time("65");
        lessonSubmit.setCankao("cankao");
        lessonSubmit.setTags(null);
        backLessonService.addLesson(lessonSubmit);

        System.out.println(lessonSubmit);
    }

    @Test
    void getJupyterIds() {
        List<JupyterFile> jupyterFiles = backLessonService.getJupyterIdsByLessonId(29);
        System.out.println(jupyterFiles);
    }

}