package edu.nuist.dao;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.vo.SonUserExp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontLessonDao {

    @Select("select * from lesson where lessonId = #{param1} order by update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    Lesson getLessonInfoByLessonId(int lessonId);

    @Select("select * from lesson where lesson_name like CONCAT('%',#{searchName},'%')")
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

    @Select("select * from son_chapter where son_id = #{param1}")
    SonChapter getSonChapterBySonId(int son_id);

    @Select("select * from son_user_exp where user_id = #{user_id} and son_id = #{son_id}")
    SonUserExp isHasSonUserExpUrl(SonUserExp sonUserExp);

    @Insert("insert into son_user_exp(son_id,user_id,exp_url,lessonId) " +
            "values (#{son_id},#{user_id},#{exp_url},#{lessonId}) ")
    void addSonUserExpUrl(SonUserExp sonUserExp);

    @Select("select * from tools")
    List<Tool> getAllTools();

    @Select("select distinct lessonId from son_user_exp where user_id = #{param1}")
    List<Integer> getLessonByUserId(int userId);

    @Select("select lessonId, lesson_name, pic_url, teacher_name from lesson where lessonId = #{param1} ")
    Lesson getLessonById(int lessonId);

}
