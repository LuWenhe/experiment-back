package edu.nuist.dao;

import edu.nuist.dto.SonChapterDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class BackLessonDaoTest {

    @Resource
    private BackLessonDao backLessonDao;

    @Test
    void testBack() {
        List<SonChapterDto> sonChapterByChapterId = backLessonDao.getSonChapterByChapterId(12);
        for (SonChapterDto sonChapterDto : sonChapterByChapterId) {
            System.out.println(sonChapterDto.getJupyterFile());
        }
    }

}
