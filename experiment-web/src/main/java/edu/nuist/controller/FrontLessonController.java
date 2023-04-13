package edu.nuist.controller;

import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.entity.SonChapter;
import edu.nuist.service.FrontLessonService;
import edu.nuist.service.ToolService;
import edu.nuist.vo.ActiveNameVO;
import edu.nuist.vo.GetAllLessons;
import edu.nuist.vo.SonUserExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class FrontLessonController {

    @Resource
    private FrontLessonService frontLessonService;

    @Resource
    private ToolService toolService;

    @PostMapping(value = "/front/getLessonByName")
    public List<Lesson> getLessonByName(@RequestBody ActiveNameVO activeName) {
        return frontLessonService.getLessonByName(activeName);
    }

    @PostMapping(value = "/front/getAllLessons")
    public List<Lesson> getAllLessons(@RequestBody ActiveNameVO activeName){
        return frontLessonService.getAllLesson(activeName.getActiveName());
    }

    @GetMapping(value = "/front/loadLessonInfo")
    public Result loadLessonInfo(@RequestParam("lessonId") int lessonId){
        return frontLessonService.loadLessonInfo(lessonId);
    }

    @GetMapping(value = "/front/getGuideBook")
    public Result getGuideBook(@RequestParam("son_id") int son_id){
        return frontLessonService.getGuideBook(son_id);
    }

//    @GetMapping(value = "/front/getDaymicExpUrl")
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

    @PostMapping("/front/getDynamicExpUrl")
    public Result getDynamicExpUrl(@RequestBody SonUserExp sonUserExp) throws IOException {
        return frontLessonService.getDynamicSonExpUrl(sonUserExp);
    }

    @GetMapping("/front/loadToolList")
    public Result loadToolList(){
        return frontLessonService.loadTool();
    }

    @GetMapping("/front/loadToolListByName")
    public Result loadToolListByName(@RequestParam("searchName") String name){
        return toolService.getTools(name);
    }

}
