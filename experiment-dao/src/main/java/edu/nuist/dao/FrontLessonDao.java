package edu.nuist.dao;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.entity.UserJupyterFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontLessonDao {

    @Select("SELECT * from lesson WHERE lessonId = #{param1} order by update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    Lesson getLessonInfoByLessonId(int lessonId);

    @Select("SELECT * FROM lesson WHERE lesson_name like CONCAT('%',#{searchName},'%')")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getLessonByName(String lessonName);

    @Select("SELECT DISTINCT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.md_description, le.html_description, le.teacher_name, le.teacher_id " +
            "FROM users s, clazz c, lesson le, lesson_tag lt, tag t " +
            "WHERE s.clazz_id = c.id AND c.teacher_id = le.teacher_id AND lt.lessonId = le.lessonId " +
            "AND lt.tag_id = t.tag_id AND s.user_id = #{userId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getLessonsByUserId(Integer userId);

    @Select("SELECT DISTINCT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.md_description, le.html_description, le.teacher_name, le.teacher_id " +
            "FROM users s, clazz c, lesson le, lesson_tag lt, tag t " +
            "WHERE s.clazz_id = c.id AND c.teacher_id = le.teacher_id AND lt.lessonId = le.lessonId " +
            "AND lt.tag_id = t.tag_id AND s.user_id = #{userId} AND lt.tag_id = #{tagId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getLessonsByUserIdAndTagId(Integer userId, Integer tagId);

    @Select("SELECT * FROM son_chapter WHERE id = #{param1}")
    SonChapter getSonChapterBySonId(int son_id);

    @Select("SELECT * FROM tools")
    List<Tool> getAllTools();

    @Select("SELECT distinct lessonId FROM son_user_exp WHERE user_id = #{param1}")
    List<Integer> getLessonByUserId(int userId);

    @Select("SELECT lessonId, lesson_name, pic_url, teacher_name FROM lesson WHERE lessonId = #{param1} ")
    Lesson getLessonById(int lessonId);

    @Select("SELECT id, user_id, name, type, url, path, son_id, lesson_id, create_time, update_time " +
            "FROM jupyter_user WHERE user_id = #{userId} AND son_id = #{sonId} AND type = 1")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "son_id", property = "sonId"),
            @Result(column = "lesson_id", property = "lessonId")
    })
    UserJupyterFile getUserJupyterFile(Integer userId, Integer sonId);

    @Insert({"<script>",
            "INSERT INTO jupyter_user (user_id, name, type, url, path, son_id, lesson_id, create_time) VALUES ",
            "<foreach collection='userJupyterFiles' item='item' index='index' separator=','>",
            "(#{item.userId}, #{item.name}, #{item.type}, #{item.url}, #{item.path}, #{item.sonId}, #{item.lessonId}, CURDATE())",
            "</foreach>",
            "</script>"
    })
    void addUserJupyterFiles(List<UserJupyterFile> userJupyterFiles);

}
