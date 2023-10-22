package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.entity.Tool;
import edu.nuist.service.ToolService;
import edu.nuist.vo.BasicResultVO;
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
    public BasicResultVO<PageInfo<Tool>> getAllTools(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Tool> toolsList = toolService.getAllTools();
            PageInfo<Tool> pageInfo = new PageInfo<>(toolsList, pageSize);
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/deleteTools")
    @PermissionAnnotation
    public BasicResultVO<Void> deleteTools(@RequestBody List<Integer> toolIds) {
        try {
            toolService.deleteToolsByToolIds(toolIds);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/findToolByName")
    public BasicResultVO<PageInfo<Tool>> findToolByName(
            @RequestParam("toolName") String toolName,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Tool> toolsList;

        try {
            toolsList = toolService.getToolsByName(toolName);
            PageInfo<Tool> pageInfo = new PageInfo<>(toolsList, pageSize);
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addTool")
    @PermissionAnnotation
    public BasicResultVO<Void> addTool(@RequestBody Tool tool) {
        try {
            toolService.addTool(tool);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/getToolsByName")
    public BasicResultVO<List<Tool>> getToolsByName(@RequestParam("toolName") String toolName) {
        try {
            List<Tool> tools = toolService.getToolsByName(toolName);
            return BasicResultVO.success(tools);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
