package edu.nuist.dao;

import edu.nuist.entity.Tag;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontTagDao {

    @Select("select * from tag")
    List<Tag> getAllTags();

    @Select("select tag_id, tagName from tag where tag_id = #{tagId}")
    @Results({
        @Result(id = true, column = "tag_id", property = "tagId"),
        @Result(column = "tagName", property = "tagName")
    })
    List<Tag> getTagsByTagId(Integer tagId);

}
