package edu.nuist.controller;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.Tool;
import edu.nuist.service.FrontLessonService;
import edu.nuist.vo.ActiveNameVO;
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

    @PostMapping(value = "/getLessonByName")
    public List<Lesson> getLessonByName(@RequestBody ActiveNameVO activeName) {
        return frontLessonService.getLessonByName(activeName);
    }

    @PostMapping("/getAllLessons")
    public List<Lesson> getAllLessons(@RequestBody ActiveNameVO activeName) {
        return frontLessonService.getAllLesson(activeName.getActiveName());
    }

    @GetMapping("/loadLessonInfo")
    public Result loadLessonInfo(@RequestParam("lessonId") int lessonId) {
        Result result = new Result();

        try {
            Lesson lesson = frontLessonService.loadLessonInfo(lessonId);
            result.setCode("200");
            result.setData(lesson);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("500");
        }

        return result;
    }

    @GetMapping("/getGuideBook")
    public Result getGuideBook(@RequestParam("sonId") int sonId) {
        Result result = new Result();
        SonChapter sonChapter;

        try {
            sonChapter = frontLessonService.getGuideBook(sonId);
            result.setCode("200");
            result.setData(sonChapter);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
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
    public Result getDynamicExpUrl(@RequestBody SonUserExp sonUserExp) {
        Result result = new Result();

        try {
            SonUserExp sonExpUrl = frontLessonService.getDynamicSonExpUrl(sonUserExp);
            result.setData(sonExpUrl);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/loadToolList")
    public Result loadToolList() {
        Result result = new Result();
        List<Tool> allTools;

        try {
            allTools = frontLessonService.getAllTools();
            result.setData(allTools);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
