package edu.nuist.dao;

import edu.nuist.dto.ChapterDto;
import edu.nuist.dto.LessonTreeDto;
import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.Chapter;
import edu.nuist.entity.JupyterFile;
import edu.nuist.entity.Lesson;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.SonChapterAndUrl;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BackLessonDao {

    @Select("SELECT lessonId, lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, canLearn, " +
            "dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id, path from lesson " +
            "order by update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription"),
    })
    List<Lesson> getAllLessons();

    /**
     * 根据教师id获取教师所教的所有课程
     */
    @Select("SELECT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.dagang, le.cankao, le.goal, le.md_description, le.html_description, " +
            "le.teacher_name, le.teacher_id, le.path FROM users s INNER JOIN lesson le ON s.user_id = le.teacher_id " +
            "WHERE s.user_id = #{teacherId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription"),
    })
    List<Lesson> getLessonsByTeacherId(Integer teacherId);

    /**
     * 根据学生id获取学生所学的所有课程
     */
    @Select("SELECT le.lessonId, le.lesson_name, le.pic_url, le.difficulty, le.learn_time, le.learn_credit, " +
            "le.suitablePerson, le.canLearn, le.dagang, le.cankao, le.goal, le.md_description, le.html_description, " +
            "le.teacher_name, le.teacher_id, le.path FROM users s INNER JOIN clazz c ON s.clazz_id = c.id " +
            "INNER JOIN lesson le ON c.teacher_id = le.teacher_id WHERE s.user_id = #{userId} order by le.update_time desc")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription"),
    })
    List<Lesson> getLessonsByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "lessonId", keyColumn = "lessonId")
    @Insert("insert into lesson(lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, " +
            "canLearn, dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id, path) " +
            "values(#{lesson_name}, #{pic_url}, #{difficulty}, #{learn_time}, #{learn_credit}, #{suitablePerson}, " +
            "#{canLearn}, #{dagang}, #{cankao}, #{goal}, #{mdDescription}, #{htmlDescription}, #{teacher_name}, " +
            "#{teacherId}, #{path})")
    @Results({
            @Result(column = "teacher_id", property = "teacherId"),
    })
    void addLesson(LessonSubmit lessonSubmit);

    @Select("SELECT lessonId, lesson_name, pic_url, difficulty, learn_time, learn_credit, suitablePerson, canLearn, " +
            "dagang, cankao, goal, md_description, html_description, teacher_name, teacher_id, path from lesson " +
            "where lessonId = #{param1}")
    @Results({
            @Result(column = "md_description", property = "mdDescription"),
            @Result(column = "html_description", property = "htmlDescription"),
            @Result(column = "teacher_id", property = "teacherId"),
    })
    LessonSubmit getLessonDetailByLessonId(int lessonId);

    @Select("select * from chapter where lessonId = #{param1}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "sonChapterList", column = "id",
                    one = @One(select = "edu.nuist.dao.BackLessonDao.getSonChapterByChapterId")),
            @Result(column = "chapter_no", property = "chapter_no"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "mp4", property = "mp4"),
            @Result(column = "ppt", property = "ppt"),
            @Result(column = "exp_type", property = "exp_type"),
            @Result(column = "guide_book", property = "guide_book")
    })
    List<Chapter> getChaptersByLessonId(int lesson_id);

    @Select("SELECT c.id, c.chapter_no, c.name, c.description, l.lesson_name FROM chapter c, lesson l " +
            "WHERE c.lessonId = l.lessonId AND c.id = #{chapterId}")
    @Results({
            @Result(column = "lesson_name", property = "lessonName")
    })
    Chapter getChapterByChapterId(Integer chapterId);

    @Select("select * from son_chapter where chapter_id = #{param1}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "jupyterFile", column = "id",
                    one = @One(select = "edu.nuist.dao.BackLessonDao.getJupyterFileBySonId"))
    })
    List<SonChapterDto> getSonChapterByChapterId(int chapter_id);

    @Update("update son_chapter set exp_url = #{exp_url} where id = #{son_id}")
    void addSonChapterJupyterURL(SonChapterAndUrl sonChapterAndUrl);

    @Select("select lessonId,lesson_name, pic_url from lesson where teacher_id = #{teacherId} " +
            "and lesson_name like CONCAT('%', #{lessonName},'%')")
    List<Lesson> findLessonsByName(Integer teacherId, String lessonName);

    @Update("update lesson set lesson_name = #{lesson_name}, pic_url = #{pic_url}, difficulty = #{difficulty}," +
            "learn_time = #{learn_time}, learn_credit = #{learn_credit}, suitablePerson = #{suitablePerson}, " +
            "canLearn = #{canLearn}, dagang = #{dagang}, cankao = #{cankao}, goal = #{goal}, " +
            "md_description = #{mdDescription}, html_description = #{htmlDescription}, teacher_name = #{teacher_name}, " +
            "path = #{path} where lessonId = #{lessonId}")
    void updateLessonInfo(LessonSubmit lessonSubmit);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into chapter(chapter_no, name, description, lessonId, path) " +
            "values (#{chapter_no}, #{name}, #{description}, #{lessonId}, #{path})")
    void addChapter(ChapterDto chapterDto);

    @Update("update chapter set chapter_no = #{chapter_no}, name = #{name}, description = #{description}, " +
            "path = #{path} where id = #{id}")
    void updateChapter(ChapterDto chapterDto);

    @Delete("delete from chapter where id = #{id}")
    void deleteChapters(Integer id);

    @Delete("DELETE FROM chapter WHERE lessonId = #{lessonId}")
    void deleteChaptersByLessonId(int lessonId);

    @Delete("delete from son_chapter where chapter_id = #{chapterId}")
    void deleteSonChapterByChapterId(Integer chapterId);

    @Delete("delete from son_chapter where id = #{id}")
    void deleteSonChapterById(Integer id);

    @Delete("DELETE FROM son_chapter where lessonId = #{lessonId}")
    void deleteSonChaptersByLessonId(Integer lessonId);

    @Insert("insert into son_chapter(son_no, name, description, exp_type, chapter_id, mp4, ppt, lessonId, path) " +
            "values (#{son_no}, #{name}, #{description}, 'Jupyter', #{chapter_id}, #{mp4}, #{ppt}, #{lessonId}, #{path}) ")
    void addSonChapter(SonChapterDto sonChapterDto);

    @Update("update son_chapter set son_no = #{son_no}, name = #{name}, description " +
            "= #{description}, ppt = #{ppt}, mp4= #{mp4}, path = #{path} where id = #{id} ")
    void updateSonChapter(SonChapterDto sonChapterDto);

    @Select("select count(*) from lesson")
    int getLessonCount();

    @Update("update son_chapter set guide_book = #{guide_book} where id = #{son_id}")
    void updateSonChapterBook(SonChapterAndUrl sonChapterAndUrl);

    @Delete("delete from lesson where lessonId = #{lessonId}")
    void deleteLessonByLessonId(Integer lessonId);

    @Delete("delete from chapter where lessonId = #{lessonId}")
    void deleteChapterByLessonId(Integer lessonId);

    @Delete("delete from son_chapter where lessonId = #{lessonId}")
    void deleteSonChapterByLessonId(Integer lessonId);

    @Delete("delete from lesson_tag where lessonId = #{lessonId}")
    void deleteTagAndLesson(Integer lessonId);

    @Select("SELECT s.id, s.son_no, s.name name, s.description, s.mp4, s.ppt, l.lesson_name lessonName, " +
            "c.name chapterName FROM son_chapter s, chapter c, lesson l " +
            "WHERE s.chapter_id = c.id AND c.lessonId = l.lessonId AND s.id = #{sonId}")
    SonChapterDto getSonChapterBySonId(Integer sonId);

    @Select("SELECT id, name, path, parent_id, level, leaf, lesson_id, chapter_id, son_id, create_time, update_time " +
            "FROM lesson_file WHERE lesson_id = #{lessonId}")
    @Results({
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "lesson_id", property = "lessonId"),
            @Result(column = "chapter_id", property = "chapterId"),
            @Result(column = "son_id", property = "sonId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<LessonTreeDto> getLessonTree(Integer lessonId);

    @Select("SELECT id, name, url, path, son_id, create_time, update_time FROM jupyter " +
            "WHERE son_id = #{sonId} AND type = 1")
    @Results({
            @Result(column = "son_id", property = "sonId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    JupyterFile getJupyterFileBySonId(Integer sonId);

    @Select("SELECT id, name, url, path, son_id, create_time, update_time FROM jupyter WHERE son_id = #{sonId}")
    @Results({
            @Result(column = "son_id", property = "sonId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<JupyterFile> getJupyterFiles(Integer sonId);

    @Insert({
            "<script>",
            "INSERT INTO jupyter (name, url, path, son_id, lesson_id) VALUES ",
            "<foreach collection='jupyterFiles' item='item' index='index' separator=','>",
            "(#{item.name}, #{item.url}, #{item.path}, #{item.sonId}, #{item.lessonId})",
            "</foreach>",
            "</script>"
    })
    void addJupyterFiles(List<JupyterFile> jupyterFiles);

    @Update({
            "<script>",
            "<foreach collection='jupyterFiles' item='item' index='index' separator=';'>",
            "UPDATE jupyter SET url = #{item.url} WHERE id = #{item.id} ",
            "</foreach>",
            "</script>"
    })
    void updateJupyterFileUrl(List<JupyterFile> jupyterFiles);

    @Delete({"<script>",
            "DELETE FROM jupyter WHERE id in (",
            "<foreach collection='jupyterIds' item='jupyterId' index='index' separator=','>",
            "#{jupyterId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteJupyterFilesByIds(List<Integer> jupyterIds);

    @Delete("DELETE FROM jupyter WHERE son_id = #{sonId}")
    void deleteJupyterFilesBySonId(Integer sonId);

    @Delete("DELETE FROM jupyter WHERE lesson_id = #{lessonId}")
    void deleteJupyterFilesByLessonId(Integer lessonId);

    @Select("SELECT j.id, j.url FROM lesson l, chapter c, son_chapter s, jupyter j WHERE l.lessonId = c.lessonId " +
            "AND c.id = s.chapter_id AND s.id = j.son_id AND l.lessonId = #{lessonId}")
    List<JupyterFile> getJupyterIdsByLessonId(Integer lessonId);

    @Select("SELECT j.id, j.url FROM chapter c, son_chapter s, jupyter j WHERE " +
            "c.id = s.chapter_id AND s.id = j.son_id AND c.id = #{chapterId}")
    List<JupyterFile> getJupyterIdsByChapterId(Integer chapterId);

}
