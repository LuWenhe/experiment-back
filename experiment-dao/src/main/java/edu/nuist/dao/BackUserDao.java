package edu.nuist.dao;

import edu.nuist.entity.User;
import java.util.List;

public interface BackUserDao {

    List<User> getAllTeachers();

    List<User> getAllStudents();

    void addTeacher(User user);

    void addStudent(User user);

    void editTeacher(User user);

    void deleteUsers(User user);

    List<User> findTeachersByRName(String realName);

    List<User> findStudentByRName(String realName);

}
