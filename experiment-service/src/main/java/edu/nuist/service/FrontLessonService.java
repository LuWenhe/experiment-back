package edu.nuist.service;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.vo.ActiveNameVO;
import edu.nuist.vo.SonUserExp;
import edu.nuist.vo.UserAndRole;

import java.io.IOException;
import java.util.List;

public interface FrontLessonService {

    List<Lesson> getAllLesson(String activeName);

    List<Lesson> getLessonByName(ActiveNameVO activeNameVO);

    Result loadLessonInfo(int lessonId);

    Result getGuideBook(int son_id);

    Result getDynamicSonExpUrl(SonUserExp sonUserExp) throws IOException;

     Result loadTool();

    Result getHistoryLesson(UserAndRole userAndRole);
    
}
