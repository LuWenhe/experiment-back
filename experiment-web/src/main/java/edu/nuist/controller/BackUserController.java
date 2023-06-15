package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import edu.nuist.listener.StudentExcelListener;
import edu.nuist.service.BackClazzService;
import edu.nuist.service.BackUserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.PageRequest;
import edu.nuist.vo.RealNameVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backUser")

public class BackUserController {

    @Resource
    private BackUserService backUserService;

    @Resource
    private BackClazzService backClazzService;

    @GetMapping(value = "/getAllTeachers")
    public BasicResultVO<PageInfo<User>> getAllTeachers(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<User> usersList = backUserService.getAllTeachers();
            PageInfo<User> pageInfo = new PageInfo<>(usersList, pageSize);
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping(value = "/loadAllTeachers")
    public BasicResultVO<List<User>> loadAllTeachers() {
        try {
            List<User> allTeachers = backUserService.getAllTeachers();
            return BasicResultVO.success(allTeachers);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping(value = "/addTeacher")
    public BasicResultVO<Void> addTeacher(@RequestBody User addTeacher) {
        try {
            addTeacher.setPassword(new EncryptUtil().getEnpPassword(addTeacher.getPhone()));
            backUserService.addTeacher(addTeacher);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping(value = "/editTeacher")
    public BasicResultVO<Void> editTeacher(@RequestBody User user) {
        try {
            backUserService.editTeacher(user);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping(value = "/getAllStudents")
    public BasicResultVO<PageInfo<User>> getAllStudent(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());

        try {
            List<User> users = backUserService.getAllStudents();
            PageInfo<User> pageInfo = new PageInfo<>(users, pageRequest.getPageSize());
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addStudent")
    public BasicResultVO<Void> addStudent(@RequestBody Student student) {
        try {
            student.setPassword(new EncryptUtil().getEnpPassword(student.getPhone()));
            backUserService.addStudent(student);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/addStudents")
    public BasicResultVO<Void> addStudents(@RequestBody List<Student> students) {
        try {
            backUserService.addStudents(students);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/editStudent")
    public BasicResultVO<Void> editStudentInfo(@RequestBody Student student) {
        try {
            backUserService.editStudent(student);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/deleteUsersByIds")
    public BasicResultVO<Void> deleteUsers(@RequestBody List<Integer> studentIds) {
        try {
            backUserService.deleteUsersByIds(studentIds);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/deleteStudentsAndClazzByClazzId")
    public BasicResultVO<Void> deleteStudentsByClazzId(Integer clazzId) {
        try {
            backUserService.deleteStudentsByClazzId(clazzId);
            backClazzService.deleteClazz(clazzId);
            return BasicResultVO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/uploadFromExcel")
    public BasicResultVO<Void> addStudentFromExcel(@RequestParam("clazzId") Integer clazzId,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader excelReader = null;
        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();
            // 根据clazzId将学生信息写入对应的班级
            excelReader = EasyExcel.read(inputStream, Student.class,
                    new StudentExcelListener(clazzId, backUserService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            return BasicResultVO.success("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return BasicResultVO.fail("文件上传失败");
        } finally {
            inputStream.close();

            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    @GetMapping("/findTeacherByName")
    public BasicResultVO<PageInfo<User>> findTeacherByName(
            @RequestParam("realName") String realName,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<User> teachers = backUserService.getTeachersByRealName(realName);
            PageInfo<User> pageInfo = new PageInfo<>(teachers, pageSize);
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/findStudentByName")
    public BasicResultVO<PageInfo<User>> findStudentByName(@RequestBody RealNameVo realNameVo) {
        PageHelper.startPage(realNameVo.getCurrentPage(), realNameVo.getPageSize());

        try {
            List<User> usersList = backUserService.getStudentByRealName(realNameVo.getRealName());
            PageInfo<User> pageInfo = new PageInfo<>(usersList, realNameVo.getPageSize());
            return BasicResultVO.success(pageInfo);
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
