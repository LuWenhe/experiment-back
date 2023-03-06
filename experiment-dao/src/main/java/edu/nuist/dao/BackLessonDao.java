package edu.nuist.dao;

import edu.nuist.entity.Chapter;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.vo.*;

import java.util.List;

public interface BackLessonDao {

    List<Lesson> getAllLessons();

    void addLesson(LessonSubmit lessonSubmit);

    LessonSubmit getLessonDetailByLessonId(int lessonId);

    List<Chapter> getChaptersByLessonId(int lesson_id);

    List<SonChapter> getSonChapterByChapterId(int chapter_id);

    void addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    List<Lesson> findLessonsByName(String lesson_name);

    List<Tool> getAllTools();

    List<Tool> getAllToolsByName(String tool_name);

    void addTool(Tool tools);

    void updateLessonInfo(LessonSubmit lessonSubmit);

    void addChapterInEditPart(AddChapterInEdit addChapterInEdit);

    void delChapterInEdit(Integer chapter_id);

    void deleteSonChapterInEdit(Integer chapter_id);

    void deleteSonChapterInEditSingle(Integer son_id);

    void addSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    void editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    int getLessonCount();

    void updateSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    void deleteLessonByLessonId(LessonIdInfo lessonIdInfo);

    void deleteChapterByLessonId(LessonIdInfo lessonIdInfo);

    void deleteSonChapterByLessonId(LessonIdInfo lessonIdInfo);

    void deleteTagAndLesson(LessonIdInfo lessonIdInfo);

    SonChapter getSonChapterInfoBySonId(SonChapterAndUrl sonChapterAndUrl);

}
