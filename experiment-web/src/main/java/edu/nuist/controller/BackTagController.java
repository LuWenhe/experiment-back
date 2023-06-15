package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Tag;
import edu.nuist.service.BackTagService;
import edu.nuist.vo.BasicResultVO;
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
    public BasicResultVO<PageInfo<Tag>> getTagListByPage(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Tag> tagList = backTagService.getTags();
            PageInfo<Tag> pageInfo = new PageInfo<>(tagList, pageSize);
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/getTags")
    public BasicResultVO<List<Tag>> getTags() {
        try {
            List<Tag> tags = backTagService.getTags();
            return BasicResultVO.success(tags);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addTag")
    public BasicResultVO<Void> addTag(@RequestBody Tag tag) {
        try {
            backTagService.addTag(tag);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/editTag")
    public BasicResultVO<Void> editTag(@RequestBody Tag tag) {
        try {
            backTagService.editTag(tag);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/delTag")
    public BasicResultVO<Void> delTag(Integer tagId) {
        try {
            backTagService.delTagByTagId(tagId);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

}
