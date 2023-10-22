package edu.nuist.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.dto.ChapterDto;
import edu.nuist.dto.JupyterFileDto;
import edu.nuist.dto.SonChapterDto;
import edu.nuist.entity.Chapter;
import edu.nuist.entity.JupyterFile;
import edu.nuist.entity.Lesson;
import edu.nuist.entity.jupyter.Cells;
import edu.nuist.entity.jupyter.JsonRootBean;
import edu.nuist.enums.RoleEnum;
import edu.nuist.enums.StatusEnum;
import edu.nuist.service.BackLessonService;
import edu.nuist.util.FileUtils;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.LessonSubmit;
import edu.nuist.vo.PageRequest;
import edu.nuist.vo.SonChapterAndUrl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backLesson")
@PermissionAnnotation
public class BackLessonController {

    @Resource
    private BackLessonService backLessonService;

    @Value("${file.fileRequestUrl}")
    private String fileRequestUrl;

    @Value("${jupyter.expUrl}")
    private String expUrl;

    @Value("${file.fileDirectory}")
    private String fileDirectory;

    private static final List<String> TOOL_TYPE = Arrays.asList(".exe", ".zip");

    private static final List<String> IMAGE_TYPE = Arrays.asList(".jpeg", ".png", ".jpg");

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

    @ApiOperation("根据标签名称获取课程")
    @PostMapping("/getLessonsByTagId")
    public BasicResultVO<PageInfo<Lesson>> getLessonsByTagName(@RequestBody PageRequest pageRequest) {
        Integer userId = pageRequest.getUserId();
        Integer roleId = pageRequest.getRoleId();
        Integer tagId = pageRequest.getTagId();
        List<Lesson> lessons;

        if (RoleEnum.ADMIN_ROLE.getCode().equals(roleId)) {
            lessons = backLessonService.getAllLessons();
        } else {
            lessons = backLessonService.getLessonsByUserId(userId, roleId);
        }

        List<Integer> lessonIds = backLessonService.getLessonIdsByTagId(tagId);
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Lesson> filterLessons = new ArrayList<>();

        for (Lesson lesson : lessons) {
            if (!lessonIds.contains(lesson.getLessonId())) {
                continue;
            }

            filterLessons.add(lesson);
        }

        PageInfo<Lesson> pageInfo = new PageInfo<>(filterLessons, pageRequest.getPageSize());
        return BasicResultVO.success(pageInfo);
    }

    @ApiOperation("管理页面添加课程")
    @PostMapping("/addLesson")
    @PermissionAnnotation("lesson:add")
    public BasicResultVO<Void> addLesson(@RequestBody LessonSubmit lessonSubmit) {
        try {
            String lessonName = lessonSubmit.getLesson_name();
            String path = fileDirectory + lessonName;
            backLessonService.addLesson(lessonSubmit);

            if (lessonSubmit.getLessonId() == null) {
                return BasicResultVO.fail();
            }

            FileUtils.createFile(path);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @ApiOperation("管理界面修改课程信息")
    @PostMapping("/updateLesson")
    @PermissionAnnotation("lesson:update")
    public BasicResultVO<Void> updateLessonInfo(@RequestBody LessonSubmit lessonSubmit) {
        try {
            Integer lessonId = lessonSubmit.getLessonId();
            LessonSubmit oldLesson = backLessonService.getLessonDetail(lessonId);

            backLessonService.updateLesson(lessonSubmit);
            List<JupyterFile> jupyterFiles = backLessonService.getJupyterIdsByLessonId(lessonId);

            String oldLessonName = oldLesson.getLesson_name();
            String newLessonName = lessonSubmit.getLesson_name();

            if (!jupyterFiles.isEmpty()) {
                for (JupyterFile jupyterFile : jupyterFiles) {
                    String url = jupyterFile.getUrl();
                    String newUrl = url.replace(oldLessonName, newLessonName);
                    jupyterFile.setUrl(newUrl);
                }

                backLessonService.updateJupyterFile(jupyterFiles);
            }

            String srcPath = fileDirectory + oldLessonName;
            String destPath = fileDirectory + newLessonName;

            backLessonService.updateLesson(lessonSubmit);

            FileUtils.renameFile(srcPath, destPath);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/deleteLesson")
    public BasicResultVO<Void> deleteLesson(Integer lessonId, String lessonName) {
        try {
            backLessonService.deleteJupyterFilesByLessonId(lessonId);
            backLessonService.deleteSonChaptersByLessonId(lessonId);
            backLessonService.deleteChaptersByLessonId(lessonId);
            backLessonService.deleteLessonById(lessonId);

            FileUtils.deleteDirectoriesAndFiles(fileDirectory + lessonName);
            return BasicResultVO.success();
        } catch (Exception e) {
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
        List<Chapter> chapters = backLessonService.getChaptersByLessonId(lessonId);
        return BasicResultVO.success(chapters);
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
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addChapter")
    public BasicResultVO<List<Chapter>> addChapter(@RequestBody ChapterDto chapterDto) {
        try {
            String chapterPath = chapterDto.getLessonName() + "/" + chapterDto.getName();
            String filePath = fileDirectory + chapterPath;
            backLessonService.addChapter(chapterDto);

            FileUtils.createFile(filePath);

            List<Chapter> chapters = backLessonService.getChaptersByLessonId(chapterDto.getLessonId());
            return BasicResultVO.success(chapters);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/updateChapter")
    public BasicResultVO<Void> updateChapter(@RequestBody ChapterDto chapterDto) {
        try {
            Integer chapterDtoId = chapterDto.getId();
            Chapter oldChapter = backLessonService.getChapterByChapterId(chapterDtoId);

            // 更新章节表
            backLessonService.updateChapter(chapterDto);
            List<JupyterFile> jupyterFiles = backLessonService.getJupyterIdsByChapterId(chapterDtoId);

            String oldChapterName = oldChapter.getName();
            String newChapterName = chapterDto.getName();
            String lessonName = chapterDto.getLessonName();

            if (!jupyterFiles.isEmpty()) {
                for (JupyterFile jupyterFile : jupyterFiles) {
                    String url = jupyterFile.getUrl();
                    String newUrl = url.replace(oldChapterName, newChapterName);
                    jupyterFile.setUrl(newUrl);
                }

                backLessonService.updateJupyterFile(jupyterFiles);
            }


            String srcPath = fileDirectory + lessonName + "/" + oldChapterName;
            String destPath = fileDirectory + lessonName + "/" + newChapterName;

            FileUtils.renameFile(srcPath, destPath);

            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/deleteChapter")
    public BasicResultVO<Void> deleteChapter(@RequestParam("lessonName") String lessonName,
                                             @RequestParam("chapterId") Integer chapterId,
                                             @RequestParam("chapterName") String chapterName) {
        try {
            List<JupyterFile> jupyterFiles = backLessonService.getJupyterIdsByChapterId(chapterId);
            List<Integer> jupyterIds = new ArrayList<>();

            for (JupyterFile jupyterFile : jupyterFiles) {
                jupyterIds.add(jupyterFile.getId());
            }

            backLessonService.deleteJupyterFilesByIds(jupyterIds);
            backLessonService.deleteSonChaptersByChapterId(chapterId);
            backLessonService.deleteChapters(chapterId);

            String filePath = fileDirectory + lessonName + chapterName;
            FileUtils.deleteDirectoriesAndFiles(filePath);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addSonChapter")
    public BasicResultVO<Void> addSonChapter(@RequestBody SonChapterDto sonChapterDto) {
        try {
            String sonChapterPath = sonChapterDto.getLessonName() + "/" + sonChapterDto.getChapterName()
                    + "/" + sonChapterDto.getName();
            backLessonService.addSonChapter(sonChapterDto);

            String filePath = fileDirectory + sonChapterPath;
            FileUtils.createFile(filePath);

            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/updateSonChapter")
    public BasicResultVO<Void> updateSonChapter(@RequestBody SonChapterDto sonChapterDto) {
        try {
            int sonChapterId = sonChapterDto.getId();
            SonChapterDto oldSonChapter = backLessonService.getSonChapter(sonChapterId);

            backLessonService.updateSonChapter(sonChapterDto);
            List<JupyterFile> jupyterFiles = backLessonService.getJupyterFiles(sonChapterId);

            String oldSonChapterName = oldSonChapter.getName();
            String newSonChapterName = sonChapterDto.getName();

            if (!jupyterFiles.isEmpty()) {
                for (JupyterFile jupyterFile : jupyterFiles) {
                    String url = jupyterFile.getUrl();
                    String newUrl = url.replace(oldSonChapterName, newSonChapterName);
                    jupyterFile.setUrl(newUrl);
                }

                backLessonService.updateJupyterFile(jupyterFiles);
            }

            String srcPath = fileDirectory + oldSonChapter.getLessonName() + "/" + oldSonChapter.getChapterName()
                    + "/" + oldSonChapterName;
            String destPath = fileDirectory + sonChapterDto.getLessonName() + "/" + sonChapterDto.getChapterName()
                    + "/" + newSonChapterName;

            FileUtils.renameFile(srcPath, destPath);

            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/delSonChapter")
    public BasicResultVO<Void> delSonChapter(@RequestParam("lessonName") String lessonName,
                                             @RequestParam("chapterName") String chapterName,
                                             @RequestParam("sonId") Integer sonId,
                                             @RequestParam("sonName") String sonName) {
        try {
            backLessonService.deleteSonChapterById(sonId);
            backLessonService.deleteJupyterFilesBySonId(sonId);

            String filePath = fileDirectory + lessonName + "/" + chapterName + "/" + sonName;
            // 删除子小节下面的所以文件
            FileUtils.deleteDirectoriesAndFiles(filePath);

            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/parseJupyter")
    public BasicResultVO<String> parseJupyter(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String tmpStr;
        boolean isFirstRow = true;
        StringBuilder jupyterContent = new StringBuilder();

        while ((tmpStr = reader.readLine()) != null) {
            jupyterContent.append(tmpStr);

            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            jupyterContent.append("\n");
        }

        reader.close();

        JsonRootBean jsonRootBean = JSON.parseObject(jupyterContent.toString(), JsonRootBean.class);
        List<Cells> cells = jsonRootBean.getCells();
        StringBuilder parseContent = new StringBuilder();

        for (Cells cell : cells) {
            String cellType = cell.getCellType();
            List<String> cellSource = cell.getSource();

            if (cellType.equals("markdown")) {
                if (cellSource.isEmpty()) {
                    continue;
                }

                // 如果之前有内容, 在添加换行符
                if (parseContent.length() > 0) {
                    parseContent.append("\n");
                }

                for (String s : cellSource) {
                    parseContent.append(s);
                }
            } else if (cellType.equals("code")) {
                if (cellSource.isEmpty()) {
                    continue;
                }

                if (parseContent.length() > 0) {
                    parseContent.append("\n");
                }

                parseContent.append("```python\n");

                for (String s : cellSource) {
                    parseContent.append(s);
                }

                parseContent.append("\n```");
            }
        }

        return BasicResultVO.success(null, parseContent.toString(), null);
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
            subPath = "tool/";
        } else if (IMAGE_TYPE.contains(fileType)) {
            subPath = "image/";
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

    @PostMapping("/uploadLessonFile")
    public BasicResultVO<Void> uploadLessonFile(@RequestParam("files") MultipartFile[] files,
                                                String path,
                                                Integer lessonId,
                                                Integer sonId) {
        path = path.trim();

        if (files == null) {
            return BasicResultVO.success();
        }

        List<JupyterFile> jupyterFiles = new ArrayList<>();
        boolean isExists = false;


        for (MultipartFile file : files) {
            JupyterFile jupyterFile = new JupyterFile();
            jupyterFile.setPath(path);
            jupyterFile.setLessonId(lessonId);
            jupyterFile.setSonId(sonId);

            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String filePath = fileDirectory + path + "/";

            // 如果文件已经存在
            if (FileUtils.ifExists(file, filePath)) {
                isExists = true;
                continue;
            }

            if (suffix.equals(".ipynb")) {
                jupyterFile.setType(1);
            } else if (suffix.equals(".py")) {
                jupyterFile.setType(2);
            }

            jupyterFile.setName(fileName);
            jupyterFile.setUrl(expUrl + path + "/" + fileName);
            jupyterFiles.add(jupyterFile);

            try {
                FileUtils.uploadFile(file, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (jupyterFiles.isEmpty() && isExists) {
            return BasicResultVO.fail("文件已经存在!");
        }

        try {
            backLessonService.addJupyterFiles(jupyterFiles);
            return BasicResultVO.success("文件上传成功!");
        } catch (Exception e) {
            return BasicResultVO.fail("文件上传失败!");
        }
    }

    @PostMapping("/addSonChapterBook")
    public BasicResultVO<Void> addSonChapterBook(@RequestBody SonChapterAndUrl sonChapterAndUrl) {
        try {
            backLessonService.addSonChapterBook(sonChapterAndUrl);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/getEditSonChapterInfo")
    public BasicResultVO<SonChapterDto> getEditSonChapterInfo(Integer sonId) {
        SonChapterDto sonChapter = backLessonService.getSonChapter(sonId);
        return BasicResultVO.success(sonChapter);
    }

    @GetMapping("/getJupyterFiles")
    public BasicResultVO<List<JupyterFile>> getJupyterFiles(Integer sonId) {
        try {
            List<JupyterFile> jupyterFiles = backLessonService.getJupyterFiles(sonId);
            return BasicResultVO.success(jupyterFiles);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/deleteJupyterFiles")
    public BasicResultVO<Void> deleteJupyterFiles(@RequestBody JupyterFileDto jupyterFileDto) {
        try {
            List<Integer> jupyterIds = jupyterFileDto.getJupyterIds();
            List<String> jupyterPaths = jupyterFileDto.getJupyterPaths();

            backLessonService.deleteJupyterFilesByIds(jupyterIds);

            for (String jupyterPath : jupyterPaths) {
                FileUtils.deleteFile(fileDirectory + jupyterPath);
            }

            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
