package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Result;
import edu.nuist.entity.Student;
import edu.nuist.entity.User;
import edu.nuist.listener.StudentExcelListener;
import edu.nuist.service.BackClazzService;
import edu.nuist.service.BackUserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.GetCurrentDate;
import edu.nuist.vo.PageRequest;
import edu.nuist.vo.RealNameVo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/userBack")
public class BackUserController {

    @Resource
    private BackUserService backUserService;

    @Resource
    private BackClazzService backClazzService;

    @PostMapping(value = "/getAllTeachers")
    public PageInfo<User> getAllTeachers(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<User> usersList;

        try {
            usersList = backUserService.getAllTeachers();
            return new PageInfo<>(usersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/addTeacher")
    public Result addTeacher(@RequestBody User addTeacher) {
        Result result = new Result();

        try {
            addTeacher.setPassword(new EncryptUtil().getEnpPassword(addTeacher.getPhone()));
            addTeacher.setCreated_time(new GetCurrentDate().getCurrentDate());
            backUserService.addTeacher(addTeacher);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping(value = "/editTeacher")
    public Result editTeacher(@RequestBody User editTeacher) {
        Result result = new Result();

        try {
            backUserService.editTeacher(editTeacher);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping(value = "/getAllStudents")
    public PageInfo<User> getAllStudent(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<User> usersList;

        try {
            usersList = backUserService.getAllStudents();
            return new PageInfo<>(usersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Student student) {
        Result result = new Result();

        try {
            student.setPassword(new EncryptUtil().getEnpPassword(student.getPhone()));
            backUserService.addStudent(student);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/addStudents")
    public Result addStudents(@RequestBody List<Student> students) {
        Result result = new Result();

        try {
            backUserService.addStudents(students);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/editStudent")
    public Result editStudentInfo(@RequestBody Student student) {
        Result result = new Result();

        try {
            backUserService.editStudent(student);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/deleteStudentsByIds")
    public Result deleteStudents(@RequestBody List<Integer> studentIds) {
        Result result = new Result();

        try {
            backUserService.deleteStudentsByIds(studentIds);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/deleteStudentsAndClazzByClazzId")
    public Result deleteStudentsByClazzId(@RequestBody Integer clazzId) {
        Result result = new Result();

        try {
            backUserService.deleteStudentsByClazzId(clazzId);
            backClazzService.deleteClazz(clazzId);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/uploadFromExcel")
    public Result addStudentFromExcel(@RequestParam("clazzId") Integer clazzId,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        ExcelReader excelReader = null;
        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();
            // 根据clazzId将学生信息写入对应的班级
            excelReader = EasyExcel.read(inputStream, Student.class,
                    new StudentExcelListener(clazzId, backUserService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            result.setCode("200");
            result.setMsg("文件上传成功!");
        } catch (IOException e) {
            log.error("import excel to db fail", e);
            result.setCode("500");
        } finally {
            inputStream.close();

            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        return result;
    }

    @PostMapping(value = "/deleteUser")
    public Result deleteUser(@RequestBody String deleteRow) throws JSONException {
        Result result = new Result();
        JSONObject jsonTest = new JSONObject(deleteRow);

        try {
            List<User> userList = com.alibaba.fastjson.JSONObject
                    .parseArray(jsonTest.getString("deleteRow"), User.class);

            for (User user : userList) {
                backUserService.deleteUser(user);
            }

            result.setCode("200");
        } catch (JSONException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/findTeacherByName")
    public PageInfo<User> findTeacherByName(@RequestBody RealNameVo realNameVo) {
        PageHelper.startPage(realNameVo.getCurrentPage(), realNameVo.getPageSize());
        List<User> usersList;

        try {
            usersList = backUserService.getTeachersByRealName(realNameVo.getRealName());
            return new PageInfo<>(usersList, realNameVo.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, realNameVo.getPageSize());
        }
    }

    @PostMapping("/findStudentByName")
    public PageInfo<User> findStudentByName(@RequestBody RealNameVo realNameVo) {
        PageHelper.startPage(realNameVo.getCurrentPage(), realNameVo.getPageSize());
        List<User> usersList;

        try {
            usersList = backUserService.getStudentByRealName(realNameVo.getRealName());
            return new PageInfo<>(usersList, realNameVo.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, realNameVo.getPageSize());
        }
    }

}
