package edu.nuist.service;

import edu.nuist.entity.Student;
import edu.nuist.entity.User;

import java.util.List;

public interface BackUserService {

    List<User> getAllTeachers();

    List<User> getTeachersByRealName(String realName);

    void addTeacher(User User);

    void editTeacher(User User);

    List<User> getAllStudents();

    List<User> getStudentByRealName(String realName);

    void addStudent(Student student);

    void addStudents(List<Student> students);

    void editStudent(Student student);

    void deleteUsersByIds(List<Integer> studentIds);

    void deleteStudentsByClazzId(Integer clazzId);

    void deleteUser(User User);

}
