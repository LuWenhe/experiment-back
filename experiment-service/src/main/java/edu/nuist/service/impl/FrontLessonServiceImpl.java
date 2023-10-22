package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.FrontLessonDao;
import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.*;
import edu.nuist.service.FrontLessonService;

import edu.nuist.util.FileUtils;
import org.springframework.beans.BeanUtils;
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
    private FrontLessonDao frontLessonDao;

    @Resource
    private BackLessonDao backLessonDao;

    @Value("${file.fileDirectory}")
    private String fileDirectory;

    @Value("${file.userExperimentDirectory}")
    private String userExperimentDirectory;

    @Value("${jupyter.expUrl}")
    private String expUrl;

    @Override
    public List<Lesson> getLessonByName(String lessonName) {
        return frontLessonDao.getLessonByName(lessonName);
    }

    @Override
    public List<Lesson> getLessonsByUserIdAndTagId(Integer userId, Integer tagId) {
        List<Lesson> lessons;

        // 如果获取该用户所有课程
        if (tagId == 0) {
            lessons = frontLessonDao.getLessonsByUserId(userId);
        } else {
            lessons = frontLessonDao.getLessonsByUserIdAndTagId(userId, tagId);
        }

        return lessons;
    }

    @Override
    public Lesson loadLessonInfo(int lessonId) {
        return frontLessonDao.getLessonInfoByLessonId(lessonId);
    }

    @Override
    public SonChapter getGuideBook(int sonId) {
        return frontLessonDao.getSonChapterBySonId(sonId);
    }

    @Override
    public List<Tool> getAllTools() {
        return frontLessonDao.getAllTools();
    }

    @Override
    public List<Lesson> getLessonByUserId(Integer userId) {
        List<Integer> lessonIds = frontLessonDao.getLessonByUserId(userId);
        List<Lesson> lessonList = new ArrayList<>();

        for (Integer lessonId : lessonIds) {
            lessonList.add(frontLessonDao.getLessonById(lessonId));
        }

        return lessonList;
    }

    @Override
    public UserJupyterFile getUserJupyter(SonChapterDto sonChapterDto) throws IOException {
        int userId = sonChapterDto.getUserId();
        int sonId = sonChapterDto.getId();
        String username = sonChapterDto.getUsername();
        String path = sonChapterDto.getJupyterFile().getPath();

        UserJupyterFile userJupyterFile = frontLessonDao.getUserJupyterFile(userId, sonId);

        if (userJupyterFile != null) {
            return userJupyterFile;
        }

        // 用户需要运行的Jupyter文件
        userJupyterFile = new UserJupyterFile();

        String sourcePath = fileDirectory + path;
        String desPath = userExperimentDirectory + username + "/" + path;

        // 将课程的文件复制到用户的目录
        FileUtils.copyDirectoriesAndFile(new File(sourcePath), new File(desPath));
        List<JupyterFile> jupyterFiles = backLessonDao.getJupyterFiles(sonId);
        List<UserJupyterFile> userJupyterFiles = new ArrayList<>();

        for (JupyterFile jupyterFile : jupyterFiles) {
            if (jupyterFile.getType() == 1) {
                BeanUtils.copyProperties(jupyterFile, userJupyterFile, "url");
            }

            // 需要保存到数据库中的对象数据
            UserJupyterFile desJupyterFile = new UserJupyterFile();
            BeanUtils.copyProperties(jupyterFile, desJupyterFile, "url");

            String url = expUrl + username + "/" + path + "/" + jupyterFile.getName();

            userJupyterFile.setUrl(url);
            desJupyterFile.setUserId(userId);
            desJupyterFile.setUrl(url);
            userJupyterFiles.add(desJupyterFile);
        }

        // 更新用户实验数据库
        frontLessonDao.addUserJupyterFiles(userJupyterFiles);
        return userJupyterFile;
    }

}
