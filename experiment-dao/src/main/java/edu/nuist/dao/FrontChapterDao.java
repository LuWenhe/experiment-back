package edu.nuist.dao;

import edu.nuist.entity.Chapter;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontChapterDao {

    @Select("select chapter_id chapterId, chapter_no chapterNo, chapter_name chapterName, description, " +
            "mp4, ppt, exp_type expType, parentId, guide_book guideBook, lessonId  from chapter")
    List<Chapter> getAllChapters();

    @Select("select chapter_id, chapter_no, chapter_name, description, mp4, ppt, " +
            "exp_type, parentId, guide_book, lessonId from chapter where lessonId = #{param1}")
    @Results({
            @Result(id = true, column = "chapter_id", property = "chapterId"),
            @Result(column = "chapter_no", property = "chapterNo"),
            @Result(column = "chapter_name", property = "chapterName"),
            @Result(column = "exp_type", property = "expType"),
            @Result(column = "guide_book", property = "guideBook"),
            @Result(column = "lessonId", property = "lessonId"),
    })
    List<Chapter> getChaptersByLessonId(Integer lessonId);

}
