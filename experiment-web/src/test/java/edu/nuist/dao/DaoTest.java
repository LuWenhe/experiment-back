package edu.nuist.dao;

import edu.nuist.entity.TagLesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DaoTest {

    @Autowired
    private BackTagDao backTagDao;

    @Test
    void getAllTeachers() {
        List<TagLesson> tagLessons = backTagDao.getTagLessons(1);
        for (TagLesson tagLesson : tagLessons) {
            System.out.println(tagLesson);
        }
    }

}