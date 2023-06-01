package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/clazz")
public class BackClazzController {

    @Resource
    private BackClazzService backClazzService;

    @GetMapping("/getClazzList")
    public PageInfo<Clazz> getClazzList(@RequestParam("teacherId") Integer teacherId,
                                        @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Clazz> clazzList;

        try {
            clazzList = backClazzService.getClazzListByTeacherId(teacherId);
            return new PageInfo<>(clazzList, pageSize);
        } catch (Exception e) {
            return new PageInfo<>(null, pageSize);
        }
    }

    @GetMapping("/getStudents")
    public PageInfo<Student> getStudents(@RequestParam("clazzId") Integer clazzId,
                                         @RequestParam("currentPage") Integer currentPage,
                                         @RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Student> students;

        try {
            students = backClazzService.getStudentsByClazzId(clazzId);
            return new PageInfo<>(students, pageSize);
        } catch (Exception e) {
            return new PageInfo<>(null, pageSize);
        }
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

    @PostMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody Integer clazzId) {
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
