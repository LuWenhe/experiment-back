package edu.nuist.dao;

import edu.nuist.entity.LessonTag;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonTagDao {

    @Select("select id, lesson_id, tag_id from lesson_tag " +
            "where lessonId = #{param1}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "lessonId", property = "lessonId"),
            @Result(column = "tag_id", property = "tagId"),
            @Result(property = "tag", column = "tag_id",
                    many = @Many(select = "edu.nuist.dao.FrontUserDao.getTagsByTagId"))
    })
    List<LessonTag> getLessonTagsByLessonId(Integer lessonId);

}
