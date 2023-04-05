package edu.nuist.service.impl;

import edu.nuist.dao.BackUserDao;
import edu.nuist.entity.Result;
import edu.nuist.entity.User;
import edu.nuist.service.BackUserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.GetCurrentDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackUserServiceImpl implements BackUserService {

    @Resource
    private BackUserDao backUsersDao;

    @Override
    public List<User> getAllTeachers() {
        return backUsersDao.getAllTeachers();
    }

    @Override
    public List<User> getAllStudents() {
        return backUsersDao.getAllStudents();
    }

    @Override
    public List<User> getTeachersByRealName(String realName) {
        return backUsersDao.findTeachersByRName(realName);
    }

    @Override
    public List<User> getStudentByRealName(String realName) {
        return backUsersDao.findStudentByRName(realName);
    }

    @Override
    public Result addTeacher(User user) {
        Result result = new Result();

        try {
            user.setPassword(new EncryptUtil().getEnpPassword(user.getPhone()));
            user.setCreated_time(new GetCurrentDate().getCurrentDate());
            backUsersDao.addTeacher(user);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }
        return result;
    }

    @Override
    public Result addStudent(User user) {
        Result result = new Result();
        try {
            user.setPassword(new EncryptUtil().getEnpPassword(user.getPhone()));
            user.setCreated_time(new GetCurrentDate().getCurrentDate());
            backUsersDao.addStudent(user);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }
        return result;
    }

    @Override
    public Result editTeacher(User user) {
        Result result = new Result();

        try {
            backUsersDao.editTeacher(user);
            result.setCode("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("500");
        }

        return result;
    }

    @Override
    public void deleteUser(User user) {
        backUsersDao.deleteUsers(user);
    }

}
