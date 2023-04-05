package edu.nuist.service;

import edu.nuist.entity.Result;
import edu.nuist.entity.User;

import java.util.List;

public interface BackUserService {

    List<User> getAllTeachers();

    List<User> getAllStudents();

    List<User> getTeachersByRealName(String realName);

    List<User> getStudentByRealName(String realName);

    Result addTeacher(User User);

    Result addStudent(User User);

    Result editTeacher(User User);

    void deleteUser(User User);
    
}
