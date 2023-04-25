package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.*;
import edu.nuist.listener.UserExcelListener;
import edu.nuist.service.BackLessonService;
import edu.nuist.service.BackTagService;
import edu.nuist.service.ToolService;
import edu.nuist.service.UserService;
import edu.nuist.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class BackLessonController {

    @Resource
    private UserService userService;

    @Resource
    private BackLessonService backLessonService;

    @Resource
    private BackTagService backTagService;

    @Resource
    private ToolService toolService;

    @Value("${avatar.image}")
    private String image;

    @Value("${platform.type}")
    private String platformType;

    @Value("${platform.address}")
    private String address;

    @ApiOperation("管理页面返回所有课程")
    @PostMapping(value = "/back/getAllLessons")
    public PageInfo<Lesson> getAllLessons(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Lesson> lessonList;

        try {
            if (pageRequest.getTagActive().equals("全部")) {
                lessonList = backLessonService.getAllLessons();
                return new PageInfo<>(lessonList, pageRequest.getPageSize());
            } else {
                lessonList = backLessonService.getAllLessonsByTag(pageRequest.getTagActive());
                return new PageInfo<>(lessonList, pageRequest.getPageSize());
            }
        } catch (Exception e) {
            // 将查询到的数据封装到PageInfo对象, 分割数据成功
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @ApiOperation("管理页面添加课程图片")
    @PostMapping("/back/addLessonPic")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String fileName1 = fileName.split("\\.")[1];
        String name = UUID.randomUUID().toString(); // 随机的uuid

        if (platformType.equals("windows")){
            String filePath = "D:\\images\\" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name+"."+fileName1);
        }

        return result;
    }

    @ApiOperation("管理页面添加课程")
    @PostMapping("/back/addLesson")
    public Result addLesson(@RequestBody LessonSubmit lessonSubmit){
        return backLessonService.addLesson(lessonSubmit);
    }

    @ApiOperation("管理界面修改课程信息")
    @PostMapping("/back/updateLessonInfo")
    public Result updateLessonInfo(@RequestBody LessonSubmit lessonSubmit){
        return backLessonService.updateLessonInfo(lessonSubmit);
    }

    @ApiOperation("获取课程详情")
    @PostMapping("/back/getLessonDetail")
    public Result getLessonDetail(@RequestBody LessonSubmit lessonSubmit){
        return backLessonService.getLessonDetail(lessonSubmit.getLessonId());
    }

    @ApiOperation("获取课程章节")
    @PostMapping("/back/getChapterInfoByLessonId")
    public Result getChapterInfoByLessonId(@RequestBody LessonSubmit lessonSubmit){
        return backLessonService.getChapterByLessonId(lessonSubmit);
    }

    @ApiOperation("添加小节的实验链接")
    @PostMapping("back/addChapterJupyterURL")
    public Result singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("son_id") int son_id) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\Dell\\" + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        SonChapterAndUrl sonChapterAndUrl = new SonChapterAndUrl();
        sonChapterAndUrl.setExp_url(address + ":8888/notebooks/" + fileName);
        sonChapterAndUrl.setSon_id(son_id);
        return backLessonService.addSonChapterJupyterURL(sonChapterAndUrl);
    }

    @ApiOperation("按名称检索课程")
    @PostMapping(value = "/back/findLessonsByName")
    public PageInfo<Lesson> findLessonsByName(@RequestBody PageRequest pageRequest) throws IOException {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Lesson> lessonList;

        try {
            lessonList = backLessonService.findLessonsByName(pageRequest.getLesson_name());
            return new PageInfo<>(lessonList, pageRequest.getPageSize());
        } catch (Exception e) {

            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping("/back/AddChapterInEdit")
    public Result AddChapterInEdit(@RequestBody AddChapterInEdit addChapterInEdit){
        return backLessonService.AddChapterInEditPart(addChapterInEdit);
    }

    @GetMapping("/back/delChapterInEdit")
    public Result delChapterInEdit(@RequestParam("chapter_id") Integer chapter_id){
        return backLessonService.delChapterInEdit(chapter_id);
    }

    @GetMapping("/back/delSonChapterInEdit")
    public Result delSonChapterInEdit(@RequestParam("son_id") Integer son_id){
        return backLessonService.delSonChapterInEdit(son_id);
    }

    @PostMapping("/back/AddSonChapterInEdit")
    public Result AddSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit){
        return backLessonService.AddSonChapterInEdit(addSonChapterInEdit);
    }

    @PostMapping("/back/EditSonChapterInEdit")
    public Result AddEditSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit){
        log.info(addSonChapterInEdit.toString());
        return backLessonService.editSonChapterInEdit(addSonChapterInEdit);
    }

    @PostMapping("/back/uploadAttachmentMp4")
    public Result uploadAttachmentMp4(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        String fileName1 = fileName.split("\\.")[1];
        String name = UUID.randomUUID().toString();

        //result.setData("http://10.14.253.39:8081/images/"+name+"."+fileName1);
        if (platformType.equals("windows")){
            String filePath = "D:\\images\\" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name+"."+fileName1);
        }

        return result;
    }

    @PostMapping("/back/uploadAttachmentPPT")
    public Result uploadAttachmentPPT(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        String fileName1 = fileName.split("\\.")[1];
        String name = UUID.randomUUID().toString(); // 随机的uuid

        if (platformType.equals("windows")){
            String filePath = "D:\\images\\" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address+":8081/"+name+"."+fileName1);
        }

        return result;
    }

    @PostMapping("/back/uploadExcelImport")
    public Result uploadExcelImport(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        ExcelReader excelReader = null;
        InputStream in = null;

        try {
            in = file.getInputStream();
            excelReader = EasyExcel.read(in, UserExcel.class, new UserExcelListener(image, userService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            result.setCode("200");
        } catch (IOException ex) {
            log.error("import excel to db fail", ex);
            result.setCode("500");
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        return result;
    }

    @PostMapping("/back/uploadExcelImportStu")
    public Result uploadExcelImportStu(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        ExcelReader excelReader = null;
        InputStream in = null;
        System.out.println(file.getOriginalFilename());

        try {
            in = file.getInputStream();
            excelReader = EasyExcel.read(in, UserExcel.class, new UserExcelListener(image, userService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            result.setCode("200");
        } catch (IOException ex) {
            log.error("import excel to db fail", ex);
            result.setCode("500");
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        return result;
    }

    @PostMapping("/back/loadTagList")
    public PageInfo<Tag> loadTagList(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Tag> tagList;

        try {
            tagList = backTagService.getAllTags();
            return new PageInfo<>(tagList, pageRequest.getPageSize());
        } catch (Exception e) {
            // 将查询到的数据封装到PageInfo对象
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping("/back/addTag")
    public Result addTag(@RequestBody Tag tag) {
        return backTagService.addTag(tag);
    }

    @PostMapping("/back/editTag")
    public Result editTag(@RequestBody Tag tag) {
        return backTagService.editTag(tag);
    }

    @PostMapping("/back/delTag")
    public Result delTag(@RequestBody Tag tag) {
        return backTagService.delTag(tag);
    }

    @GetMapping("/back/getOptionList")
    public Result getOptionList(){
        return backLessonService.getAllOptionList();
    }

    @PostMapping("/back/addSonChapterBook")
    public Result addSonChapterBook(@RequestBody SonChapterAndUrl sonChapterAndUrl) {
        return backLessonService.addSonChapterBook(sonChapterAndUrl);
    }

    @GetMapping("/back/deleteLessonById")
    public Result deleteLessonById(@RequestParam("lesson_id") int lesson_id){
        LessonIdInfo lessonIdInfo = new LessonIdInfo();
        lessonIdInfo.setLessonId(lesson_id);
        return backLessonService.deleteLessonById(lessonIdInfo);
    }

    @PostMapping("/back/getEditSonChapterInfo")
    public Result getEditSonChapterInfo(@RequestBody SonChapterAndUrl sonChapterAndUrl){
        return backLessonService.getEditSonChapterInfo(sonChapterAndUrl);
    }

    @PostMapping(value = "/back/getAllTools")
    public PageInfo<Tool> getAllTools(@RequestBody PageRequest pageRequest)  {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Tool> toolsList;

        try {
            toolsList = backLessonService.getAllTools();
            return new PageInfo<>(toolsList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/back/findToolByName")
    public PageInfo<Tool> findToolByName(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Tool> toolsList;

        try {
            toolsList = backLessonService.getAllToolsByName(pageRequest.getTool_name());
            return new PageInfo<>(toolsList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/back/addTool")
    public Result addTool(@RequestBody Tool tools) {
        System.out.println(tools);
        return backLessonService.addTool(tools);
    }

    ///back/deleteTools
    @PostMapping(value = "/back/deleteTools")
    public synchronized Result deleteTools(@RequestBody String deleteRow) throws JSONException {
        Result result = new Result();
        JSONObject jsonTest = new JSONObject(deleteRow);

        try {
            List<Tool> toolsList = com.alibaba.fastjson.JSONObject.parseArray(jsonTest.getString("deleteRow"), Tool.class);

            for (Tool tools : toolsList) {
                toolService.deleteTool(tools.getTool_id());
            }

            result.setCode("200");
        } catch (JSONException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
