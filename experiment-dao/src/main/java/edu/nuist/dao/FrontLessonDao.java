package edu.nuist.dao;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.vo.SonUserExp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontLessonDao {

    @Select("SELECT * from lesson ")
    List<Lesson> getAllLessons();

    @Select("select * from lesson where lessonId = #{param1}")
    Lesson getLessonInfoByLessonId(int lessonId);

    @Select("select * from lesson where lesson_name like CONCAT('%',#{searchName},'%')")
    List<Lesson> getLessonByName(String lessonName);

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

    @Select("select lessonId,lesson_name,pic_url,teacher_name from lesson where lessonId = #{param1} ")
    Lesson getLessonById(int lessonId);

}
