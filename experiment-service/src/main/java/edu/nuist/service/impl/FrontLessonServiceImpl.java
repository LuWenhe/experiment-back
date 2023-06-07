package edu.nuist.service.impl;

import edu.nuist.dao.BackTagDao;
import edu.nuist.dao.FrontLessonDao;
import edu.nuist.entity.*;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.ActiveNameVO;
import edu.nuist.vo.HistoryLesson;
import edu.nuist.vo.SonUserExp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FrontLessonServiceImpl implements FrontLessonService {

    @Resource
    private FrontLessonDao frontLessonsDao;

    @Resource
    private BackTagDao backTagsDao;

    @Value("${platform.type}")
    private String platformType;

    @Value("${platform.address}")
    private String address;

    @Value("${platform.windowsExpUrl}")
    private String windowExpUrl;

    @Value("${platform.linuxExpUrl}")
    private String linuxExpUrl;

    @Override
    public List<Lesson> getAllLesson(String activeName) {
        List<Lesson> lessonList = new ArrayList<>();
        List<TagLesson> tagLessonList;

        if (activeName.equals("all")) {
            return frontLessonsDao.getAllLessons();
        } else if (activeName.equals("ai")) {
            int tag_id = backTagsDao.getTagIDByTagName(activeName);
            tagLessonList = backTagsDao.getTagLessons(tag_id);

            for (TagLesson tagLesson : tagLessonList) {
                lessonList.add(tagLesson.getLesson());
            }

            return lessonList;
        } else if (activeName.equals("meteo")) {
            int tag_id = backTagsDao.getTagIDByTagName(activeName);
            tagLessonList = backTagsDao.getTagLessons(tag_id);

            for (TagLesson tagLesson : tagLessonList) {
                lessonList.add(tagLesson.getLesson());
            }

            return lessonList;
        } else if (activeName.equals("soft")) {
            int tag_id = backTagsDao.getTagIDByTagName(activeName);
            tagLessonList = backTagsDao.getTagLessons(tag_id);

            for (TagLesson tagLesson : tagLessonList) {
                lessonList.add(tagLesson.getLesson());
            }

            return lessonList;
        } else {
            int tag_id = backTagsDao.getTagIDByTagName(activeName);
            tagLessonList = backTagsDao.getTagLessons(tag_id);

            for (TagLesson tagLesson : tagLessonList) {
                lessonList.add(tagLesson.getLesson());
            }

            return lessonList;
        }
    }

    @Override
    public List<Lesson> getLessonByName(ActiveNameVO activeNameVO) {
        return frontLessonsDao.getLessonByName(activeNameVO);
    }

    @Override
    public Lesson loadLessonInfo(int lessonId) {
        return frontLessonsDao.getLessonInfoByLessonId(lessonId);
    }

    @Override
    public SonChapter getGuideBook(int sonId) {
        return frontLessonsDao.getSonChapterBySonId(sonId);
    }

    @Override
    public SonUserExp getDynamicSonExpUrl(SonUserExp sonUserExp) throws IOException {
        // 查询是否有jupyter文件
        SonUserExp sonUserExp1 = frontLessonsDao.isHasSonUserExpUrl(sonUserExp);

        if (sonUserExp1 != null) {
            return sonUserExp1;
        } else {
            int sonId = sonUserExp.getSon_id();
            int userId = sonUserExp.getUser_id();
            String cmdString;   // 执行复制的指令
            String expUrl;      // 生成Jupyter的地址

            if (platformType.equals("windows")) {
                cmdString = "cmd /c COPY C:/Users/luwen/template.ipynb C:/Users/luwen/"
                        + sonId + userId + ".ipynb";
                expUrl = windowExpUrl + sonId + userId + ".ipynb";
            } else {
                cmdString = "cp /home/pl/jupyter_files/template.ipynb /home/pl/jupyter_files/"
                        + sonId + userId + ".ipynb";
                expUrl = linuxExpUrl + sonId + userId + "./ipynb";
            }

            // 执行指令
            Runtime.getRuntime().exec(cmdString);
            sonUserExp.setExp_url(expUrl);
            frontLessonsDao.addSonUserExpUrl(sonUserExp);
            sonUserExp.setId(frontLessonsDao.isHasSonUserExpUrl(sonUserExp).getId());
            return sonUserExp;
        }
    }

    @Override
    public List<Tool> getAllTools() {
        return frontLessonsDao.getAllTools();
    }

    @Override
    public List<Lesson> getHistoryLesson(Integer userId) {
        List<HistoryLesson> historyLessons = frontLessonsDao.getHistoryID(userId);
        List<Lesson> lessonList = new ArrayList<>();

        for (HistoryLesson historyLesson : historyLessons) {
            lessonList.add(frontLessonsDao.getLessonById(historyLesson.getLessonId()));
        }

        return lessonList;
    }

}
