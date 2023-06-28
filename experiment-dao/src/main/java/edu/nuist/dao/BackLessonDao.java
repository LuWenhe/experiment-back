package edu.nuist.dao;

import edu.nuist.entity.Chapter;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.vo.AddChapterInEdit;
import edu.nuist.vo.AddSonChapterInEdit;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackLessonDao {

    @Select("SELECT lessonId, lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, canLearn, " +
            "dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id from lesson " +
            "order by update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getAllLessons();

    /**
     * 根据教师id获取教师所教的所有课程
     */
    @Select("SELECT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.dagang, le.cankao, le.goal, le.md_description, le.html_description, " +
            "le.teacher_name, le.teacher_id FROM users s INNER JOIN lesson le ON s.user_id = le.teacher_id " +
            "WHERE s.user_id = #{teacherId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getLessonsByTeacherId(Integer teacherId);

    /**
     * 根据学生id获取学生所学的所有课程
     */
    @Select("SELECT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.dagang, le.cankao, le.goal, le.md_description, le.html_description, " +
            "le.teacher_name, le.teacher_id FROM users s INNER JOIN clazz c ON s.clazz_id = c.id " +
            "INNER JOIN lesson le ON c.teacher_id = le.teacher_id WHERE s.user_id = #{userId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription")
    })
    List<Lesson> getLessonsByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "lessonId", keyColumn = "lessonId")
    @Insert("insert into lesson(lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, " +
            "canLearn, dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id) " +
            "values(#{lesson_name}, #{pic_url}, #{difficulty}, #{learn_time}, #{learn_credit}, #{suitablePerson}, " +
            "#{canLearn}, #{dagang}, #{cankao}, #{goal}, #{mdDescription}, #{htmlDescription}, #{teacher_name}, " +
            "#{teacherId})")
    @Results({
            @Result(column = "teacher_id", property = "teacherId")
    })
    void addLesson(LessonSubmit lessonSubmit);

    @Select("SELECT lessonId, lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, canLearn, " +
            "dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id from lesson " +
            "where lessonId = #{param1}")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription"),
            @Result(column = "teacher_id", property = "teacherId")
    })
    LessonSubmit getLessonDetailByLessonId(int lessonId);

    @Select("select * from chapter where lessonId = #{param1}")
    @Results({
            @Result(id = true, column = "chapter_id", property = "chapter_id"),
            @Result(property = "sonChapterList",column = "chapter_id",
                    one = @One(select = "edu.nuist.dao.BackLessonDao.getSonChapterByChapterId")),
            @Result(column = "chapter_no", property = "chapter_no"),
            @Result(column = "chapter_name", property = "chapter_name"),
            @Result(column = "description", property = "description"),
            @Result(column = "mp4", property = "mp4"),
            @Result(column = "ppt", property = "ppt"),
            @Result(column = "exp_type", property = "exp_type"),
            @Result(column = "guide_book", property = "guide_book")
    })
    List<Chapter> getChaptersByLessonId(int lesson_id);

    @Select("select * from son_chapter where chapter_id = #{param1}")
    List<SonChapter> getSonChapterByChapterId(int chapter_id);

    @Update("update son_chapter set exp_url = #{exp_url} where son_id = #{son_id}")
    void addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    @Select("select lessonId,lesson_name, pic_url from lesson where teacher_id = #{teacherId} " +
            "and lesson_name like CONCAT('%', #{lessonName},'%')")
    List<Lesson> findLessonsByName(Integer teacherId, String lessonName);

    @Update("update lesson set lesson_name = #{lesson_name}, pic_url = #{pic_url}, difficulty = #{difficulty}," +
            "learn_time = #{learn_time}, learn_credit = #{learn_credit}, suitablePerson = #{suitablePerson}, " +
            "canLearn = #{canLearn}, dagang = #{dagang}, cankao = #{cankao}, goal = #{goal}, " +
            "md_description = #{mdDescription}, html_description = #{htmlDescription}, teacher_name = #{teacher_name} " +
            "where lessonId = #{lessonId}")
    void updateLessonInfo(LessonSubmit lessonSubmit);

    @Insert("insert into chapter(chapter_no,chapter_name,description,lessonId) " +
            "values (#{chapter_no},#{chapter_name},#{description},#{lessonId}) ")
    void addChapterInEditPart(AddChapterInEdit addChapterInEdit);

    @Update("update chapter set chapter_no = #{chapter_no}, chapter_name = #{chapter_name}, " +
            "description = #{description} where chapter_id = #{chapter_id}")
    void updateChapter(Chapter chapter);

    @Delete("delete from chapter where chapter_id = #{param1}")
    void delChapterInEdit(Integer chapter_id);

    @Delete("delete from son_chapter where chapter_id = #{param1}")
    void deleteSonChapterInEdit(Integer chapter_id);

    @Delete("delete from son_chapter where son_id = #{param1}")
    void deleteSonChapterInEditSingle(Integer son_id);

    @Insert("insert into son_chapter(son_no,son_name,description,exp_type,chapter_id,mp4,ppt,lessonId) " +
            "values (#{son_no}, #{son_name},#{description},'Jupyter',#{chapter_id},#{mp4},#{ppt},#{lessonId}) ")
    void addSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    @Update("update son_chapter set son_no = #{son_no}, son_name = #{son_name},description " +
            "= #{description}, ppt = #{ppt}, mp4= #{mp4} where son_id = #{son_id} ")
    void editSonChapterInEdit(AddSonChapterInEdit addSonChapterInEdit);

    @Select("select count(*) from lesson")
    int getLessonCount();

    @Update("update son_chapter set guide_book = #{guide_book} where son_id = #{son_id}")
    void updateSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    @Delete("delete from lesson where lessonId = #{lessonId}")
    void deleteLessonByLessonId(Integer lessonId);

    @Delete("delete from chapter where lessonId = #{lessonId}")
    void deleteChapterByLessonId(Integer lessonId);

    @Delete("delete from son_chapter where lessonId = #{lessonId}")
    void deleteSonChapterByLessonId(Integer lessonId);

    @Delete("delete from lesson_tag where lessonId = #{lessonId}")
    void deleteTagAndLesson(Integer lessonId);

    @Select("select son_id,son_no,son_name,description,mp4,ppt from son_chapter where son_id = #{sonId}")
    SonChapter getSonChapterInfoBySonId(Integer sonId);

}
