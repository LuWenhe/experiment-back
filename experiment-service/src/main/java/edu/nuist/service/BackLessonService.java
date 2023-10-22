package edu.nuist.service;

import edu.nuist.dto.ChapterDto;
import edu.nuist.dto.LessonTreeDto;
import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.Chapter;
import edu.nuist.entity.JupyterFile;
import edu.nuist.entity.Lesson;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;

import java.util.List;

public interface BackLessonService {

    List<Lesson> getAllLessons();

    List<Lesson> getLessonsByUserId(Integer userId, Integer roleId);

    void addLesson(LessonSubmit lessonSubmit);

    LessonSubmit getLessonDetail(int lessonId);

    List<Lesson> findLessonsByName(Integer teacherId, String lessonName);

    void updateLesson(LessonSubmit lessonSubmit);

    void addChapter(ChapterDto chapterDto);

    Chapter getChapterByChapterId(Integer chapterId);

    void updateChapter(ChapterDto chapterDto);

    void deleteChapters(Integer chapterId);

    void deleteChaptersByLessonId(int lessonId);

    void deleteSonChapterById(Integer son_id);

    void deleteSonChaptersByChapterId(Integer chapterId);

    void deleteSonChaptersByLessonId(Integer lessonId);

    void addSonChapter(SonChapterDto sonChapterDto);

    void updateSonChapter(SonChapterDto sonChapterDto);

    List<Lesson> getLessonsByTag(String tagName);

    List<Integer> getLessonIdsByTagId(Integer tagId);

    void addSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    void deleteLessonById(Integer lessonId);

    SonChapterDto getSonChapter(Integer sonId);

    List<Chapter> getChaptersByLessonId(Integer lessonId);

    List<LessonTreeDto> getLessonTree(Integer lessonId);

    List<JupyterFile> getJupyterFiles(Integer sonId);

    List<JupyterFile> getJupyterIdsByLessonId(Integer lessonId);

    List<JupyterFile> getJupyterIdsByChapterId(Integer chapterId);

    void addJupyterFiles(List<JupyterFile> jupyterFiles);

    void updateJupyterFile(List<JupyterFile> jupyterFiles);

    void deleteJupyterFilesByIds(List<Integer> jupyterIds);

    void deleteJupyterFilesBySonId(Integer sonId);

    void deleteJupyterFilesByLessonId(Integer lessonId);

}
