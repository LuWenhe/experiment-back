package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.dto.LessonTreeDto;
import edu.nuist.entity.*;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackLessonService;
import edu.nuist.dto.ChapterDto;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;
import edu.nuist.dto.SonChapterDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackLessonServiceImpl implements BackLessonService {

    @Resource
    private BackLessonDao backLessonDao;

    @Resource
    private BackTagDao backTagDao;

    @Override
    public List<Lesson> getAllLessons() {
        return backLessonDao.getAllLessons();
    }

    @Override
    public List<Lesson> getLessonsByUserId(Integer userId, Integer roleId) {
        List<Lesson> lessons = null;

        if (roleId.equals(RoleEnum.STUDENT_ROLE.getCode())) {
            lessons = backLessonDao.getLessonsByUserId(userId);
        } else if (roleId.equals(RoleEnum.TEACHER_ROLE.getCode())) {
            lessons = backLessonDao.getLessonsByTeacherId(userId);
        }

        return lessons;
    }

    @Override
    public void addLesson(LessonSubmit lessonSubmit) {
        lessonSubmit.setLearnCredit(Double.parseDouble(lessonSubmit.getLearn_time()));
        lessonSubmit.setLearnTime(Double.parseDouble(lessonSubmit.getLearn_time()));

        backLessonDao.addLesson(lessonSubmit);
        String[] tags = lessonSubmit.getTags();

        if (tags != null) {
            for (String tag : tags) {
                int tagId = backTagDao.getTagId(tag);
                TagAndLesson tagAndLesson = new TagAndLesson();
                tagAndLesson.setLessonId(lessonSubmit.getLessonId());
                tagAndLesson.setTag_id(tagId);
                backTagDao.addTagAndLesson(tagAndLesson);
            }
        }
    }

    @Override
    public LessonSubmit getLessonById(Integer lessonId) {
        return backLessonDao.getLessonDetailByLessonId(lessonId);
    }

    @Override
    public LessonSubmit getLessonDetail(int lessonId) {
        LessonSubmit lessonSubmit = backLessonDao.getLessonDetailByLessonId(lessonId);
        List<TagAndLesson> lessonsTagByLessonId = backTagDao.getLessonsTagByLessonId(lessonId);
        String[] tags = new String[lessonsTagByLessonId.size()];

        for (int i = 0; i < lessonsTagByLessonId.size(); i++) {
            tags[i] = lessonsTagByLessonId.get(i).getTag().getTagName();
        }

        lessonSubmit.setTags(tags);
        return lessonSubmit;
    }

    @Override
    public void addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl) {
        backLessonDao.addSonChapterJupyterURL(sonChapterAndUrl);
    }

    @Override
    public List<Lesson> findLessonsByName(Integer teacherId, String lessonName) {
        return backLessonDao.findLessonsByName(teacherId, lessonName);
    }

    @Override
    public void updateLesson(LessonSubmit lessonSubmit) {
        backTagDao.delLessonAndTag(lessonSubmit.getLessonId());
        String[] tags = lessonSubmit.getTags();

        for (String tag : tags) {
            int tagId = backTagDao.getTagId(tag);
            TagAndLesson tagAndLesson = new TagAndLesson();
            tagAndLesson.setLessonId(lessonSubmit.getLessonId());
            tagAndLesson.setTag_id(tagId);
            backTagDao.addTagAndLesson(tagAndLesson);
        }

        backLessonDao.updateLessonInfo(lessonSubmit);
    }

    @Override
    public Integer addChapter(ChapterDto chapterDto) {
        backLessonDao.addChapter(chapterDto);
        return chapterDto.getId();
    }

    @Override
    public Chapter getChapterByChapterId(Integer chapterId) {
        return backLessonDao.getChapterByChapterId(chapterId);
    }

    @Override
    public void updateChapter(ChapterDto chapterDto) {
        backLessonDao.updateChapter(chapterDto);
    }

    @Override
    public void deleteChapters(Integer chapterId) {
        backLessonDao.deleteChapters(chapterId);
    }

    @Override
    public void deleteChaptersByLessonId(int lessonId) {
        backLessonDao.deleteChaptersByLessonId(lessonId);
    }

    @Override
    public void deleteSonChapterById(Integer son_id) {
        backLessonDao.deleteSonChapterById(son_id);
    }

    @Override
    public void deleteSonChaptersByChapterId(Integer chapterId) {
        backLessonDao.deleteSonChapterByChapterId(chapterId);
    }

    @Override
    public void deleteSonChaptersByLessonId(Integer lessonId) {
        backLessonDao.deleteSonChaptersByLessonId(lessonId);
    }

    @Override
    public void addSonChapter(SonChapterDto sonChapterDto) {
        backLessonDao.addSonChapter(sonChapterDto);
    }

    @Override
    public void updateSonChapter(SonChapterDto sonChapterDto) {
        backLessonDao.updateSonChapter(sonChapterDto);
    }

    @Override
    public List<Lesson> getLessonsByTag(String tagName) {
        int tag_id = backTagDao.getTagIDByTagName(tagName);
        List<TagLesson> tagLessonList = backTagDao.getTagLessons(tag_id);
        List<Lesson> lessonList = new ArrayList<>();

        for (TagLesson tagLesson : tagLessonList) {
            lessonList.add(tagLesson.getLesson());
        }

        return lessonList;
    }

    @Override
    public void addSonChapterBook(SonChapterAndUrl sonChapterAndUrl) {
        backLessonDao.updateSonChapterBook(sonChapterAndUrl);
    }

    @Override
    public void deleteLessonById(Integer lessonId) {
        backLessonDao.deleteLessonByLessonId(lessonId);
    }

    @Override
    public SonChapterDto getSonChapter(Integer sonId) {
        return backLessonDao.getSonChapterBySonId(sonId);
    }

    @Override
    public List<Chapter> getChaptersByLessonId(Integer lessonId) {
        return backLessonDao.getChaptersByLessonId(lessonId);
    }

    @Override
    public List<LessonTreeDto> getLessonTree(Integer lessonId) {
        List<LessonTreeDto> lessonFileMenu = backLessonDao.getLessonTree(lessonId);

        LessonTreeDto root = new LessonTreeDto(1, "root", null, 0, 0, 1,
                0, 0, 0, null, null, null);
        lessonFileMenu.add(root);

        return lessonFileMenu;
    }

    @Override
    public List<JupyterFile> getJupyterFiles(Integer sonId) {
        return backLessonDao.getJupyterFiles(sonId);
    }

    @Override
    public void addJupyterFiles(List<JupyterFile> jupyterFiles) {
        backLessonDao.addJupyterFiles(jupyterFiles);
    }

    @Override
    public List<JupyterFile> getJupyterIdsByLessonId(Integer lessonId) {
        return backLessonDao.getJupyterIdsByLessonId(lessonId);
    }

    @Override
    public List<JupyterFile> getJupyterIdsByChapterId(Integer chapterId) {
        return backLessonDao.getJupyterIdsByChapterId(chapterId);
    }

    @Override
    public void updateJupyterFile(List<JupyterFile> jupyterFiles) {
        backLessonDao.updateJupyterFileUrl(jupyterFiles);
    }

    @Override
    public void deleteJupyterFilesByIds(List<Integer> jupyterIds) {
        backLessonDao.deleteJupyterFilesByIds(jupyterIds);
    }

    @Override
    public void deleteJupyterFilesBySonId(Integer sonId) {
        backLessonDao.deleteJupyterFilesBySonId(sonId);
    }

    @Override
    public void deleteJupyterFilesByLessonId(Integer lessonId) {
        backLessonDao.deleteJupyterFilesByLessonId(lessonId);
    }

}
