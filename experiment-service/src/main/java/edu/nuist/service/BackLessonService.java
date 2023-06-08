package edu.nuist.service;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.vo.AddChapterInEdit;
import edu.nuist.vo.AddSonChapterInEdit;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;

import java.util.List;

public interface BackLessonService {

    List<Lesson> getAllLessons();

    Result addLesson(LessonSubmit lessonSubmit);

    Result getLessonDetail(int lessonId);

    Result getChaptersInfoByLessonId(int lesson_id);

    Result addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    List<Lesson> findLessonsByName(String lesson_name);

    Result updateLessonInfo(LessonSubmit lessonSubmit);

    Result AddChapterInEditPart(AddChapterInEdit addChapterInEdit);

    Result delChapterInEdit(Integer chapter_id);

    Result delSonChapterInEdit(Integer son_id);

    Result AddSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    Result editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    List<Lesson> getAllLessonsByTag(String tagName);

    Result addSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    Result deleteLessonById(Integer lessonId);

    Result getEditSonChapterInfo(Integer sonId);

    Result getChapterByLessonId(Integer lessonId);

}
