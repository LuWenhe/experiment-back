package edu.nuist.service;

import edu.nuist.entity.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);

    void addStudents(List<Student> students);

    List<Student> getAllStudent();

    void editStudent(Student student);

    void deleteStudentsByIds(List<Integer> studentIds);

}
