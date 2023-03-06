package edu.nuist.dao;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Tag;
import edu.nuist.entity.TagAndLesson;
import edu.nuist.entity.TagLesson;

import java.util.List;

public interface BackTagDao {

    int getTagId(String tagName);

    void addTagAndLesson(TagAndLesson tagAndLesson);

    List<TagAndLesson> getLessonsTagByLessonId(int lessonId);

    Tag getTagById(int tag_id);

    void delLessonAndTag(int lesson_id);

    int getTagIDByTagName(String tagName);

    List<TagLesson> getTagLessons(int tag_id);

    Lesson getLessonByLessonId(int lessonId);

    List<Tag> getAllTags();

    void addTag(Tag tag);

    void updateTage(Tag tag);

    void delTag(Tag tag);

    int findLessonTagNum(Tag tag);

    int getTagNum();

}
