package edu.nuist.service.impl;

import edu.nuist.dao.BackTagDao;
import edu.nuist.dao.FrontLessonDao;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.SonUserExp;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FrontLessonServiceImpl implements FrontLessonService {

    @Resource
    private FrontLessonDao frontLessonsDao;

    @Resource
    private BackTagDao backTagsDao;

    @Value("${file.fileDirectory}")
    private String fileDirectory;

    @Value("${jupyter.expUrl}")
    private String expUrl;

    @Override
    public List<Lesson> getLessonByName(String lessonName) {
        return frontLessonsDao.getLessonByName(lessonName);
    }

    @Override
    public List<Lesson> getLessonsByUserIdAndTagId(Integer userId, Integer tagId) {
        List<Lesson> lessons;

        // 如果获取该用户所有课程
        if (tagId == 0) {
            lessons = frontLessonsDao.getLessonsByUserId(userId);
        } else {
            lessons = frontLessonsDao.getLessonsByUserIdAndTagId(userId, tagId);
        }

        return lessons;
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
        SonUserExp sonUserExp1 = frontLessonsDao.isHasSonUserExpUrl(sonUserExp);

        // 如果jupyter的地址不为空
        if (sonUserExp1 != null && !StringUtils.isBlank(sonUserExp1.getExp_url())) {
            return sonUserExp1;
        } else {
            int sonId = sonUserExp.getSon_id();
            int userId = sonUserExp.getUser_id();
            String expUrlDes;   // 生成Jupyter的地址

            // 复制Jupyter模板文件
            String sourcePath = fileDirectory + "jupyter/template.ipynb";
            String destinationPath = fileDirectory + "jupyter/" + sonId + userId + ".ipynb";
            FileUtils.copyFile(new File(sourcePath), new File(destinationPath));

            expUrlDes = expUrl + sonId + userId + ".ipynb";

            sonUserExp.setExp_url(expUrlDes);
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
    public List<Lesson> getLessonByUserId(Integer userId) {
        List<Integer> lessonIds = frontLessonsDao.getLessonByUserId(userId);
        List<Lesson> lessonList = new ArrayList<>();

        for (Integer lessonId : lessonIds) {
            lessonList.add(frontLessonsDao.getLessonById(lessonId));
        }

        return lessonList;
    }

}
