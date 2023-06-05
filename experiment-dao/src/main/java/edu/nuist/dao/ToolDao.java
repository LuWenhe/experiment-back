package edu.nuist.dao;

import edu.nuist.entity.Tool;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ToolDao {

    @Delete({"<script>",
            "DELETE FROM tools WHERE tool_id in (",
            "<foreach collection='toolIds' item='toolId' index='index' separator=','>",
            "#{toolId}",
            "</foreach>",
            ")",
            "</script>"
    })
    void deleteToolByIds(List<Integer> toolIds);

    @Delete("delete from tools where tool_id  = #{param1}")
    void deleteTool(int tool_id);

    @Select("select * from tools where tool_name like CONCAT('%',#{param1},'%')")
    List<Tool> getToolsByName(String name);

}
