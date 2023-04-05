package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.BackUserService;
import edu.nuist.vo.PageRequest;
import edu.nuist.vo.RealNameVo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class BackUserController {

    @Resource
    private BackUserService backUsersService;

    @PostMapping(value = "/userBack/getAllTeachers")
    public PageInfo<User> getAllTeachers(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<User> usersList;

        try {
            usersList = backUsersService.getAllTeachers();
            return new PageInfo<>(usersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/userBack/getAllStudent")
    public PageInfo<User> getAllStudent(@RequestBody PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<User> usersList;

        try {
            usersList = backUsersService.getAllStudents();
            return new PageInfo<>(usersList, pageRequest.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, pageRequest.getPageSize());
        }
    }

    @PostMapping(value = "/userBack/addTeacher")
    public Result addTeacher(@RequestBody User addTeacher){
        return backUsersService.addTeacher(addTeacher);

    }

    @PostMapping(value = "/userBack/addStudent")
    public Result addStudent(@RequestBody User addStudent){
        return backUsersService.addStudent(addStudent);

    }

    @PostMapping(value = "/userBack/editTeacher")
    public Result editTeacher(@RequestBody User editTeacher){
        return backUsersService.editTeacher(editTeacher);

    }

    @PostMapping(value = "/userBack/deleteUser")
    public Result deleteUser(@RequestBody String deleteRow) throws JSONException {
        Result result = new Result();
        JSONObject jsonTest = new JSONObject(deleteRow);

        try {
            List<User> userList = com.alibaba.fastjson.JSONObject
                    .parseArray(jsonTest.getString("deleteRow"), User.class);

            for (User user: userList) {
                backUsersService.deleteUser(user);
            }

            result.setCode("200");
        } catch (JSONException e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @PostMapping("/userBack/findTeacherByName")
    public PageInfo<User> findTeacherByName(@RequestBody RealNameVo realNameVo){
        PageHelper.startPage(realNameVo.getCurrentPage(), realNameVo.getPageSize());
        List<User> usersList;

        try {
            usersList = backUsersService.getTeachersByRealName(realNameVo.getRealName());
            return new PageInfo<>(usersList, realNameVo.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, realNameVo.getPageSize());
        }
    }

    @PostMapping("/userBack/findStudentByName")
    public PageInfo<User> findStudentByName(@RequestBody RealNameVo realNameVo){
        PageHelper.startPage(realNameVo.getCurrentPage(), realNameVo.getPageSize());
        List<User> usersList;

        try {
            usersList = backUsersService.getStudentByRealName(realNameVo.getRealName());
            return new PageInfo<>(usersList, realNameVo.getPageSize());
        } catch (Exception e) {
            return new PageInfo<>(null, realNameVo.getPageSize());
        }
    }

}
