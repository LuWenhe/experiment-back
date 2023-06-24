package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.*;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackLessonService;
import edu.nuist.vo.AddChapterInEdit;
import edu.nuist.vo.AddSonChapterInEdit;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        for (String tag : tags) {
            int tagId = backTagDao.getTagId(tag);
            TagAndLesson tagAndLesson = new TagAndLesson();
            tagAndLesson.setLessonId(lessonSubmit.getLessonId());
            tagAndLesson.setTag_id(tagId);
            backTagDao.addTagAndLesson(tagAndLesson);
        }
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
    public List<Chapter> getChaptersInfoByLessonId(int lesson_id) {
        return backLessonDao.getChaptersByLessonId(lesson_id);
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
    public void updateLessonInfo(LessonSubmit lessonSubmit) {
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
    public List<Chapter> addChapterInEditPart(AddChapterInEdit addChapterInEdit) {
        backLessonDao.addChapterInEditPart(addChapterInEdit);
        return backLessonDao.getChaptersByLessonId(addChapterInEdit.getLessonId());
    }

    @Override
    public void updateChapter(Chapter chapter) {
        backLessonDao.updateChapter(chapter);
    }

    @Override
    public void delChapterInEdit(Integer chapter_id) {
        backLessonDao.delChapterInEdit(chapter_id);
        backLessonDao.deleteSonChapterInEdit(chapter_id);
    }

    @Override
    public void delSonChapterInEdit(Integer son_id) {
        backLessonDao.deleteSonChapterInEditSingle(son_id);
    }

    @Override
    public void addSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit) {
        backLessonDao.addSonChapterInEdit(addSonChapterInEdit);
    }

    @Override
    public void editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit) {
        backLessonDao.editSonChapterInEdit(addSonChapterInEdit);
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
    @Transactional
    public void deleteLessonById(Integer lessonId) {
        backLessonDao.deleteSonChapterByLessonId(lessonId);
        backLessonDao.deleteChapterByLessonId(lessonId);
        backLessonDao.deleteLessonByLessonId(lessonId);
        backLessonDao.deleteTagAndLesson(lessonId);
    }

    @Override
    public SonChapter getEditSonChapterInfo(Integer sonId) {
        return backLessonDao.getSonChapterInfoBySonId(sonId);
    }

    @Override
    public List<Chapter> getChapterByLessonId(Integer lessonId) {
        return backLessonDao.getChaptersByLessonId(lessonId);
    }

}
