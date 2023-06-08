package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Result;
import edu.nuist.entity.Tag;
import edu.nuist.service.BackTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backTag")
public class BackTagController {

    @Resource
    private BackTagService backTagService;

    @GetMapping("/getTagsByPage")
    public Result getTagListByPage(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Tag> tagList = backTagService.getTags();
            PageInfo<Tag> pageInfo = new PageInfo<>(tagList, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/getTags")
    public Result getTags() {
        Result result = new Result();

        try {
            List<Tag> tags = backTagService.getTags();
            result.setData(tags);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/addTag")
    public Result addTag(@RequestBody Tag tag) {
        Result result = new Result();

        try {
            backTagService.addTag(tag);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/editTag")
    public Result editTag(@RequestBody Tag tag) {
        Result result = new Result();

        try {
            backTagService.editTag(tag);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/delTag")
    public Result delTag(Integer tagId) {
        Result result = new Result();

        try {
            backTagService.delTagByTagId(tagId);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
