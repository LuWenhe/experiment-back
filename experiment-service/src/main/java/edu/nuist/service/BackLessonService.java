package edu.nuist.service;

import edu.nuist.entity.Chapter;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.vo.AddChapterInEdit;
import edu.nuist.vo.AddSonChapterInEdit;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;

import java.util.List;

public interface BackLessonService {

    List<Lesson> getAllLessons();

    List<Lesson> getLessonsByUserId(Integer userId, Integer roleId);

    void addLesson(LessonSubmit lessonSubmit);

    LessonSubmit getLessonDetail(int lessonId);

    List<Chapter> getChaptersInfoByLessonId(int lesson_id);

    void addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    List<Lesson> findLessonsByName(Integer teacherId, String lessonName);

    void updateLessonInfo(LessonSubmit lessonSubmit);

    List<Chapter> AddChapterInEditPart(AddChapterInEdit addChapterInEdit);

    void delChapterInEdit(Integer chapter_id);

    void delSonChapterInEdit(Integer son_id);

    void addSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    void editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    List<Lesson> getAllLessonsByTag(String tagName);

    void addSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    void deleteLessonById(Integer lessonId);

    SonChapter getEditSonChapterInfo(Integer sonId);

    List<Chapter> getChapterByLessonId(Integer lessonId);

}
