package edu.nuist.dao;

import edu.nuist.entity.Tool;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ToolDao {

    @Delete("delete from tools where tool_id  = #{param1}")
    void deleteTool(int tool_id);

    @Select("select * from tools where tool_name like CONCAT('%',#{param1},'%')")
    List<Tool> getToolsByName(String name);

}
