package edu.nuist.service;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.vo.ActiveNameVO;
import edu.nuist.vo.SonUserExp;

import java.io.IOException;
import java.util.List;

public interface FrontLessonService {

    List<Lesson> getAllLesson(String activeName);

    List<Lesson> getLessonByName(ActiveNameVO activeNameVO);

    Lesson loadLessonInfo(int lessonId);

    SonChapter getGuideBook(int sonId);

    SonUserExp getDynamicSonExpUrl(SonUserExp sonUserExp) throws IOException;

    List<Tool> getAllTools();

    List<Lesson> getHistoryLesson(Integer userId);
    
}
