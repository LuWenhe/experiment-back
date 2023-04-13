package edu.nuist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Result;
import edu.nuist.entity.Student;
import edu.nuist.listener.StudentExcelListener;
import edu.nuist.service.StudentService;
import edu.nuist.vo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Slf4j
public class BackStudentController {

    @Resource
    private StudentService studentService;

    @PostMapping("/userBack/getStudents")
    public PageInfo<Student> getAllStudents(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Student> studentList;

        studentList = studentService.getAllStudent();
        return new PageInfo<>(studentList, pageRequest.getPageSize());
    }

    @PostMapping("/userBack/addOneStudent")
    public Result addStudent(@RequestBody Student student) {
        Result result = new Result();

        try {
            studentService.addStudent(student);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/userBack/addStudents")
    public Result addStudents(@RequestBody List<Student> students) {
        Result result = new Result();

        try {
            studentService.addStudents(students);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/userBack/editStudentInfo")
    public Result editStudentInfo(@RequestBody Student student) {
        Result result = new Result();

        try {
            studentService.editStudent(student);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/userBack/deleteStudents")
    public Result deleteStudents(@RequestBody List<Integer> studentIds) {
        Result result = new Result();

        try {
            studentService.deleteStudentsByIds(studentIds);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/userBack/uploadFromExcel")
    public Result addStudentFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        ExcelReader excelReader = null;
        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();
            excelReader = EasyExcel.read(inputStream, Student.class, new StudentExcelListener(studentService)).build();
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

}
