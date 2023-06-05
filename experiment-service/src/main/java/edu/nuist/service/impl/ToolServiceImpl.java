package edu.nuist.service.impl;

import edu.nuist.dao.ToolDao;
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
    public void deleteToolsByToolIds(List<Integer> toolIds) {
        toolDao.deleteToolByIds(toolIds);
    }

    @Override
    public List<Tool> getToolsByName(String name) {
        return toolDao.getToolsByName(name);
    }

    @Override
    public List<Tool> getTools(String name) {
        return toolDao.getToolsByName(name);
    }

}
