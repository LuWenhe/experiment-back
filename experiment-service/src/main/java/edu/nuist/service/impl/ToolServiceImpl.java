package edu.nuist.service.impl;

import edu.nuist.dao.ToolDao;
import edu.nuist.entity.Result;
import edu.nuist.entity.Tool;
import edu.nuist.service.ToolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {

    @Resource
    private ToolDao toolDao;

    @Override
    public void deleteTool(int tool_id) {
        toolDao.deleteTool(tool_id);
    }

    @Override
    public List<Tool> getToolsByName(String name) {
        return toolDao.getToolsByName(name);
    }

    @Override
    public Result getTools(String name) {
        Result result = new Result();

        try {
            result.setData(toolDao.getToolsByName(name));
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }


        return result;
    }

}
