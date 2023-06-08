package edu.nuist.service;

import edu.nuist.entity.Tool;

import java.util.List;

public interface ToolService {

    void deleteToolsByToolIds(List<Integer> toolIds);

    List<Tool> getToolsByName(String name);

    List<Tool> getAllTools();

    void addTool(Tool tool);

}
