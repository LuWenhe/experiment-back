package edu.nuist.service;

import edu.nuist.entity.Clazz;
import edu.nuist.entity.Student;

import java.util.List;

public interface BackClazzService {

    List<Clazz> getClazzListByTeacherId(Integer teacherId);

    List<Student> getStudentsByClazzId(Integer clazzId);

}
