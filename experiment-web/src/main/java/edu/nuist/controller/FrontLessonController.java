package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.*;
import edu.nuist.service.BackLessonService;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/frontLesson")
public class FrontLessonController {

    @Resource
    private FrontLessonService frontLessonService;

    @Resource
    private BackLessonService backLessonService;

    @GetMapping(value = "/getLessonsByName")
    public BasicResultVO<List<Lesson>> getLessonByName(String lessonName) {
        List<Lesson> lesson = frontLessonService.getLessonByName(lessonName);
        return BasicResultVO.success(lesson);
    }

    @PostMapping("/getLessons")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByUserIdAndTagId(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Integer userId = pageRequest.getUserId();
        Integer tagId = pageRequest.getTagId();

        PageHelper.startPage(currentPage, pageSize);
        List<Lesson> lessons = frontLessonService.getLessonsByUserIdAndTagId(userId, tagId);
        PageInfo<Lesson> pageInfo = new PageInfo<>(lessons, pageSize);
        return BasicResultVO.success(pageInfo);
    }

    @GetMapping("/getLessonInfo")
    public BasicResultVO<Lesson> loadLessonInfo(@RequestParam("lessonId") int lessonId) {
        Lesson lesson = frontLessonService.loadLessonInfo(lessonId);
        return BasicResultVO.success(lesson);
    }

    @GetMapping("/getGuideBook")
    public BasicResultVO<SonChapter> getGuideBook(@RequestParam("sonId") int sonId) {
        SonChapter sonChapter = frontLessonService.getGuideBook(sonId);
        return BasicResultVO.success(sonChapter);
    }

    @GetMapping("/loadToolList")
    public BasicResultVO<List<Tool>> loadToolList() {
        List<Tool> allTools = frontLessonService.getAllTools();
        return BasicResultVO.success(allTools);
    }

    @GetMapping("/getLessonsByUserId")
    public BasicResultVO<List<Lesson>> getLessons(Integer userId) {
        List<Lesson> lessons = frontLessonService.getLessonByUserId(userId);
        return BasicResultVO.success(lessons);
    }

    @GetMapping("/getChapterInfoByLessonId")
    public BasicResultVO<List<Chapter>> getChapterInfoByLessonId(Integer lessonId) {
        List<Chapter> chapters = backLessonService.getChaptersByLessonId(lessonId);
        return BasicResultVO.success(chapters);
    }

    @PostMapping("/getUserJupyter")
    public BasicResultVO<UserJupyterFile> getUserJupyter(@RequestBody SonChapterDto sonChapterDto) {
        UserJupyterFile userJupyterFile;

        try {
            userJupyterFile = frontLessonService.getUserJupyter(sonChapterDto);
        } catch (IOException e) {
            return BasicResultVO.fail("该课程没有实验文件!");
        }

        return BasicResultVO.success(userJupyterFile);
    }

}
