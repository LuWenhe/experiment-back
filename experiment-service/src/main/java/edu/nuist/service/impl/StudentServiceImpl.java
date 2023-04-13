package edu.nuist.service.impl;

import edu.nuist.dao.StudentDao;
import edu.nuist.entity.Student;
import edu.nuist.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public void addStudents(List<Student> students) {
        studentDao.addStudents(students);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentDao.getAllStudent();
    }

    @Override
    public void editStudent(Student student) {
        studentDao.editStudent(student);
    }

    @Override
    public void deleteStudentsByIds(List<Integer> studentIds) {
        studentDao.deleteStudentsByIds(studentIds);
    }

}
