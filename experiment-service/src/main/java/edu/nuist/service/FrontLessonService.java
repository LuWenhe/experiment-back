package edu.nuist.service;

import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.entity.UserJupyterFile;

import java.io.IOException;
import java.util.List;

public interface FrontLessonService {

    List<Lesson> getLessonByName(String lessonName);

    List<Lesson> getLessonsByUserIdAndTagId(Integer userId, Integer tagId);

    Lesson loadLessonInfo(int lessonId);

    SonChapter getGuideBook(int sonId);

    List<Tool> getAllTools();

    List<Lesson> getLessonByUserId(Integer userId);

    UserJupyterFile getUserJupyter(SonChapterDto sonChapterDto) throws IOException;

}
