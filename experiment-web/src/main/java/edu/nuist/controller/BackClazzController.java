package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Clazz;
import edu.nuist.entity.Result;
import edu.nuist.entity.Student;
import edu.nuist.service.BackClazzService;
import edu.nuist.vo.PageRequest;
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
    public Result getClazzList(@RequestParam("teacherId") Integer teacherId) {
        Result result = new Result();

        try {
            List<Clazz> clazzList = backClazzService.getClazzListByTeacherId(teacherId);
            result.setData(clazzList);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
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

}
