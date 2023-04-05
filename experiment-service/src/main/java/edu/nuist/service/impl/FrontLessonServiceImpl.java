package edu.nuist.service.impl;

import edu.nuist.dao.BackTagDao;
import edu.nuist.dao.FrontLessonDao;
import edu.nuist.entity.*;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.ActiveNameVO;
import edu.nuist.vo.HistoryLesson;
import edu.nuist.vo.SonUserExp;
import edu.nuist.vo.UserAndRole;
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
    public Result loadLessonInfo(int lessonId) {
        Result result = new Result();

        try {
            Lesson lesson = frontLessonsDao.getLessonInfoByLessonId(lessonId);
            result.setCode("200");
            result.setData(lesson);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("500");
        }

        return result;
    }

    @Override
    public Result getGuideBook(int son_id) {
        Result result = new Result();
        SonChapter sonChapter;

        try {
            sonChapter = frontLessonsDao.getSonChapterBySonId(son_id);
            result.setCode("200");
            result.setData(sonChapter);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getDynamicSonExpUrl(SonUserExp sonUserExp) {
        Result result = new Result();
        SonUserExp sonUserExp1 = frontLessonsDao.IsHasSonUserExpUrl(sonUserExp);

        try {
            if (sonUserExp1 != null) {
                result.setData(sonUserExp1);
                result.setCode("200");
            } else {
                if (platformType.equals("windows")) {
                    String cmdString = "cmd /c COPY C:\\Users\\luwen\\template.ipynb C:\\Users\\luwen\\"
                            + sonUserExp.getSon_id() + sonUserExp.getUser_id() + ".ipynb";

                    Runtime.getRuntime().exec(cmdString);
                    sonUserExp.setExp_url("http://localhost:8888/notebooks/" + sonUserExp.getSon_id()
                            + sonUserExp.getUser_id() + ".ipynb");
                } else {
                    String cmdString = "cp /home/pl/jupyter_files/template.ipynb /home/pl/jupyter_files/" + sonUserExp.getSon_id() + sonUserExp.getUser_id() + ".ipynb";
                    Runtime.getRuntime().exec(cmdString);
                    sonUserExp.setExp_url("http://10.0.7.205:8888/notebooks/" + sonUserExp.getSon_id() + sonUserExp.getUser_id() + ".ipynb");
                }

                frontLessonsDao.addSonUserExpUrl(sonUserExp);
                sonUserExp.setId(frontLessonsDao.IsHasSonUserExpUrl(sonUserExp).getId());
                result.setCode("200");
                result.setData(sonUserExp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result loadTool() {
        Result result = new Result();
        List<Tool> allTools;

        try {
            allTools = frontLessonsDao.getAllTools();
            result.setData(allTools);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getHistoryLesson(UserAndRole userAndRole) {
        Result result = new Result();

        try {
            List<HistoryLesson> historyLessonList = frontLessonsDao.getHistoryID(userAndRole.getUser_id());
            List<Lesson> lessonList = new ArrayList<>();

            for (HistoryLesson historyLesson : historyLessonList) {
                lessonList.add(frontLessonsDao.getLessonById(historyLesson.getLessonId()));
            }

            result.setData(lessonList);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
