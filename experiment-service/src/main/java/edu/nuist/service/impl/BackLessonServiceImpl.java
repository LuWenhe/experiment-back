package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.*;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackLessonService;
import edu.nuist.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    @Transactional
    public Result addLesson(LessonSubmit lessonSubmit) {
        Result result = new Result();

        try {
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

            result.setCode("200");
            result.setData(lessonSubmit.getLessonId());
        } catch (NumberFormatException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getLessonDetail(int lessonId) {
        Result result = new Result();

        try {
            LessonSubmit lessonDetailByLessonId = backLessonDao.getLessonDetailByLessonId(lessonId);
            List<TagAndLesson> lessonsTagByLessonId = backTagDao.getLessonsTagByLessonId(lessonId);
            String[] tags = new String[lessonsTagByLessonId.size()];

            for (int i = 0; i < lessonsTagByLessonId.size(); i++) {
                tags[i] = lessonsTagByLessonId.get(i).getTag().getTagName();
            }

            lessonDetailByLessonId.setTags(tags);
            result.setData(lessonDetailByLessonId);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getChaptersInfoByLessonId(int lesson_id) {
        Result result = new Result();

        try {
            List<Chapter> chapterList = backLessonDao.getChaptersByLessonId(lesson_id);
            result.setCode("200");
            result.setData(chapterList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl) {
        Result result = new Result();

        try {
            backLessonDao.addSonChapterJupyterURL(sonChapterAndUrl);
            result.setCode("200");
            result.setData(sonChapterAndUrl);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public List<Lesson> findLessonsByName(Integer teacherId, String lessonName) {
        return backLessonDao.findLessonsByName(teacherId, lessonName);
    }

    @Override
    @Transactional
    public Result updateLessonInfo(LessonSubmit lessonSubmit) {
        Result result = new Result();

        try {
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
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddChapterInEditPart(AddChapterInEdit addChapterInEdit) {
        Result result = new Result();

        try {
            backLessonDao.addChapterInEditPart(addChapterInEdit);
            List<Chapter> chapterList = backLessonDao.getChaptersByLessonId(addChapterInEdit.getLessonId());
            result.setCode("200");
            result.setData(chapterList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result delChapterInEdit(Integer chapter_id) {
        Result result = new Result();

        try {
            backLessonDao.delChapterInEdit(chapter_id);
            backLessonDao.deleteSonChapterInEdit(chapter_id);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result delSonChapterInEdit(Integer son_id) {
        Result result = new Result();

        try {
            backLessonDao.deleteSonChapterInEditSingle(son_id);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit) {
        Result result = new Result();

        try {
            backLessonDao.addSonChapterInEdit(addSonChapterInEdit);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit) {
        Result result = new Result();

        try {
            backLessonDao.editSonChapterInEdit(addSonChapterInEdit);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }


    @Override
    public List<Lesson> getAllLessonsByTag(String tagName) {
        int tag_id = backTagDao.getTagIDByTagName(tagName);
        List<TagLesson> tagLessonList = backTagDao.getTagLessons(tag_id);
        List<Lesson> lessonList = new ArrayList<>();

        for (TagLesson tagLesson : tagLessonList) {
            lessonList.add(tagLesson.getLesson());
        }

        return lessonList;
    }

    @Override
    @Transactional
    public Result addSonChapterBook(SonChapterAndUrl sonChapterAndUrl) {
        Result result = new Result();

        try {
            backLessonDao.updateSonChapterBook(sonChapterAndUrl);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    @Transactional
    public Result deleteLessonById(Integer lessonId) {
        Result result = new Result();

        try {
            backLessonDao.deleteSonChapterByLessonId(lessonId);
            backLessonDao.deleteChapterByLessonId(lessonId);
            backLessonDao.deleteLessonByLessonId(lessonId);
            backLessonDao.deleteTagAndLesson(lessonId);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getEditSonChapterInfo(Integer sonId) {
        Result result = new Result();

        try {
            SonChapter sonChapter = backLessonDao.getSonChapterInfoBySonId(sonId);
            result.setCode("200");
            result.setData(sonChapter);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getChapterByLessonId(Integer lessonId) {
        Result result = new Result();

        try {
            List<Chapter> chapterList = backLessonDao.getChaptersByLessonId(lessonId);
            result.setCode("200");
            result.setData(chapterList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
