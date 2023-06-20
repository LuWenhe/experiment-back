package edu.nuist.service;

import edu.nuist.entity.Clazz;
import edu.nuist.entity.Student;

import java.util.List;

public interface BackClazzService {

    List<Clazz> getAllClazzList();

    List<Clazz> getClazzListByTeacherId(Integer teacherId);

    List<Student> getStudentsByClazzId(Integer clazzId);

    boolean addClazz(Clazz clazz);

    boolean updateClazz(Clazz clazz);

    boolean deleteClazz(Integer clazzId);

}
