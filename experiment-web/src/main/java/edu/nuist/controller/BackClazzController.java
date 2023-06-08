package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.entity.Clazz;
import edu.nuist.entity.Result;
import edu.nuist.entity.Student;
import edu.nuist.service.BackClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backClazz")
public class BackClazzController {

    @Resource
    private BackClazzService backClazzService;

    @PermissionAnnotation
    @GetMapping("/getClazzList")
    public Result getClazzList(@RequestParam("teacherId") Integer teacherId,
                               @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Clazz> clazzList = backClazzService.getClazzListByTeacherId(teacherId);
            PageInfo<Clazz> pageInfo = new PageInfo<>(clazzList, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/getStudents")
    public Result getStudentsByClazzId(@RequestParam("clazzId") Integer clazzId,
                                       @RequestParam("currentPage") Integer currentPage,
                                       @RequestParam("pageSize") Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(currentPage, pageSize);

        try {
            List<Student> students = backClazzService.getStudentsByClazzId(clazzId);
            PageInfo<Student> pageInfo = new PageInfo<>(students, pageSize);
            result.setData(pageInfo);
            result.setCode("200");
        } catch (Exception e) {
            result.setCode("500");
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/addClazz")
    public Result addClazz(@RequestBody Clazz clazz) {
        Result result = new Result();

        try {
            backClazzService.addClazz(clazz);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/updateClazz")
    public Result editClazz(@RequestBody Clazz clazz) {
        Result result = new Result();

        try {
            backClazzService.updateClazz(clazz);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @GetMapping("/deleteClazz")
    public Result deleteClazz(@RequestParam("clazzId") Integer clazzId) {
        Result result = new Result();

        try {
            backClazzService.deleteClazz(clazzId);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

}
