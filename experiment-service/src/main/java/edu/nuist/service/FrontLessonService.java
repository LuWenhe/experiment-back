package edu.nuist.service;

import edu.nuist.entity.*;
import edu.nuist.vo.SonUserExp;

import java.io.IOException;
import java.util.List;

public interface FrontLessonService {

    List<Lesson> getLessonByName(String lessonName);

    List<Lesson> getLessonsByUserIdAndTagId(Integer userId, Integer tagId);

    Lesson loadLessonInfo(int lessonId);

    SonChapter getGuideBook(int sonId);

    SonUserExp getDynamicSonExpUrl(SonUserExp sonUserExp) throws IOException;

    List<Tool> getAllTools();

    List<Lesson> getLessonByUserId(Integer userId);
    
}
