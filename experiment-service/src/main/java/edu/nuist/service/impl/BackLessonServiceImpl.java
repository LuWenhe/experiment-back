package edu.nuist.service.impl;

import edu.nuist.dao.BackLessonDao;
import edu.nuist.dao.BackTagDao;
import edu.nuist.entity.*;
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
    private BackTagDao backTagsDao;

    @Override
    public List<Lesson> getAllLessons() {
        return backLessonDao.getAllLessons();
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
                int tagId = backTagsDao.getTagId(tag);
                TagAndLesson tagAndLesson = new TagAndLesson();
                tagAndLesson.setLessonId(lessonSubmit.getLessonId());
                tagAndLesson.setTag_id(tagId);
                backTagsDao.addTagAndLesson(tagAndLesson);

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
            List<TagAndLesson> lessonsTagByLessonId = backTagsDao.getLessonsTagByLessonId(lessonId);
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
    public List<Lesson> findLessonsByName(String lesson_name) {
        return backLessonDao.findLessonsByName(lesson_name);
    }

    @Override
    public List<Tool> getAllTools() {
        return backLessonDao.getAllTools();
    }

    @Override
    public List<Tool> getAllToolsByName(String tool_name) {
        return backLessonDao.getAllToolsByName(tool_name);
    }

    @Override
    @Transactional
    public Result addTool(Tool Tool) {
        Result result = new Result();

        try {
            backLessonDao.addTool(Tool);
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
    public Result updateLessonInfo(LessonSubmit lessonSubmit) {
        Result result = new Result();

        try {
            backTagsDao.delLessonAndTag(lessonSubmit.getLessonId());
            String[] tags = lessonSubmit.getTags();

            for (String tag : tags) {
                int tagId = backTagsDao.getTagId(tag);
                TagAndLesson tagAndLesson = new TagAndLesson();
                tagAndLesson.setLessonId(lessonSubmit.getLessonId());
                tagAndLesson.setTag_id(tagId);
                backTagsDao.addTagAndLesson(tagAndLesson);

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
        int tag_id = backTagsDao.getTagIDByTagName(tagName);
        List<TagLesson> tagLessonList = backTagsDao.getTagLessons(tag_id);
        List<Lesson> lessonList = new ArrayList<>();

        for (TagLesson tagLesson : tagLessonList) {
            lessonList.add(tagLesson.getLesson());

        }

        return lessonList;
    }

    @Override
    public Result getAllOptionList() {
        Result result = new Result();

        try {
            List<OptionList> optionListList = new ArrayList<>();
            List<Tag> tagList = backTagsDao.getAllTags();

            for (Tag tag : tagList) {
                optionListList.add(new OptionList(tag.getTagName(),tag.getTagName()));
            }

            result.setCode("200");
            result.setData(optionListList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
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
    public Result deleteLessonById(LessonIdInfo lessonIdInfo) {
        Result result = new Result();

        try {
            backLessonDao.deleteSonChapterByLessonId(lessonIdInfo);
            backLessonDao.deleteChapterByLessonId(lessonIdInfo);
            backLessonDao.deleteLessonByLessonId(lessonIdInfo);
            backLessonDao.deleteTagAndLesson(lessonIdInfo);
            result.setCode("200");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getEditSonChapterInfo(SonChapterAndUrl sonChapterAndUrl) {
        Result result = new Result();

        try {
            SonChapter sonChapter = backLessonDao.getSonChapterInfoBySonId(sonChapterAndUrl);
            result.setCode("200");
            result.setData(sonChapter);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public Result getChapterByLessonId(LessonSubmit lessonSubmit) {
        Result result = new Result();

        try {
            List<Chapter> chapterList = backLessonDao.getChaptersByLessonId(lessonSubmit.getLessonId());
            result.setCode("200");
            result.setData(chapterList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
