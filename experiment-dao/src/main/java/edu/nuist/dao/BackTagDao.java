package edu.nuist.dao;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Tag;
import edu.nuist.entity.TagAndLesson;
import edu.nuist.entity.TagLesson;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackTagDao {

    @Select("select tag_id from tag where tagName = #{tagName}")
    int getTagId(String tagName);

    @Insert("insert into lesson_tag (tag_id,lessonId) values (#{tag_id},#{lessonId}) ")
    void addTagAndLesson(TagAndLesson tagAndLesson);

    @Select("select * from lesson_tag where lessonId = #{param1} ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "lessonId", property = "lessonId"),
            @Result(column = "tag_id", property = "tag_id"),
            @Result(property = "tag", column = "tag_id",
                    one = @One(select = "edu.nuist.dao.BackTagDao.getTagById"))

    })
    List<TagAndLesson> getLessonsTagByLessonId(int lessonId);

    @Select("select tag_id ,tagName from tag where tag_id = #{param1}")
    Tag getTagById(int tag_id);

    @Delete("delete from lesson_tag where lessonId = #{param1}")
    void delLessonAndTag(int lesson_id);

    @Select("select tag_id from tag where tagName = #{param1}")
    int getTagIDByTagName(String tagName);

    @Select("select * from lesson_tag where tag_id = #{param1}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "lessonId", property = "lessonId"),
            @Result(property = "lesson",column = "lessonId",
                    one = @One(select = "edu.nuist.dao.BackTagDao.getLessonByLessonId")),
            @Result(column = "tag_id", property = "tag_id")

    })
    List<TagLesson> getTagLessons(int tag_id);

    @Select("select * from lesson where lessonId = #{param1}")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    Lesson getLessonByLessonId(int lessonId);

    @Select("select tag_id, tagName from tag")
    List<Tag> getTags();

    @Insert("insert into tag(tagName) values (#{tagName}) ")
    void addTag(Tag tag);

    @Update("update tag set tagName = #{tagName} where tag_id = #{tag_id}")
    void updateTage(Tag tag);

    @Delete("delete from tag where tag_id = #{tagId}")
    void delTag(Integer tagId);

    @Select("select count(*) from lesson_tag where tag_id = #{tagId}")
    int findLessonTagNum(Integer tagId);

    @Select("select count(*) from tag")
    int getTagNum();

}
