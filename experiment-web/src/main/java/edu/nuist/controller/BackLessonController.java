package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.entity.Chapter;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.SonChapter;
import edu.nuist.entity.UserExcel;
import edu.nuist.enums.RoleEnum;
import edu.nuist.enums.StatusEnum;
import edu.nuist.listener.UserExcelListener;
import edu.nuist.service.BackLessonService;
import edu.nuist.service.UserService;
import edu.nuist.util.FileUtils;
import edu.nuist.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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

    @Value("${file.fileRequestUrl}")
    private String fileRequestUrl;

    @Value("${jupyter.expUrl}")
    private String expUrl;

    @Value("${file.fileDirectory}")
    private String fileDirectory;

    private static final List<String> TOOL_TYPE = Arrays.asList(".exe", ".zip");

    private static final List<String> IMAGE_TYPE = Arrays.asList(".jpeg", ".png");

    private static final List<String> VIDEO_TYPE = Arrays.asList(".mp4", ".mkv");

    @ApiOperation("根据id返回老师或学生的所有课程")
    @PostMapping("/getLessonsByUserId")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByUserId(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        Integer userId = pageRequest.getUserId();
        Integer roleId = pageRequest.getRoleId();
        List<Lesson> lessons;

        // 如果是管理员
        if (RoleEnum.ADMIN_ROLE.getCode().equals(roleId)) {
            lessons = backLessonService.getAllLessons();
        } else {
            lessons = backLessonService.getLessonsByUserId(userId, roleId);
        }

        PageInfo<Lesson> pageInfo = new PageInfo<>(lessons, pageRequest.getPageSize());
        return BasicResultVO.success(pageInfo);
    }

    // Todo
    @ApiOperation("根据标签名称获取课程")
    @PostMapping("/getLessonsByTagName")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByTagName(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Lesson> lessons = backLessonService.getAllLessonsByTag(pageRequest.getTagName());
        PageInfo<Lesson> pageInfo = new PageInfo<>(lessons, pageRequest.getPageSize());
        return BasicResultVO.success(pageInfo);
    }

    @ApiOperation("管理页面添加课程")
    @PostMapping("/addLesson")
    @PermissionAnnotation("lesson:add")
    public BasicResultVO<Integer> addLesson(@RequestBody LessonSubmit lessonSubmit) {
        try {
            backLessonService.addLesson(lessonSubmit);
            return BasicResultVO.success(lessonSubmit.getLessonId());
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @ApiOperation("管理界面修改课程信息")
    @PostMapping("/updateLessonInfo")
    @PermissionAnnotation("lesson:update")
    public BasicResultVO<Void> updateLessonInfo(@RequestBody LessonSubmit lessonSubmit) {
        try {
            backLessonService.updateLessonInfo(lessonSubmit);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @ApiOperation("获取课程详情")
    @GetMapping("/getLessonDetail")
    public BasicResultVO<LessonSubmit> getLessonDetail(Integer lessonId) {
        LessonSubmit lessonSubmit = backLessonService.getLessonDetail(lessonId);
        return BasicResultVO.success(lessonSubmit);
    }

    @ApiOperation("获取课程章节")
    @GetMapping("/getChapterInfoByLessonId")
    public BasicResultVO<List<Chapter>> getChapterInfoByLessonId(Integer lessonId) {
        List<Chapter> chapters = backLessonService.getChapterByLessonId(lessonId);
        return BasicResultVO.success(chapters);
    }

    @ApiOperation("添加小节的实验链接")
    @PostMapping("/addChapteraddress")
    public BasicResultVO<SonChapterAndUrl> singleFileUpload(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("son_id") int son_id) throws IOException {
        if (file.isEmpty()) {
            return BasicResultVO.fail("文件为空!");
        }

        String fileName = file.getOriginalFilename();
        String filePath = fileDirectory + "jupyter/" + fileName;
        FileUtils.uploadFile(file, filePath);

        SonChapterAndUrl sonChapterAndUrl = new SonChapterAndUrl();
        sonChapterAndUrl.setExp_url(expUrl + fileName);
        sonChapterAndUrl.setSon_id(son_id);

        backLessonService.addSonChapterJupyterURL(sonChapterAndUrl);
        return BasicResultVO.success(sonChapterAndUrl);
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
    public BasicResultVO<List<Chapter>> addChapterInEdit(@RequestBody AddChapterInEdit addChapterInEdit) {
        List<Chapter> chapters = backLessonService.AddChapterInEditPart(addChapterInEdit);
        return BasicResultVO.success(chapters);
    }

    @GetMapping("/delChapterInEdit")
    public BasicResultVO<Void> delChapterInEdit(@RequestParam("chapterId") Integer chapterId) {
        try {
            backLessonService.delChapterInEdit(chapterId);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/delSonChapterInEdit")
    public BasicResultVO<Void> delSonChapterInEdit(@RequestParam("sonId") Integer sonId) {
        try {
            backLessonService.delSonChapterInEdit(sonId);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addSonChapterInEdit")
    public BasicResultVO<Void> addSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit) {
        try {
            backLessonService.addSonChapterInEdit(addSonChapterInEdit);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/editSonChapterInEdit")
    public BasicResultVO<Void> editSonChapterInEdit(@RequestBody AddSonChapterInEdit addSonChapterInEdit) {
        try {
            backLessonService.editSonChapterInEdit(addSonChapterInEdit);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/uploadFile")
    public BasicResultVO<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return BasicResultVO.fail("文件为空!");
        }

        String originalFilename = file.getOriginalFilename();
        String fileType = "";

        // 获取文件后缀
        if (!StringUtils.isBlank(originalFilename)) {
            fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String subPath;

        if (TOOL_TYPE.contains(fileType)) {
            subPath = "tools/";
        } else if (IMAGE_TYPE.contains(fileType)) {
            subPath = "images/";
        } else if (VIDEO_TYPE.contains(fileType)) {
            subPath = "video/";
        } else {
            subPath = "ppt/";
        }

        String fileName;

        try {
            fileName = FileUtils.uploadFile(file, fileDirectory + subPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new BasicResultVO<>(StatusEnum.SUCCESS_200, fileRequestUrl + subPath + fileName);
    }

    @PostMapping("/uploadExcelImport")
    public BasicResultVO<Void> uploadExcelImport(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;

        try {
            in = file.getInputStream();
            excelReader = EasyExcel.read(in, UserExcel.class, new UserExcelListener(image, userService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            return BasicResultVO.success("上传成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            return BasicResultVO.fail("上传失败");
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    @PostMapping("/uploadExcelImportStu")
    public BasicResultVO<Void> uploadExcelImportStu(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;

        try {
            in = file.getInputStream();
            excelReader = EasyExcel.read(in, UserExcel.class, new UserExcelListener(image, userService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            return BasicResultVO.success("上传成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            return BasicResultVO.fail("上传失败");
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    @PostMapping("/addSonChapterBook")
    public BasicResultVO<Void> addSonChapterBook(@RequestBody SonChapterAndUrl sonChapterAndUrl) {
        try {
            backLessonService.addSonChapterBook(sonChapterAndUrl);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/deleteLessonById")
    public BasicResultVO<Void> deleteLessonById(@RequestParam("lessonId") int lessonId) {
        try {
            backLessonService.deleteLessonById(lessonId);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/getEditSonChapterInfo")
    public BasicResultVO<SonChapter> getEditSonChapterInfo(Integer sonId) {
        SonChapter sonChapter = backLessonService.getEditSonChapterInfo(sonId);
        return BasicResultVO.success(sonChapter);
    }

}
