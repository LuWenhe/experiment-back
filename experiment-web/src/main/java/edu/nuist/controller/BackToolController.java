package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Result;
import edu.nuist.entity.Tool;
import edu.nuist.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backTool")
public class BackToolController {

    @Resource
    private ToolService toolService;

    @GetMapping("/getAllTools")
    public Result getAllTools(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Tool> toolsList = toolService.getAllTools();
            PageInfo<Tool> pageInfo = new PageInfo<>(toolsList, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/deleteTools")
    public Result deleteTools(@RequestBody List<Integer> toolIds) {
        Result result = new Result();

        try {
            toolService.deleteToolsByToolIds(toolIds);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/findToolByName")
    public Result findToolByName(@RequestParam("toolName") String toolName,
                                 @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);
        List<Tool> toolsList;

        try {
            toolsList = toolService.getToolsByName(toolName);
            PageInfo<Tool> pageInfo = new PageInfo<>(toolsList, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/addTool")
    public Result addTool(@RequestBody Tool tool) {
        Result result = new Result();

        try {
            toolService.addTool(tool);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/loadToolListByName")
    public Result loadToolListByName(@RequestParam("searchName") String name) {
        Result result = new Result();

        try {
            List<Tool> tools = toolService.getToolsByName(name);
            result.setData(tools);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
