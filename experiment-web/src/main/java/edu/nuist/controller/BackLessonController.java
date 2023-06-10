package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.Result;
import edu.nuist.entity.UserExcel;
import edu.nuist.listener.UserExcelListener;
import edu.nuist.service.BackLessonService;
import edu.nuist.service.UserService;
import edu.nuist.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/backLesson")
@PermissionAnnotation
public class BackLessonController {

    @Resource
    private UserService userService;

    @Resource
    private BackLessonService backLessonService;

    @Value("${avatar.image}")
    private String image;

    @Value("${platform.type}")
    private String platformType;

    @Value("${platform.address}")
    private String address;

    @ApiOperation("根据id返回老师或学生的所有课程")
    @PostMapping("/getLessonsByUserId")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByUserId(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        Integer userId = pageRequest.getUserId();
        Integer roleId = pageRequest.getRoleId();

        try {
            List<Lesson> lessons = backLessonService.getLessonsByUserId(userId, roleId);
            PageInfo<Lesson> pageInfo = new PageInfo<>(lessons, pageRequest.getPageSize());
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    // Todo
    @ApiOperation("根据标签名称获取课程")
    @PostMapping("/getLessonsByTagName")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByTagName(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        String tagName = pageRequest.getTagName();

        try {
            List<Lesson> lessons = backLessonService.getAllLessonsByTag(tagName);
            PageInfo<Lesson> pageInfo = new PageInfo<>(lessons, pageRequest.getPageSize());
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @ApiOperation("管理页面添加课程图片")
    @PostMapping("/addLessonPic")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        String fileName1 = fileName.split("\\.")[1];
        String name = UUID.randomUUID().toString();

        if (platformType.equals("windows")) {
            String filePath = "D:/Projects/ActualProjects/experiment_ai/images/" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            log.info("filePath: {}", filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/" + name + "." + fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        }

        return result;
    }

    @ApiOperation("管理页面添加课程")
    @PostMapping("/addLesson")
    @PermissionAnnotation("lesson:add")
    public Result addLesson(@RequestBody LessonSubmit lessonSubmit) {
        return backLessonService.addLesson(lessonSubmit);
    }

    @ApiOperation("管理界面修改课程信息")
    @PostMapping("/updateLessonInfo")
    @PermissionAnnotation("lesson:update")
    public Result updateLessonInfo(@RequestBody LessonSubmit lessonSubmit) {
        return backLessonService.updateLessonInfo(lessonSubmit);
    }

    @ApiOperation("获取课程详情")
    @GetMapping("/getLessonDetail")
    public Result getLessonDetail(Integer lessonId) {
        return backLessonService.getLessonDetail(lessonId);
    }

    @ApiOperation("获取课程章节")
    @GetMapping("/getChapterInfoByLessonId")
    public Result getChapterInfoByLessonId(Integer lessonId) {
        return backLessonService.getChapterByLessonId(lessonId);
    }

    @ApiOperation("添加小节的实验链接")
    @PostMapping("/addChapterJupyterURL")
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
    @PostMapping("/findLessonsByName")
    public BasicResultVO<PageInfo<Lesson>> findLessonsByName(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        Integer userId = pageRequest.getUserId();
        String lessonName = pageRequest.getLessonName();

        try {
            List<Lesson> lessonList = backLessonService.findLessonsByName(userId, lessonName);
            PageInfo<Lesson> pageInfo = new PageInfo<>(lessonList, pageRequest.getPageSize());
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addChapterInEdit")
    public Result addChapterInEdit(@RequestBody AddChapterInEdit addChapterInEdit) {
        return backLessonService.AddChapterInEditPart(addChapterInEdit);
    }

    @GetMapping("/delChapterInEdit")
    public Result delChapterInEdit(@RequestParam("chapterId") Integer chapterId) {
        return backLessonService.delChapterInEdit(chapterId);
    }

    @GetMapping("/delSonChapterInEdit")
    public Result delSonChapterInEdit(@RequestParam("sonId") Integer sonId) {
        return backLessonService.delSonChapterInEdit(sonId);
    }

    @PostMapping("/addSonChapterInEdit")
    public Result addSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit) {
        return backLessonService.AddSonChapterInEdit(addSonChapterInEdit);
    }

    @PostMapping("/editSonChapterInEdit")
    public Result editSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit) {
        return backLessonService.editSonChapterInEdit(addSonChapterInEdit);
    }

    @PostMapping("/uploadAttachmentMp4")
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
        if (platformType.equals("windows")) {
            String filePath = "D:\\images\\" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/" + name + "." + fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        }

        return result;
    }

    @PostMapping("/uploadAttachmentPPT")
    public Result uploadAttachmentPPT(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();

        if (file.isEmpty()) {
            result.setCode("500");
            return result;
        }

        String fileName = file.getOriginalFilename();
        String fileName1 = fileName.split("\\.")[1];
        String name = UUID.randomUUID().toString(); // 随机的uuid

        if (platformType.equals("windows")) {
            String filePath = "D:\\images\\" + name + "." + fileName1;
            //String filePath = "/home/pic/"+name+"."+fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        } else {
            String filePath = "/home/pl/files/" + name + "." + fileName1;
            System.out.println(filePath);
            File dest = new File(filePath);
            file.transferTo(dest);
            result.setCode("200");
            result.setData(address + ":8081/" + name + "." + fileName1);
        }

        return result;
    }

    @PostMapping("/uploadExcelImport")
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

    @PostMapping("/uploadExcelImportStu")
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

    @PostMapping("/addSonChapterBook")
    public Result addSonChapterBook(@RequestBody SonChapterAndUrl sonChapterAndUrl) {
        return backLessonService.addSonChapterBook(sonChapterAndUrl);
    }

    @GetMapping("/deleteLessonById")
    public Result deleteLessonById(@RequestParam("lessonId") int lessonId) {
        return backLessonService.deleteLessonById(lessonId);
    }

    @GetMapping("/getEditSonChapterInfo")
    public Result getEditSonChapterInfo(Integer sonId) {
        return backLessonService.getEditSonChapterInfo(sonId);
    }

}
