package edu.nuist.controller;

import edu.nuist.entity.*;
import edu.nuist.service.BackLessonService;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.SonUserExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        try {
            List<Lesson> lesson = frontLessonService.getLessonByName(lessonName);
            return BasicResultVO.success(lesson);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/getAllLessons")
    public BasicResultVO<List<Lesson>> getAllLessons(String activeName) {
        try {
            List<Lesson> allLesson = frontLessonService.getAllLesson(activeName);
            return BasicResultVO.success(allLesson);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
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

//    @GetMapping("/getDaymicExpUrl")
//    public String getDaymicExpUrl(){
//
//        String uri = "http://localhost:9000/createContainer";
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(uri);
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(httpGet);
//            HttpEntity entity = null;
//            String resultString =null;
//            if (response != null) {
//                entity = response.getEntity();
//                System.out.println();
//                String context = EntityUtils.toString(entity, "utf-8");
//                System.out.println(context);
//                resultString = context;
//            }
//            return resultString;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @PostMapping("/getDynamicExpUrl")
    public BasicResultVO<SonUserExp> getDynamicExpUrl(@RequestBody SonUserExp sonUserExp) {
        try {
            SonUserExp sonExpUrl = frontLessonService.getDynamicSonExpUrl(sonUserExp);
            return BasicResultVO.success(sonExpUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
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
        List<Chapter> chapters = backLessonService.getChapterByLessonId(lessonId);
        return BasicResultVO.success(chapters);
    }

}
