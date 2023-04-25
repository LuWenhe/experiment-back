package edu.nuist.dao;

import edu.nuist.entity.Student;
import edu.nuist.entity.TagLesson;
import edu.nuist.vo.StudentAndClazz;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DaoTest {

    @Resource
    private BackTagDao backTagDao;

    @Resource
    private BackClazzDao backClazzDao;

    @Test
    void getAllTeachers() {
        List<TagLesson> tagLessons = backTagDao.getTagLessons(1);
        for (TagLesson tagLesson : tagLessons) {
            System.out.println(tagLesson);
        }
    }

    @Test
    void getStudent() {
        List<StudentAndClazz> studentAndClazzes = backClazzDao.getStudentsByTeacherId(2);

        for (StudentAndClazz s : studentAndClazzes) {
            System.out.println(s);
        }
    }

}