package edu.nuist.service;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.entity.Tool;
import edu.nuist.vo.*;

import java.util.List;

public interface BackLessonService {

    List<Lesson> getAllLessons();

    Result addLesson(LessonSubmit lessonSubmit);

    Result getLessonDetail(int lessonId);

    Result getChaptersInfoByLessonId(int lesson_id);

    Result addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    List<Lesson> findLessonsByName(String lesson_name);

    List<Tool> getAllTools();

    List<Tool> getAllToolsByName(String tool_name);

    Result addTool(Tool tools);

    Result updateLessonInfo(LessonSubmit lessonSubmit);

    Result AddChapterInEditPart(AddChapterInEdit addChapterInEdit);

    Result delChapterInEdit(Integer chapter_id);

    Result delSonChapterInEdit(Integer son_id);

    Result AddSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    Result editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    List<Lesson> getAllLessonsByTag(String tagName);

    Result getAllOptionList();

    Result addSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    Result deleteLessonById(LessonIdInfo lessonIdInfo);

    Result getEditSonChapterInfo(SonChapterAndUrl sonChapterAndUrl);

    Result getChapterByLessonId(LessonSubmit lessonSubmit);

}
