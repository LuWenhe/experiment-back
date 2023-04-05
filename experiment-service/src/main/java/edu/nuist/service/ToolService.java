package edu.nuist.service;

import edu.nuist.entity.Result;
import edu.nuist.entity.Tool;

import java.util.List;

public interface ToolService {

    void deleteTool(int tool_id);

    List<Tool> getToolsByName(String name);

    Result getTools(String name);

}
